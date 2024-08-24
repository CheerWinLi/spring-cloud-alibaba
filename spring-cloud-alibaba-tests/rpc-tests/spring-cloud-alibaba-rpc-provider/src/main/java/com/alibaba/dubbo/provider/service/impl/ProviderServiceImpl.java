package com.alibaba.dubbo.provider.service.impl;


import com.alibaba.dubbo.consumer.api.ProviderService;
import com.alibaba.dubbo.consumer.entity.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author :Lictory
 * @date : 2024/08/01
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String hi() {
        return "Hi Spring Cloud Alibaba 测试成功";
    }

    @Override
    public void post(Integer userId) {
        System.out.println("sca post Test 测试"+userId);
    }

}
