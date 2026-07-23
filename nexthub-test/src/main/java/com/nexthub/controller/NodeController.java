package com.nexthub.controller;

import com.nexthub.model.Node;
import com.nexthub.service.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nexthub")
public class NodeController {


    private final NodeService nodeService;


    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }


    @GetMapping("/node")
    public ResponseEntity<List<Node>> getNodes(
            @RequestParam(defaultValue = "10") int limit
    ) {

        return ResponseEntity.ok(
                nodeService.getNodes(limit)
        );
    }


    @GetMapping("/node/{nodeId}")
    public ResponseEntity<Node> getNodeById(
            @PathVariable String nodeId
    ) {

        Node node = nodeService.getById(nodeId);

        if (node == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(node);
    }


    @PostMapping("/node")
    public ResponseEntity<?> createNode(
            @RequestBody String request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(request);
    }
}