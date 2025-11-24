package com.gateway.apigateway.Message;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface APIMessageRepo extends JpaRepository<APIMessage, String> {
	
	@Query(value = "SELECT a FROM API_GATEWAY_MESSAGES a ORDER BY a.startTime DESC")
	Page<APIMessage> findWithLimit(Pageable pageable);
	
	@Query(value ="SELECT q FROM API_GATEWAY_MESSAGES q WHERE "
			 + "(cast(:startTime as timestamp) IS NULL OR q.startTime >= :startTime) AND "
		     + "(cast(:endTime as timestamp) IS NULL OR q.endTime <= :endTime) AND "
		     + "(:httpMethod IS NULL OR q.httpMethod = :httpMethod) AND "
		     + "(:callerName IS NULL OR q.callerName = :callerName) AND "
		     + "(:httpStatus IS NULL OR q.httpstatus = :httpStatus) AND "
		     + "(:serviceId IS NULL OR q.serviceId = :serviceId) "
	        + " ORDER BY q.startTime DESC")
	Page<APIMessage> searchLogs(@Param("startTime") Date startTime,
		    @Param("endTime") Date endTime,
		    @Param("httpMethod") String httpMethod,
		    @Param("callerName") String callerName,
		    @Param("httpStatus") String httpStatus,
		    @Param("serviceId") String serviceId,
		    Pageable pageable);
	
	@Query(value = "SELECT m.httpstatus as httpstatus, m.serviceId as serviceId, COUNT(m) as count " +
		       "FROM API_GATEWAY_MESSAGES m " +
		       "WHERE (cast(:startTime as timestamp) IS NULL OR m.startTime >= :startTime) " +
		       "AND (cast(:endTime as timestamp) IS NULL OR m.endTime <= :endTime) " +
		       "GROUP BY m.httpstatus, m.serviceId" )
	List<StatusCount> metric(  @Param("startTime") Date startTime,
		    @Param("endTime") Date endTime); 
	
}
