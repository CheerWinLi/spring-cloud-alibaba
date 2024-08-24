package com.alibaba.dubbo.provider.service.impl;


import com.alibaba.dubbo.consumer.entity.User;
import com.alibaba.dubbo.consumer.api.FooService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author :Lictory
 * @date : 2024/08/01
 */
@Service
public class FooServiceImpl implements FooService {
    @Override
    public String foo() {
        return "Foo Spring Cloud Alibaba Dubbo";
    }

    @Override
    public User getUser() {
        return new User();
    }


}
