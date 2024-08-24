package com.alibaba.dubbo.consumer.config;

import com.alibaba.dubbo.consumer.client.FeignRpcClient;

import com.alibaba.dubbo.consumer.interceptor.RpcFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :Lictory
 * @date : 2024/08/11
 */

@Configuration
public class RpcFeignConfiguration {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    @Bean
//    public RequestInterceptor myRequestInterceptor() {
//        return new RpcFeignInterceptor();
//    }

    @Bean
    public feign.Client feignClient() {
        return new FeignRpcClient(loadBalancerClient);
    }
}
