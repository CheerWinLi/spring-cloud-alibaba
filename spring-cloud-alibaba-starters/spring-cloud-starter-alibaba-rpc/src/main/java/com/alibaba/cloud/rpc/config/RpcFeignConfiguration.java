package com.alibaba.cloud.rpc.config;

import com.alibaba.cloud.rpc.client.FeignRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :Lictory
 * @date : 2024/08/11
 */

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.rpc.enable", matchIfMissing = true)
public class RpcFeignConfiguration {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Bean
    public feign.Client feignClient() {
        return new FeignRpcClient(loadBalancerClient);
    }
}
