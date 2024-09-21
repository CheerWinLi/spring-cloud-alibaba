package com.alibaba.cloud.rpc.server;


import com.alibaba.cloud.rpc.metadata.HttpMetadata;
import com.alibaba.cloud.rpc.metadata.HttpRpcResponse;
import com.alibaba.cloud.rpc.utils.MockHttpServletRequestConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author :Lictory
 * @date : 2024/08/18
 */
@Component
public class RpcNettyServerListener implements ApplicationListener<ApplicationReadyEvent> {
    private ExchangeServer server;

    @Value("${spring.rpc.netty.port}")
    private String port;

    @Value("${spring.rpc.netty.host}")
    private String host;

    private RpcDispatcherServlet dispatcherServlet;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initLocalServer(host, Integer.parseInt(port));
        this.dispatcherServlet = new RpcDispatcherServlet();
    }

    private void initLocalServer(String host, int port) {
        URL url = URL.valueOf("exchange://" + host + ":" + port);
        try {
            this.server = Exchangers.bind(url, new ExchangeHandlerAdapter(FrameworkModel.defaultModel()) {
                @Override
                public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) {
                    HttpMetadata httpMetadata = (HttpMetadata) msg;
                    String url = httpMetadata.getUrl();
                    System.out.println(url);
                    MockHttpServletRequest request = MockHttpServletRequestConverter.getMockHttpServletRequest(httpMetadata, url);
                    MockHttpServletResponse response = new MockHttpServletResponse();
                    try {
                        dispatcherServlet.service(request, response);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    HttpRpcResponse httpRpcResponse = new HttpRpcResponse();
                    httpRpcResponse.setStatusCode(response.getStatus());
                    httpRpcResponse.setBody(response.getContentAsByteArray());
                    httpRpcResponse.setReasonPhrase(response.getErrorMessage());
                    httpRpcResponse.setHeaders(convertHeaders(response));
                    return CompletableFuture.completedFuture(httpRpcResponse);
                }
            });
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Collection<String>> convertHeaders(HttpServletResponse httpServletResponse) {
        Map<String, Collection<String>> feignHeaders = new HashMap<>();
        // 遍历所有头部名称
        for (String headerName : httpServletResponse.getHeaderNames()) {
            // 获取对应头部名称的所有值
            Collection<String> headerValues = httpServletResponse.getHeaders(headerName);
            // 将其加入到 feignHeaders 中
            feignHeaders.put(headerName, new ArrayList<>(headerValues));
        }
        return feignHeaders;
    }
}



