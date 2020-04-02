package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * netty client with TCP
 */
public abstract class AbstractNettyTcpClient extends AbstractNettyClient {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(AbstractNettyTcpClient.class);

    /**
     * initialize
     */
    public void connect(Bootstrap abstractBootstrap) throws Exception {
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
                .channel(NettyNetworkUtil.getClientSocketChannelClass(isUseNativeIO(), false))
                .option(ChannelOption.SO_LINGER, 0)
                .handler(getServiceHandler());
        return bootstrap;
    }

    @Override
    public SocketAddress getSocketAddress() {
        return new InetSocketAddress(getPort());
    }

    /**
     * get port
     *
     * @return port number
     */
    public abstract int getPort();
}
