package com.alibaba.dubbo.provider.server;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/22
 */
public class RpcToHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private final Map<String, Collection<String>> headers;
    private final String method;
    private final String requestURI;
    private final String queryString;

    public RpcToHttpServletRequestWrapper(HttpServletRequest request, String method, String requestURI, byte[] body, Map<String, Collection<String>> headers) {
        super(request);
        this.body = body != null ? body : new byte[0];
        this.headers = headers;
        this.method = method;
        this.requestURI = requestURI;
        // 可能需要从url中提取query string
        this.queryString = request.getQueryString();
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }
    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Collection<String> headerValues = headers.get(name);
        if (headerValues != null) {
            return Collections.enumeration(headerValues);
        }
        return Collections.enumeration(Collections.emptyList());
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public String getHeader(String name) {
        Collection<String> headerValues = headers.get(name);
        if (headerValues != null && !headerValues.isEmpty()) {
            return headerValues.iterator().next();  // 返回第一个值
        }
        return null;
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Not used in this implementation
            }
        };
    }

    @Override
    public int getContentLength() {
        return body.length;
    }

    @Override
    public String getContentType() {
        return getHeader("Content-Type");  // 使用 headers 中的 Content-Type
    }
}
