package com.withwiz.plankton.network.server.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * TCP setup class for netty framework.<BR>
 * Created by uni4love on 2016. 12. 13..
 */
public abstract class AbstractNettyTcpServer extends AbstractNettyServer {
    /**
     * event loop group: acceptor
     */
    private EventLoopGroup acceptorEventLoopGroup = null;

    @Override
    public void start(AbstractBootstrap abstractBootstrap) {
        try {
            ChannelFuture channelFuture = abstractBootstrap.bind(getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("message: ", e);
            stop();
        }
    }

    @Override
    public AbstractBootstrap getBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(getAcceptorEventLoopGroup(getAcceptorThreadSize()), getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getServerSocketChannelClass(isUseNativeIO(), false))
                .option(ChannelOption.SO_BACKLOG, getBacklogSize())
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(getServiceHandler());
        return serverBootstrap;
    }

    /**
     * return a acceptor(boss?) EventLoopGroup for netty.<BR>
     *
     * @return EventLoopGroup
     */
    public EventLoopGroup getAcceptorEventLoopGroup(int threadSize) {
        if (acceptorEventLoopGroup == null) {
            acceptorEventLoopGroup = NettyNetworkUtil.createEventLoopGroup(threadSize, isUseNativeIO());
        }
        return acceptorEventLoopGroup;
    }

    @Override
    public void stop() {
        if (acceptorEventLoopGroup != null)
            acceptorEventLoopGroup.shutdownGracefully();
        super.stop();
    }

    /**
     * get acceptor(boss?) thread size.<BR>
     *
     * @return size
     */
    public abstract int getAcceptorThreadSize();

    /**
     * get backlog size.<BR>
     *
     * @return size
     */
    public abstract int getBacklogSize();
}
