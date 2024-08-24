package com.alibaba.dubbo.provider.controller;

import com.alibaba.dubbo.consumer.api.ProviderService;
import com.alibaba.dubbo.provider.service.impl.ProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :Lictory
 * @date : 2024/08/09
 */

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/get")
    public String getTest() {
        return providerService.hi();
    }

    @PostMapping("/post/{userId}")
    public void postTest(@PathVariable("userId") Integer userId) {
        providerService.post(userId);
    }
}
