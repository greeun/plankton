package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.interfaces.network.client.INettyClient;
import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * implements for netty client
 */
public abstract class AbstractNettyClient implements INettyClient<byte[]> {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(AbstractNettyClient.class);

    /**
     * event loop group: worker
     */
    private EventLoopGroup workerEventLoopGroup = null;

    /**
     * channel
     */
    protected Channel channel = null;

    /**
     * connect
     */
    public void connect() throws Exception {
        connect(getBootstrap());
    }

    @Override
    public void disconnect() {
        if (workerEventLoopGroup != null)
            workerEventLoopGroup.shutdownGracefully();
    }

    @Override
    public void send(byte[] data) {
        if (!isConnected()) {
            try {
                connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        clientHandler.getChannelHandlerContext().writeAndFlush(data);
        channel.writeAndFlush(Unpooled.copiedBuffer(data));
    }

    @Override
    public boolean isConnected() {
        return channel == null ? false : true;
    }

    /**
     * create a EventLoopGroup for netty.<BR>
     *
     * @param threadSize thread size
     * @return EventLoopGroup
     */
    public EventLoopGroup getWorkerEventLoopGroup(int threadSize) {
        if (workerEventLoopGroup == null) {
            workerEventLoopGroup = NettyNetworkUtil.createEventLoopGroup(threadSize, isUseNativeIO());
        }
        return workerEventLoopGroup;
    }

    /**
     * get ServerBootstrap for netty.<BR>
     *
     * @return ServerBootstrap
     */
    protected abstract Bootstrap getBootstrap();

    /**
     * get SocketAddress.<BR>
     *
     * @return SocketAddress
     */
    public abstract SocketAddress getSocketAddress();

    /**
     * get use native io.<BR>
     *
     * @return true/false
     */
    public abstract boolean isUseNativeIO();

    /**
     * get worker thread size.
     *
     * @return worker thread size
     */
    public abstract int getWorkerThreadSize();

    /**
     * get service handler for netty.<BR>
     *
     * @return channel handler
     */
    protected abstract ChannelHandler getServiceHandler();

    /**
     * connect to server.<BR>
     *
     * @param abstractBootstrap
     */
    protected abstract void connect(Bootstrap abstractBootstrap) throws Exception;
}
