package com.alibaba.dubbo.provider.server;

import com.alibaba.rpc.metadata.HttpMetadata;
import org.apache.coyote.Request;

import java.util.Collection;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/23
 */
public class CoyoteRequestBuilder {
    public static Request initializeCoyoteRequest(HttpMetadata httpMetadata, String url) {
        Request coyoteRequest = new Request();

        // 设置 HTTP 方法
        String method = httpMetadata.getMethod();
        coyoteRequest.method().setString(method);

        // 设置请求的完整 URL 和 URI
        coyoteRequest.requestURI().setString(url);
        //TODO 如果 URL 中包含查询字符串，设置查询参数
//        String[] urlParts = url.split("\\?", 2);
//        if (urlParts.length == 2) {
//            String queryString = urlParts[1];
//            coyoteRequest.queryString().setString(queryString);
//        }

        //TODO  设置请求体（如果有）
//        if (httpMetadata.getBody() != null) {
//            coyoteRequest. (new ByteChunk().append(httpMetadata.getBody()));
//        }

        // 设置请求头
        Map<String, Collection<String>> headers = httpMetadata.getHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (String headerValue : entry.getValue()) {
                coyoteRequest.getMimeHeaders().addValue(headerName).setString(headerValue);
            }
        }
        return coyoteRequest;
    }
}
