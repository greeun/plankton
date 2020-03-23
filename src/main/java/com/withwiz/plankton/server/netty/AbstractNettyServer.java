package com.withwiz.plankton.server.netty;

import com.withwiz.plankton.server.AbstractServer;
import com.withwiz.plankton.server.netty.util.ServerUtil;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Setup class for using netty server framework.<BR>
 * Created by uni4love on 2016. 12. 12..
 */
public abstract class AbstractNettyServer extends AbstractServer {
    /**
     * loggger
     */
    private final Logger logger = LoggerFactory.getLogger(AbstractNettyServer.class);

    /**
     * event loop group: worker
     */
    private EventLoopGroup workerEventLoopGroup = null;

    @Override
    public void start() {
        start(getBootstrap());
    }

    @Override
    public void stop() {
        if (workerEventLoopGroup != null)
            workerEventLoopGroup.shutdownGracefully();
    }

    /**
     * create a EventLoopGroup for netty.<BR>
     *
     * @param threadSize thread size
     * @return EventLoopGroup
     */
    public EventLoopGroup getWorkerEventLoopGroup(int threadSize) {
        if (workerEventLoopGroup == null) {
            workerEventLoopGroup = ServerUtil.createEventLoopGroup(threadSize, isUseNativeIO());
        }
        return workerEventLoopGroup;
    }

    /**
     * get ServerBootstrap for netty.<BR>
     *
     * @return ServerBootstrap
     */
    protected abstract AbstractBootstrap getBootstrap();

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
     * get worker thread size.<BR>
     *
     * @return size
     */
    protected abstract int getWorkerThreadSize();

    /**
     * get service handler for netty.<BR>
     *
     * @return channel handler
     */
    protected abstract ChannelHandler getServiceHandler();

    /**
     * start server.<BR>
     *
     * @param abstractBootstrap
     */
    protected abstract void start(AbstractBootstrap abstractBootstrap);
}
