package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {

        OrderControllerV1 orderController = new OrderControllerV1Impl(orderService(logTrace));
        OrderControllerV1 orderControllerProxy = (OrderControllerV1) Proxy.newProxyInstance(
                        OrderControllerV1.class.getClassLoader(),
                        new Class[]{OrderControllerV1.class},
                        new LogTraceBasicHandler(orderController, logTrace));
        return orderControllerProxy;
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepository(logTrace));
        OrderServiceV1 orderServiceProxy = (OrderServiceV1) Proxy.newProxyInstance(
                        OrderServiceV1.class.getClassLoader(),
                        new Class[]{OrderServiceV1.class},
                        new LogTraceBasicHandler(orderService, logTrace));
        return orderServiceProxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();
        OrderRepositoryV1 orderRepositoryProxy = (OrderRepositoryV1) Proxy.newProxyInstance(
                        OrderRepositoryV1.class.getClassLoader(),
                        new Class[]{OrderRepositoryV1.class},
                        new LogTraceBasicHandler(orderRepository, logTrace));
        return orderRepositoryProxy;
    }

}
