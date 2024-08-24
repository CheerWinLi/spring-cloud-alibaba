package com.alibaba.dubbo.provider.config;

import com.alibaba.cloud.nacos.registry.NacosRegistration;

import com.alibaba.rpc.metadata.HttpMetadata;
import com.alibaba.rpc.metadata.HttpRpcResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;

import org.apache.dubbo.common.URL;

import org.apache.dubbo.common.utils.NetUtils;

import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.RemotingServer;
import org.apache.dubbo.remoting.exchange.*;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;

import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.apache.dubbo.common.constants.CommonConstants.EXECUTOR_MANAGEMENT_MODE_DEFAULT;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author :Lictory
 * @date : 2024/08/12
 */

@Configuration
public class RpcRegistryConfiguration {

    @Autowired
    private NacosRegistration nacosRegistration;

    @PostConstruct
    public void init(){
        Map<String, String> metadata = new HashMap<>();
        int port = NetUtils.getAvailablePort();
//        initLocalServer(nacosRegistration.getHost(), port);
        System.setProperty("rpc.netty.port", String.valueOf(port));
        System.setProperty("rpc.netty.host",nacosRegistration.getHost());
        metadata.put("rpc.netty.port", String.valueOf(port));
        nacosRegistration.getMetadata().putAll(metadata);
    }
}
