package com.alibaba.dubbo.consumer.client;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author :Lictory
 * @date : 2024/08/13
 */
public class UrlResolver {

    //TODO add ExchangerClient into

    public static String resolveOriginalUrl(LoadBalancerClient loadBalancerClient, String url) {
        URL result = null;
        try {
            result = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String serviceId = result.getHost();
        ServiceInstance choose = loadBalancerClient.choose(serviceId);
        int nettyPort = Integer.parseInt(choose.getMetadata().get("rpc.netty.port"));
        return "exchange://" + choose.getHost() + ":" + nettyPort;
    }

    public static String getPathFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            StringBuilder path = new StringBuilder();
            path.append(url.getPath());
            if (url.getQuery() != null) {
                path.append("?").append(url.getQuery());
            }
            if (url.getRef() != null) {
                path.append("#").append(url.getRef());
            }
            return path.toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + urlString, e);
        }
    }
}
