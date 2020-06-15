package com.withwiz.plankton.network.server.netty;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * TCP setup class for netty framework.<BR>
 * Created by uni4love on 2016. 12. 15..
 */
public abstract class AbstractNettyUdpServer extends AbstractNettyServer {
    @Override
    public void start(AbstractBootstrap abstractBootstrap) {
        try {
            ChannelFuture channelFuture = abstractBootstrap.bind(getPort()).sync();
            channelFuture.channel().closeFuture().await();
        } catch (Exception e) {
            log.error("message: ", e);
            stop();
        }
    }

    @Override
    public AbstractBootstrap getBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(getServiceHandler());
        return bootstrap;
    }

//    /**
//     * get InetAddress for UDP
//     *
//     * @return InetAddress
//     */
//    public abstract InetAddress getInetAddress();
}
