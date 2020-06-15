package com.withwiz.plankton.network.server.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Default TCP server class.<BR>
 * Created by uni4love on 2016. 12. 13..
 */
public class DefaultNettyTcpServer extends AbstractNettyTcpServer {
    /**
     * property: netty.nativeio
     */
    boolean isUseNativeIO = false;

    /**
     * property: netty.port
     */
    int port = 18080;

    /**
     * property: netty.threads.acceptor
     */
    int acceptorThreadSize = 10;

    /**
     * property: netty.threads.worker
     */
    int workerThreadSize = 10;

    /**
     * property: netty.backlog
     */
    int backlogSize = 100;

    /**
     * constructor
     */
    public DefaultNettyTcpServer() {
    }

    @Override
    public boolean isUseNativeIO() {
        log.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getAcceptorThreadSize() {
        log.debug("acceptorThreadSize: {}", acceptorThreadSize);
        return acceptorThreadSize;
    }

    @Override
    public int getWorkerThreadSize() {
        log.debug("workerThreadSize: {}", workerThreadSize);
        return workerThreadSize;
    }

    @Override
    public int getBacklogSize() {
        log.debug("backlogSize: {}", backlogSize);
        return backlogSize;
    }

    @Override
    public ChannelHandler getServiceHandler() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) {
                ChannelPipeline cp = sc.pipeline();
                cp.addLast(new LoggingHandler(LogLevel.INFO));
                //add handlers like LoggingHandler...

            }
        };
    }
}
