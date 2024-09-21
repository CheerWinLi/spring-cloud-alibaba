package com.alibaba.cloud.rpc.client;

import com.alibaba.cloud.rpc.metadata.HttpMetadata;
import com.alibaba.cloud.rpc.metadata.HttpRpcResponse;
import com.alibaba.cloud.rpc.utils.UrlResolver;
import feign.Client;
import feign.Request;
import feign.Response;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author :Lictory
 * @date : 2024/08/12
 */


public class FeignRpcClient implements Client {

    private LoadBalancerClient loadBalancerClient;
    private ExchangeClient client;

    public FeignRpcClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public Response execute(Request request, Request.Options options) {

        String url = UrlResolver.resolveOriginalUrl(loadBalancerClient, request.url());
        this.client = UrlResolver.getClient(url);
        HttpMetadata httpMetadata = initHttpMetadata(
                UrlResolver.getPathFromUrl(request.url()),
                request.httpMethod().name(),
                request.headers(),
                request.body()
        );
        HttpRpcResponse httpRpcResponse = null;
        try {
            CompletableFuture<Object> future = client.request(httpMetadata);
            httpRpcResponse = (HttpRpcResponse) future.get();
        } catch (RemotingException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Response.builder()
                .status(httpRpcResponse.getStatusCode())
                .reason(httpRpcResponse.getReasonPhrase())
                .headers(httpRpcResponse.getHeaders())
                .body(httpRpcResponse.getBody())
                .request(request)
                .build();
    }

    private HttpMetadata initHttpMetadata(String url, String method, Map<String, Collection<String>> headers, byte[] body) {
        HttpMetadata httpMetadata = new HttpMetadata();
        httpMetadata.setBody(body);
        httpMetadata.setUrl(url);
        httpMetadata.setHeaders(headers);
        httpMetadata.setMethod(method);
        return httpMetadata;
    }
}


