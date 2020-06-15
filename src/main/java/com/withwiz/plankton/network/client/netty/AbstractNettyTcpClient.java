package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;

/**
 * netty client with TCP
 */
public abstract class AbstractNettyTcpClient extends AbstractNettyClient {

    @Override
    public Bootstrap createBootstrap() {
        return new Bootstrap().group(getWorkerEventLoopGroup(getWorkerThreadSize()))
                .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), false))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
    }
}
