package com.gateway.apigateway;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.apigateway.Message.APIMessage;
import com.gateway.apigateway.Message.APIMessageRepo;
import com.gateway.apigateway.Message.StatusCount;
import com.gateway.apigateway.apiservice.APIServiceService;
import com.gateway.apigateway.controller.model.APIMessagesResponse;
import com.gateway.apigateway.controller.model.APIMetric;
import com.gateway.apigateway.controller.model.APIMetrics;
import com.gateway.apigateway.controller.model.APIService;
import com.gateway.apigateway.controller.model.Error;
import com.gateway.apigateway.controller.model.ErrorResponse;
import com.gateway.apigateway.controller.model.HttpStatusCount;
import com.gateway.apigateway.controller.model.Response;
import com.gateway.apigateway.controller.model.SearchLogQuery;
import com.gateway.apigateway.user.GetUserResponse;
import com.gateway.apigateway.user.User;
import com.gateway.apigateway.user.UserRepo;
import com.gateway.apigateway.user.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private APIMessageRepo apiMessageRepo;
	
	@Autowired
	private APIServiceService apiServices;

	@PostMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			userService.addUser(user);
			Response response = new Response();
			response.setOpreation("ADD_USER");
			response.setMessage("User Added.");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Error error = new Error();
			error.setError("Failed to add user");
			error.setMessage(e.getMessage());
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(error);
			return ResponseEntity.badRequest().body(errorResponse);
		}

	}

	@GetMapping("/user")
	public ResponseEntity<GetUserResponse> getUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PostMapping("/call")
	public ResponseEntity<APIMessagesResponse> getCalls(@RequestBody SearchLogQuery query) {
		
		Integer limit = query.getLimit();
		if(limit == null || limit > 1000) limit = 1000;
		 org.springframework.data.domain.Pageable pageable = PageRequest.of(0,limit.intValue());
		APIMessagesResponse messagesResponse = new APIMessagesResponse();
		messagesResponse.setApiMessages(apiMessageRepo.searchLogs(query.getStartTime(), 
				query.getEndTime(), query.getHttpMethod(), query.getCallerName(), query.getHttpStatus(), query.getServiceId(), pageable));
		return ResponseEntity.ok(messagesResponse);
	}
	
	@GetMapping("/call/{id}")
	public ResponseEntity<APIMessage> getCallById(@PathVariable String id) {
	    Optional<APIMessage> optional =	apiMessageRepo.findById(id);
	    if(optional.isEmpty()) return ResponseEntity.ok(new APIMessage());
		return ResponseEntity.ok(optional.get());
	}
	
	@GetMapping("/metric")
	public ResponseEntity<APIMetrics> metric(  @RequestParam(required = false) String startDate,  @RequestParam(required = false) String endDate) throws ParseException{
		Date start =null;
		Date end =null;
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 if(startDate != null) start = formatter.parse(startDate);
		 if(endDate != null) end = formatter.parse(endDate);
		List<StatusCount> counts = apiMessageRepo.metric(start, end);
		Map<String, List<HttpStatusCount>> map = new HashMap<String, List<HttpStatusCount>>();
		Map<String, Long> apiCount = new HashMap<String, Long>() ;
		Map<String, List<StatusCount>> groupedByServiceId =	counts.stream().parallel().collect(Collectors.groupingBy(StatusCount::getServiceId));
		Long grandTotal = 0l;
		for (String apiId : groupedByServiceId.keySet()) {
			Long apiTotal = 0l;
			List<HttpStatusCount> httpStatusCounts = new ArrayList<HttpStatusCount>();
		    List<StatusCount> countslist = groupedByServiceId.get(apiId);
		    for (StatusCount statusCount : countslist) {
		    	HttpStatusCount httpStatusCount = new HttpStatusCount();
		    	httpStatusCount.setCount(statusCount.getCount());
		    	httpStatusCount.setHttpStatusCode(statusCount.getHttpstatus());
		    	apiTotal  = apiTotal + statusCount.getCount();
		    	httpStatusCounts.add(httpStatusCount);
			}
		    map.put(apiId, httpStatusCounts);
		    apiCount.put(apiId, apiTotal);
		    grandTotal = grandTotal + apiTotal;
		}
		List<APIMetric> apiMetrics = new ArrayList<APIMetric>();
		
		for (String key : apiCount.keySet()) {
			APIMetric apiMetric = new APIMetric();
			apiMetric.setApiId(key);
			apiMetric.setTotalCount(apiCount.get(key));
			apiMetric.setHttpStatusCounts(map.get(key));
			apiMetrics.add(apiMetric);
		}
		APIMetrics apiMetrics2 = new APIMetrics();
		apiMetrics2.setApiMetrics(apiMetrics);
		apiMetrics2.setTotal(grandTotal);
		return  ResponseEntity.ok(apiMetrics2);
		 
	}
	
	@PostMapping("/service")
    public String addRoute(@RequestBody APIService service) throws Exception {
		apiServices.addRoute(service);
        return "Service " + service.getServiceId() + " added!";
    }
	
	@DeleteMapping("/service")
	public String removeRoute(@RequestHeader(required = true,name = "serviceId") String apiId) throws Exception {
		Boolean result = apiServices.deleteRoute(apiId);
		String message = "Service '"+apiId+"'";
		if(result) message = message +" removed successfully!.";
		else message = message +" failed to remove!.";
		return  message;
	}
	
	@GetMapping("/service")
	public List<APIService> getAllService(){
		return apiServices.getAllServices();
	}
	

}
