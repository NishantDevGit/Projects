package com.nexthub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nexthub.node")
public record  NodeProperties (
        int maxLimit
){
}
