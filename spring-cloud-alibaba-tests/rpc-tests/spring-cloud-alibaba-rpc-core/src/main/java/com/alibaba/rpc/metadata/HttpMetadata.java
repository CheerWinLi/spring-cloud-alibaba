package com.alibaba.rpc.metadata;

import feign.Request;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/16
 */
public class HttpMetadata implements Serializable {

    private String url;

    private String method;

    private  byte[] body;

    private  Map<String, Collection<String>> headers;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Collection<String>> headers) {
        this.headers = headers;
    }
}
