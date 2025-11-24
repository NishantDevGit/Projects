package com.gateway.apigateway.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.component.jetty.JettyHttpComponent;
import org.eclipse.jetty.util.thread.VirtualThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class CustomJettyConfig implements CamelContextAware {

    private CamelContext context;
    @Value("${apigateway.thread.max}")
    private int maxThread;
    @Value("${apigateway.thread.min}")
    private int minThread;
    @Value("${apigateway.timeout}")
    private long timeout;

    @PostConstruct
    public void configureJettyThreading() {
        JettyHttpComponent jetty = context.getComponent("jetty", JettyHttpComponent.class);
        VirtualThreadPool threadPool =  new VirtualThreadPool();
        threadPool.setName("API-GATEWAY-POOL");
        threadPool.setMaxThreads(maxThread);
        jetty.setThreadPool(threadPool);
        jetty.setContinuationTimeout(timeout);
    }

    @Override
    public void setCamelContext(CamelContext context) {
        this.context = context;
    }

    @Override
    public CamelContext getCamelContext() {
        return this.context;
    }
}