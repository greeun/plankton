package com.withwiz.plankton.network.client.netty;

import io.netty.channel.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default UnixDomainSocket client class.<BR>
 */
public class DefaultNettyUnixDomainSocketClient extends AbstractNettyUnixDomainSocketClient {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(DefaultNettyUnixDomainSocketClient.class);

    /**
     * property: netty.uds
     */
    boolean isUseUds = true;

    /**
     * property: netty.nativeio
     */
    boolean isUseNativeIO = false;

    /**
     * property: netty.socket.path
     */
    String socketPath = "/tmp/uds.sock";

    /**
     * property: netty.threads.worker
     */
    int workerThreadSize = 10;

    /**
     * constructor
     */
    public DefaultNettyUnixDomainSocketClient(boolean isUseUds, String socketPath, boolean isUseNativeIO) {
        this.isUseUds = isUseUds;
        this.socketPath = socketPath;
        this.isUseNativeIO = isUseNativeIO;
    }

    /**
     * constructor
     */
    public DefaultNettyUnixDomainSocketClient(int workerThreadSize, boolean isUseUds, String socketPath, boolean isUseNativeIO) {
        this(isUseUds, socketPath, isUseNativeIO);
        this.workerThreadSize = workerThreadSize;
    }

    public boolean isUseUds() {
        logger.debug("isUds: {}", isUseUds);
        return isUseUds;
    }

    public boolean isUseNativeIO() {
        logger.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    public String getSocketPath() {
        logger.debug("socketPath: {}", socketPath);
        return socketPath;
    }

    public int getWorkerThreadSize() {
        logger.debug("workerThreadSize: {}", workerThreadSize);
        return workerThreadSize;
    }

    @Override
    public boolean isUds() {
        return true;
    }

    @Override
    protected ChannelHandler getServiceHandler() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel sc) {
                ChannelPipeline cp = sc.pipeline();
                cp.addLast(new LoggingHandler(LogLevel.DEBUG));
//            cp.addLast(new ByteArrayDecoder());
//            cp.addLast(new ByteArrayEncoder());
                if(handler == null) {
                    logger.error("User handler is NULL!");
                } else {
                    cp.addLast(handler);
                }
            }
        };
    }
}
