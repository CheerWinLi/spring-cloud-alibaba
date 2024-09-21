package com.alibaba.cloud.rpc.utils;

import com.alibaba.cloud.rpc.metadata.HttpMetadata;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Collection;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/23
 */
public class MockHttpServletRequestConverter {
    public static MockHttpServletRequest getMockHttpServletRequest(HttpMetadata httpMetadata, String url) {
        // 创建 MockHttpServletRequest 实例
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        // 设置 HTTP 方法
        mockRequest.setMethod(httpMetadata.getMethod());

        // 设置 URL
        mockRequest.setRequestURI(url);

        // 设置请求头
        Map<String, Collection<String>> headers = httpMetadata.getHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (String headerValue : entry.getValue()) {
                mockRequest.addHeader(headerName, headerValue);
            }
        }
        // 设置请求体（如果有）
        if (httpMetadata.getBody() != null) {
            mockRequest.setContent(httpMetadata.getBody());
        }
        return mockRequest;
    }
}
