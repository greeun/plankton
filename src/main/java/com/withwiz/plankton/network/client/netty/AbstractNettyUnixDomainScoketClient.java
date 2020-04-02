package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.unix.DomainSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * netty client with Unix Domain Socket
 */
public abstract class AbstractNettyUnixDomainScoketClient extends AbstractNettyClient {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(AbstractNettyUnixDomainScoketClient.class);

    /**
     * initialize
     */
    public void connect(Bootstrap abstractBootstrap) throws InterruptedException {
        try {
            channel = abstractBootstrap.connect(getSocketAddress()).sync().channel();
        } catch (InterruptedException e) {
            logger.error("message: {}", e);
            disconnect();
            throw e;
        }
    }

    @Override
    public Bootstrap getBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), isUds()))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
        return bootstrap;
    }

    @Override
    public SocketAddress getSocketAddress() {
        return new DomainSocketAddress(getSocketPath());
    }

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
