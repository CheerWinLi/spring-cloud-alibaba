import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.api.connection.AbstractConnectionClient;
import org.apache.dubbo.remoting.api.pu.AbstractPortUnificationServer;
import org.apache.dubbo.remoting.api.pu.DefaultPuHandler;
import org.apache.dubbo.remoting.exchange.*;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerDispatcher;
import org.apache.dubbo.remoting.transport.netty.NettyPortUnificationServer;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CompletableFuture;

import static org.apache.dubbo.common.constants.CommonConstants.EXECUTOR_MANAGEMENT_MODE_DEFAULT;

/**
 * @author :Lictory
 * @date : 2024/08/28
 */
public class RpcExchangersForTriTest {
    private static URL url;

    @BeforeAll
    public static void init() throws RemotingException {
        int port = NetUtils.getAvailablePort();
        url = URL.valueOf("tri://127.0.0.1:" + port);
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        ApplicationConfig applicationConfig = new ApplicationConfig("provider-app");
        applicationConfig.setExecutorManagementMode(EXECUTOR_MANAGEMENT_MODE_DEFAULT);
        applicationModel.getApplicationConfigManager().setApplication(applicationConfig);
        ConfigManager configManager = new ConfigManager(applicationModel);
        configManager.setApplication(applicationConfig);
        configManager.getApplication();
        applicationModel.setConfigManager(configManager);
        url = url.setScopeModel(applicationModel);
        ModuleModel moduleModel = applicationModel.getDefaultModule();
        url = url.putAttribute(CommonConstants.SCOPE_MODEL, moduleModel);
    }

    @Test
    public void test() throws RemotingException {
        AbstractPortUnificationServer server = new NettyPortUnificationServer(url, new ExchangeHandlerAdapter(FrameworkModel.defaultModel()) {
            @Override
            public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) throws RemotingException {
                System.out.println(msg);
                return CompletableFuture.completedFuture("GET");
            }
        });
        AbstractConnectionClient client = PortUnificationExchanger.connect(url, new DefaultPuHandler());
        client.send("test");
    }
}


