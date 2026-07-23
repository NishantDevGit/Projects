package com.nexthub.model;

import java.time.LocalDateTime;

public record Node(
        String nodeId,
        String nodeName,
        String ipAddress,
        long uptimeSeconds,
        LocalDateTime lastStartup
) {
}