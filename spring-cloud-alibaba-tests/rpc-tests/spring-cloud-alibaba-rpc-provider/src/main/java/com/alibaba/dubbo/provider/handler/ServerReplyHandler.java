package com.alibaba.dubbo.provider.handler;


import com.alibaba.dubbo.consumer.entity.User;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.support.Replier;


/**
 * @author :Lictory
 * @date : 2024/08/14
 */

public class ServerReplyHandler implements Replier {

    @Override
    public Object reply(ExchangeChannel channel, Object request) throws RemotingException {
        System.out.println("收到来自" + channel.getRemoteAddress() + "的请求" + request.toString());
        User user=new User();
        user.setId(6);
        return user;
    }
}
