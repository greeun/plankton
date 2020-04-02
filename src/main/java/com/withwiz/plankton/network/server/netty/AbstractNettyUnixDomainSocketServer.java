package com.withwiz.plankton.network.server.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Unix Domain Socket setup class for netty framework.<BR>
 * Created by uni4love on 2019. 12. 13..
 */
public abstract class AbstractNettyUnixDomainSocketServer extends AbstractNettyServer {
    /**
     * loggger
     */
    private final Logger logger = LoggerFactory.getLogger(AbstractNettyUnixDomainSocketServer.class);

    /**
     * event loop group: acceptor
     */
    private EventLoopGroup acceptorEventLoopGroup = null;

    @Override
    public void start(AbstractBootstrap abstractBootstrap) {
        try {
            ChannelFuture channelFuture = abstractBootstrap.bind(getSocketAddress()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("message: ", e);
            stop();
        }
    }

    @Override
    public AbstractBootstrap getBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(getAcceptorEventLoopGroup(getAcceptorThreadSize()), getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getServerSocketChannelClass(isUseNativeIO(), isUds()))
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

    public SocketAddress getSocketAddress() {
        return isUds() ? new DomainSocketAddress(getSocketPath()) : new InetSocketAddress(getPort());
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

    /**
     * get socket file path.<BR>
     *
     * @return socket file path
     */
    public abstract String getSocketPath();

    /**
     * get use uds(Unix Domain Socket).<BR>
     *
     * @return true/false
     */
    public abstract boolean isUds();
}
