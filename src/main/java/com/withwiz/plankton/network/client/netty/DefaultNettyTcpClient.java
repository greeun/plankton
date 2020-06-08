package com.withwiz.plankton.network.client.netty;

import io.netty.channel.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

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
     * server host
     */
    String host = null;

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
    public DefaultNettyTcpClient(String host, int port, boolean isUseNativeIO) {
        this.host = host;
        this.port = port;
        this.isUseNativeIO = isUseNativeIO;
    }

    /**
     * constructor
     */
    public DefaultNettyTcpClient(String host, int port, int workerThreadSize, boolean isUseNativeIO) {
        this(host, port, isUseNativeIO);
        this.workerThreadSize = workerThreadSize;
    }

    public boolean isUseNativeIO() {
        return isUseNativeIO;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getWorkerThreadSize() {
        return workerThreadSize;
    }

    @Override
    protected SocketAddress getSocketAddress() {
        if (host != null)
            return new InetSocketAddress(getHost(), getPort());
        else
            return new InetSocketAddress(getPort());
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
                if (handler == null) {
                    logger.error("User handler is NULL!");
                } else {
                    cp.addLast(handler);
                }
            }
        };
    }
}
