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

package com.alibaba.dubbo.provider.service.impl;


import com.alibaba.dubbo.consumer.entity.User;
import com.alibaba.dubbo.consumer.api.FooService;
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
