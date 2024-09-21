package com.alibaba.cloud.rpc.server;

import com.alibaba.cloud.rpc.utils.SpringUtil;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author :Lictory
 * @date : 2024/08/31
 */

public class RpcDispatcherServlet extends DispatcherServlet {
    public RpcDispatcherServlet() {
        super();
        // 初始化mapping
        onRefresh(SpringUtil.applicationContext);
    }
}
