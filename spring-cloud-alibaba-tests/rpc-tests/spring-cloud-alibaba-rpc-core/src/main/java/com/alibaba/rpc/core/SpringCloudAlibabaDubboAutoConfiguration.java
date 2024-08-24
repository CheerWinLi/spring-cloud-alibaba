package com.alibaba.rpc.core;



import com.alibaba.rpc.annotation.DubboFeignBuilder;
import com.alibaba.rpc.annotation.FeignClientToDubboProviderBeanPostProcessor;
import feign.Feign;
import org.apache.dubbo.config.AbstractConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.apache.dubbo.spring.boot.util.DubboUtils.BASE_PACKAGES_PROPERTY_NAME;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_PREFIX;

/**
 * @author :Lictory
 * @date : 2024/07/31
 */

//@Configuration
//@ConditionalOnProperty(prefix = DUBBO_PREFIX, name = "enabled", matchIfMissing = true, havingValue = "true")
//@ConditionalOnClass(AbstractConfig.class)
//public class SpringCloudAlibabaDubboAutoConfiguration {
//    @ConditionalOnProperty(name = BASE_PACKAGES_PROPERTY_NAME)
//    @ConditionalOnClass(ConfigurationPropertySources.class)
//    @Bean
//    public FeignClientToDubboProviderBeanPostProcessor feignClientToDubboProviderBeanPostProcessor(Environment environment) {
//        Set<String> packagesToScan = environment.getProperty(BASE_PACKAGES_PROPERTY_NAME, Set.class, emptySet());
//        return new FeignClientToDubboProviderBeanPostProcessor(packagesToScan);
//    }
//
//    @Bean
//    public Feign.Builder feignDubboBuilder() {
//        return new DubboFeignBuilder();
//    }
//}

