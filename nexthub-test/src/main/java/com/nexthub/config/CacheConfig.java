package com.nexthub.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager cacheManager(NodeProperties properties) {
        LOGGER.info("Initializing Caffeine Cache Manager with max limit: {}", properties.maxLimit());
        CaffeineCacheManager cacheManager =
                new CaffeineCacheManager("nodes");

        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .initialCapacity(100)
                        .maximumSize(properties.maxLimit())
        );
        LOGGER.info("Caffeine Cache Manager initialized successfully.");
        return cacheManager;
    }
}