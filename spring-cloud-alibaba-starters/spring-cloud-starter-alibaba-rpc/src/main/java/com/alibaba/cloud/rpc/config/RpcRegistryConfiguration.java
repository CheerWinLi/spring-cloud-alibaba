package com.alibaba.cloud.rpc.config;

import com.alibaba.cloud.nacos.registry.NacosRegistration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/12
 */

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.rpc.enable", matchIfMissing = true)
public class RpcRegistryConfiguration {

    @Autowired
    private NacosRegistration nacosRegistration;

    @Value("${spring.rpc.netty.port}")
    private Integer port;

    @PostConstruct
    public void init() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("spring.rpc.netty.port", String.valueOf(port));
        nacosRegistration.getMetadata().putAll(metadata);
    }
}
