package com.alibaba.dubbo.consumer.client;

import com.alibaba.rpc.metadata.HttpMetadata;
import com.alibaba.rpc.metadata.HttpRpcResponse;
import feign.Client;
import feign.Request;
import feign.Response;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.servlet.DispatcherServlet;

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
        initLocalClient(url);
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

    private void initLocalClient(String url) {
        URL targetUrl = URL.valueOf(url);
        try {

            this.client = Exchangers.connect(targetUrl, new ExchangeHandlerDispatcher() {
                @Override
                public void received(Channel channel, Object message) {
                    System.out.println("收到来自服务端的返回值");
                    super.received(channel, message);
                }
            });
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        }
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


