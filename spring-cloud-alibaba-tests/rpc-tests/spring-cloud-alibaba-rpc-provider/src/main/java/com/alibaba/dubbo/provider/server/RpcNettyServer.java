package com.alibaba.dubbo.provider.server;

/**
 * @author :Lictory
 * @date : 2024/08/15
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class RpcNettyServer {

    private final String host;
    private final int port;

    public RpcNettyServer(String host,int port) {
        this.host=host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());  // 解码器
                            pipeline.addLast(new StringEncoder());  // 编码器
//                            pipeline.addLast(new NettyServerHandler());  // 自定义处理器
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(host,port).sync();
            System.out.println("Server started on port " + port);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        new NettyServer(8080).start();
//    }
}

