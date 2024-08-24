package com.alibaba.dubbo.consumer.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author :Lictory
 * @date : 2024/08/11
 */
public class RpcFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("rpcFeign拦截测试");
    }
}
