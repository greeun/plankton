package com.withwiz.plankton.network.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Default UnixDomainSocket client class.<BR>
 */
public class DefaultNettyUnixDomainSocketClient extends AbstractNettyUnixDomainSocketClient {
    /**
     * property: netty.uds
     */
    boolean isUseUds = true;

    /**
     * property: netty.nativeio
     */
    boolean isUseNativeIO = false;

    /**
     * property: netty.threads.worker
     */
    int workerThreadSize = 10;

    /**
     * constructor
     */
    public DefaultNettyUnixDomainSocketClient(String socketPath, boolean isUseUds, boolean isUseNativeIO) {
        socketAddress = new DomainSocketAddress(socketPath);
        log.debug("this is constructor3.");
        this.isUseUds = isUseUds;
        this.isUseNativeIO = isUseNativeIO;
    }

    /**
     * constructor
     */
    public DefaultNettyUnixDomainSocketClient(String socketPath, boolean isUseUds, boolean isUseNativeIO, int workerThreadSize) {
        this(socketPath, isUseUds, isUseNativeIO);
        this.workerThreadSize = workerThreadSize;
    }

    @Override
    public boolean isUseNativeIO() {
        log.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    @Override
    public int getWorkerThreadSize() {
        log.debug("workerThreadSize: {}", workerThreadSize);
        return workerThreadSize;
    }

    @Override
    public boolean isUseUds() {
        log.debug("isUds: {}", isUseUds);
        return isUseUds;
    }

    @Override
    public ChannelHandler getServiceHandler() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel sc) {
                ChannelPipeline cp = sc.pipeline();
                cp.addLast(new LoggingHandler(LogLevel.DEBUG));
//            cp.addLast(new ByteArrayDecoder());
//            cp.addLast(new ByteArrayEncoder());
                if(handler == null) {
                    log.error("User handler is NULL!");
                } else {
                    cp.addLast(handler);
                }
            }
        };
    }
}
