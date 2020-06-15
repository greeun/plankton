package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;

/**
 * netty client with Unix Domain Socket
 */
public abstract class AbstractNettyUnixDomainSocketClient extends AbstractNettyClient {

    @Override
    public Bootstrap createBootstrap() {
        return new Bootstrap().group(getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), isUseUds()))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
    }

    /**
     * get use uds(Unix Domain Socket).<BR>
     *
     * @return true/false
     */
    public abstract boolean isUseUds();
}
