package com.withwiz.plankton.network.client.netty;

import io.netty.channel.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default TCP client class.<BR>
 */
public class DefaultNettyTcpClient extends AbstractNettyTcpClient {
    /**
     * loggger
     */
    private static Logger logger = LoggerFactory.getLogger(DefaultNettyTcpClient.class);

    /**
     * property: netty.nativeio
     */
    boolean isUseNativeIO = false;

    /**
     * server port
     */
    int port = 18080;

    /**
     * property: netty.threads.worker
     */
    int workerThreadSize = 10;

    /**
     * constructor
     */
    public DefaultNettyTcpClient(int port, boolean isUseNativeIO) {
        this.port = port;
        this.isUseNativeIO = isUseNativeIO;
    }

    /**
     * constructor
     */
    public DefaultNettyTcpClient(int workerThreadSize, int port, boolean isUseNativeIO) {
        this(port, isUseNativeIO);
        this.workerThreadSize = workerThreadSize;
    }

    public boolean isUseNativeIO() {
        logger.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    public int getPort() {
        logger.debug("port: {}", port);
        return port;
    }

    public int getWorkerThreadSize() {
        logger.debug("workerThreadSize: {}", workerThreadSize);
        return workerThreadSize;
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
