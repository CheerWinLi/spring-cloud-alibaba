/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
