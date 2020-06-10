package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty client with Unix Domain Socket
 */
public abstract class AbstractNettyUnixDomainSocketClient extends AbstractNettyClient {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(AbstractNettyUnixDomainSocketClient.class);

    @Override
    public Bootstrap createBootstrap() {
        logger.warn("this is createBootstrap.");
        return new Bootstrap().group(getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), isUds()))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
    }

    /**
     * get use uds(Unix Domain Socket).<BR>
     *
     * @return true/false
     */
    public abstract boolean isUds();
}
