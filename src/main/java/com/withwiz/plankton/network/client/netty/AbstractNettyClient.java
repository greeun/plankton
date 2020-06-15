package com.withwiz.plankton.network.client.netty;

import com.withwiz.plankton.network.client.AbstractClient;
import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
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
    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * bootstrap
     */
    protected Bootstrap bootstrap = null;

    /**
     * socket address
     */
    protected SocketAddress socketAddress = null;

    /**
     * event loop group: worker
     */
    protected EventLoopGroup workerEventLoopGroup = null;

    /**
     * channel
     */
    protected Channel channel = null;

    /**
     * user handler
     */
    protected ChannelInboundHandlerAdapter handler = null;

    /**
     * constructor
     */
    public AbstractNettyClient() {
//        bootstrap = createBootstrap();
    }

    /**
     * connect
     */
    public void connect(Bootstrap bootstrap) throws Exception {
        try {
            channel = bootstrap.connect(getSocketAddress()).sync().channel();
        } catch (Exception e) {
            disconnect();
            throw e;
        }
    }

    /**
     * connect
     */
    @Override
    public void connect() throws Exception {
        if (bootstrap == null) {
            bootstrap = createBootstrap();
        }
        log.debug("Bootstrap: {}", bootstrap);
        connect(getBootstrap());
    }

    @Override
    public void disconnect() {
        if (workerEventLoopGroup != null)
            workerEventLoopGroup.shutdownGracefully();
    }

    public void send(byte[] data) throws Exception {
        send(Unpooled.copiedBuffer(data));
    }

    @Override
    public void send(ByteBuf data) throws Exception {
        if (!isConnected()) {
            throw new Exception("Not connected yet.");
        }
        channel.writeAndFlush(data);
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
     * get bootstrap
     *
     * @return Bootstrap
     */
    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    /**
     * get SocketAddress
     *
     * @return SocketAddress
     */
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    /**
     * set SocketAddress
     *
     * @param socketAddress SocketAddress
     */
    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

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
     * create and return Bootstrap
     *
     * @return Bootstrap
     */
    public abstract Bootstrap createBootstrap();

    /**
     * get service handler for netty.<BR>
     *
     * @return channel handler
     */
    public abstract ChannelHandler getServiceHandler();
}
