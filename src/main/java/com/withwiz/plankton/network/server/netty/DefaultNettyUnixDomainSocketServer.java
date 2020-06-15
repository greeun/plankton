package com.withwiz.plankton.network.server.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Default UnixDomain server class.<BR>
 * Created by uni4love on 2019. 12. 13..
 */
public class DefaultNettyUnixDomainSocketServer extends AbstractNettyUnixDomainSocketServer {
    /**
     * property: netty.uds
     */
    boolean isUds = true;

    /**
     * property: netty.nativeio
     */
    boolean isUseNativeIO = false;

    /**
     * property: netty.socket.path
     */
    String socketPath = "/tmp/uds.sock";

    /**
     * property: netty.port
     */
    int port;

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
    public DefaultNettyUnixDomainSocketServer() {

    }

    @Override
    public boolean isUds() {
        log.debug("isUds: {}", isUds);
        return isUds;
    }

    @Override
    public boolean isUseNativeIO() {
        log.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    @Override
    public String getSocketPath() {
        log.debug("socketPath: {}", socketPath);
        return socketPath;
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
