package com.alibaba.dubbo.consumer.api;



import com.alibaba.dubbo.consumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author :Lictory
 * @date : 2024/08/01
 */
@FeignClient("provider")
public interface FooService {
    @GetMapping("/api/test")
    String foo();
    @GetMapping("/user")
    User getUser();
}

