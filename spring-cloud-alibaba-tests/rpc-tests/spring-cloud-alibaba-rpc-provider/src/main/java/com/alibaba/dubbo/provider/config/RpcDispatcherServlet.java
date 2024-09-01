package com.alibaba.dubbo.provider.config;

import com.alibaba.dubbo.provider.util.SpringUtil;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author :Lictory
 * @date : 2024/09/01
 */
public class RpcDispatcherServlet extends DispatcherServlet {
    public RpcDispatcherServlet() {
        super();
        // 初始化mapping
        onRefresh(SpringUtil.applicationContext);
    }
}
