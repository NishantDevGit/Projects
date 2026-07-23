package com.nexthub.service;

import com.nexthub.config.NodeProperties;
import com.nexthub.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NodeService {

    private final Cache nodeCache;

    private final NodeProperties properties;

    private final AtomicInteger sequence = new AtomicInteger(0);

    private final Random random = new Random();

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeService.class);


    public NodeService(
            CacheManager cacheManager,
            NodeProperties properties
    ) {
        this.nodeCache = cacheManager.getCache("nodes");
        this.properties = properties;
    }


    public List<Node> getNodes(int limit) {

        int actualLimit = Math.min(
                Math.max(limit, 1),
                properties.maxLimit()
        );

        createMissingNodes(actualLimit);

        List<Node> result = new ArrayList<>();

        for (int i = 1; i <= actualLimit; i++) {

            Node node = nodeCache.get(
                    "NODE-" + String.format("%06d", i),
                    Node.class
            );

            if (node != null) {
                result.add(node);
            }
        }

        return result;
    }


    private void createMissingNodes(int requiredCount) {

        while (sequence.get() < requiredCount) {

            int id = sequence.incrementAndGet();

            Node node = new Node(
                    "NODE-" + String.format("%06d", id),
                    "Server-" + random.nextInt(10000),
                    generateIp(),
                    random.nextLong(1000, 500000),
                    LocalDateTime.now()
                            .minusSeconds(random.nextInt(100000))
            );

            nodeCache.put(
                    node.nodeId(),
                    node
            );
            LOGGER.info("Created node: {}", node);
        }
    }


    public Node getById(String nodeId) {

        return nodeCache.get(
                nodeId,
                Node.class
        );
    }


    private String generateIp() {

        return random.nextInt(1,255)
                + "."
                + random.nextInt(1,255)
                + "."
                + random.nextInt(1,255)
                + "."
                + random.nextInt(1,255);
    }
}
