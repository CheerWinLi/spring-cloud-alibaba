import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.Log;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.RemotingServer;
import org.apache.dubbo.remoting.api.connection.AbstractConnectionClient;
import org.apache.dubbo.remoting.api.pu.AbstractPortUnificationServer;
import org.apache.dubbo.remoting.api.pu.DefaultPuHandler;
import org.apache.dubbo.remoting.exchange.*;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerDispatcher;
import org.apache.dubbo.remoting.transport.ChannelHandlerAdapter;
import org.apache.dubbo.remoting.transport.netty.NettyPortUnificationServer;
import org.apache.dubbo.remoting.transport.netty4.NettyConnectionClient;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;

import org.apache.dubbo.rpc.protocol.tri.rest.support.spring.HandlerInterceptorAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.dubbo.common.constants.CommonConstants.*;

/**
 * @author :Lictory
 * @date : 2024/08/27
 */
public class RpcExchangersTest {
    private static URL url;

    private static ExchangeClient client;
    private static ExchangeServer server;

//    @BeforeAll
//    public static void init() throws RemotingException {
//        int port = NetUtils.getAvailablePort();
//        url = URL.valueOf("empty://127.0.0.1:" + port + "?foo=bar");
//        ApplicationModel applicationModel = ApplicationModel.defaultModel();
//        ApplicationConfig applicationConfig = new ApplicationConfig("provider-app");
//        applicationConfig.setExecutorManagementMode(EXECUTOR_MANAGEMENT_MODE_DEFAULT);
//        applicationModel.getApplicationConfigManager().setApplication(applicationConfig);
//        ConfigManager configManager = new ConfigManager(applicationModel);
//        configManager.setApplication(applicationConfig);
//        configManager.getApplication();
//        applicationModel.setConfigManager(configManager);
//        url = url.setScopeModel(applicationModel);
//        ModuleModel moduleModel = applicationModel.getDefaultModule();
//        url = url.putAttribute(CommonConstants.SCOPE_MODEL, moduleModel);
//    }

//    @Test
//    void test() throws RemotingException {
//        AbstractPortUnificationServer server = new NettyPortUnificationServer(url, new ExchangeHandlerAdapter(FrameworkModel.defaultModel()) {
//            @Override
//            public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) throws RemotingException {
//                System.out.println(msg);
//                return CompletableFuture.completedFuture("收到了哥们");
//            }
//        });
//
//        server.send("1");
//    }
//

//    @BeforeAll
    public static void init(){
        url=URL.valueOf("tri://127.0.0.1:"+ NetUtils.getAvailablePort());
        url.addParameter(PROTOCOL_KEY,TRIPLE);
    }
//    @Test
static void testExchangers() throws RemotingException, ExecutionException, InterruptedException {
        init();
        getExchangersServer();
        getExchangersClient();
//    CompletableFuture<Object> test = client.request("test");
//    System.out.println(test.get());
}

    static void getExchangersServer() throws RemotingException {
       server= Exchangers.bind(url, new ExchangeHandlerAdapter(FrameworkModel.defaultModel()) {
            @Override
            public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) throws RemotingException {
                System.out.println(msg);
                return CompletableFuture.completedFuture("received request"+msg);
            }
        });
    }
    static void getExchangersClient() throws RemotingException {
        client= Exchangers.connect(url);
    }

    public static void main(String[] args) throws RemotingException, ExecutionException, InterruptedException {
        testExchangers();
    }
}
