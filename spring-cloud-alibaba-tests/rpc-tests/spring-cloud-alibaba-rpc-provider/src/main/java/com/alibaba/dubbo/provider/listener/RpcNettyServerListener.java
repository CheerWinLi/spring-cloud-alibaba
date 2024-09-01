package com.alibaba.dubbo.provider.listener;

import com.alibaba.dubbo.provider.config.RpcDispatcherServlet;
import com.alibaba.dubbo.provider.server.CaptureHttpServletResponseWrapper;
import com.alibaba.dubbo.provider.server.CoyoteRequestBuilder;
import com.alibaba.dubbo.provider.server.MockHttpServletRequestConverter;
import com.alibaba.dubbo.provider.server.RpcToHttpServletRequestWrapper;
import com.alibaba.rpc.common.RpcConstance;
import com.alibaba.rpc.metadata.HttpMetadata;
import com.alibaba.rpc.metadata.HttpRpcResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponseFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author :Lictory
 * @date : 2024/08/18
 */
@Component
public class RpcNettyServerListener implements ApplicationListener<ApplicationReadyEvent> {
    private ExchangeServer server;

    @Value("${rpc.netty.port}")
    private String port;

    @Value("${rpc.netty.host}")
    private String host;
    private RpcDispatcherServlet dispatcherServlet;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.dispatcherServlet = new RpcDispatcherServlet();
        initLocalServer(host, Integer.parseInt(port));
    }

    private void initLocalServer(String host, int port) {
        URL url = URL.valueOf("exchange://" + host + ":" + port);
        url = url.addParameter("serialization", "hessian2");
        try {
            this.server = Exchangers.bind(url, new ExchangeHandlerAdapter(FrameworkModel.defaultModel()) {
                @Override
                public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) {
                    System.out.println("接收到客户端请求");
                    HttpMetadata httpMetadata = (HttpMetadata) msg;
                    String url = httpMetadata.getUrl();
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



