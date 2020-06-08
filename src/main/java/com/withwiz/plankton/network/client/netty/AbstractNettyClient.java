package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.client.AbstractClient;
import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * implements for netty client
 */
public abstract class AbstractNettyClient extends AbstractClient<ByteBuf> {
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
     * user handler
     */
    protected ChannelInboundHandlerAdapter handler = null;

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
    public void send(ByteBuf data) throws Exception {
        if (!isConnected()) {
            throw new Exception("Not connected yet.");
        }
        channel.writeAndFlush(data);
    }

    public void send(byte[] data) throws Exception {
        send(Unpooled.copiedBuffer(data));
    }

    @Override
    public boolean isConnected() {
        return channel == null || !channel.isActive() ? false : true;
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

    public void setHandler(ChannelInboundHandlerAdapter handler) {
        this.handler = handler;
    }

    /**
     * get ServerBootstrap for netty.<BR>
     *
     * @return ServerBootstrap
     */
    public abstract Bootstrap getBootstrap();

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
    public abstract ChannelHandler getServiceHandler();

    /**
     * connect to server.<BR>
     *
     * @param abstractBootstrap Bootstrap
     */
    public abstract void connect(Bootstrap abstractBootstrap) throws Exception;
}
