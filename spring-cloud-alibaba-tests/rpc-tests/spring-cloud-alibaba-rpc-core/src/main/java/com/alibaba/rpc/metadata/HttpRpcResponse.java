package com.alibaba.rpc.metadata;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author :Lictory
 * @date : 2024/08/16
 */
public class HttpRpcResponse implements Serializable {
    private int statusCode;
    private String reasonPhrase;
    private Map<String, Collection<String>> headers;
    private byte[] body;
    public HttpRpcResponse(int statusCode, String reasonPhrase, Map<String, Collection<String>> headers, byte[] body) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
        this.body = body;
    }

    public HttpRpcResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Collection<String>> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}

