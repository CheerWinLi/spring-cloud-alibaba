package com.alibaba.dubbo.consumer.service;


import com.alibaba.dubbo.consumer.api.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :Lictory
 * @date : 2024/08/01
 */
@RestController
public class TestService {
    @Autowired
    private ProviderService providerService;

    @GetMapping("/test/get")
    public String testGet() {
        return providerService.hi();
    }

    @PostMapping("/test/post/{userId}")
    public void testPost(@PathVariable("userId") Integer userId) {
        providerService.post(userId);
    }
}


