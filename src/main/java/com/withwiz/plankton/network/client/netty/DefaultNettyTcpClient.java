package com.withwiz.plankton.network.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

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
     * property: netty.threads.worker
     */
    int workerThreadSize = 10;

    /**
     * constructor
     */
    public DefaultNettyTcpClient(String host, int port, boolean isUseNativeIO) {
        if (host == null)
            socketAddress = new InetSocketAddress(port);
        else
            socketAddress = new InetSocketAddress(host, port);
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

    public int getWorkerThreadSize() {
        return workerThreadSize;
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
                if (handler == null) {
                    logger.error("User handler is NULL!");
                } else {
                    cp.addLast(handler);
                }
            }
        };
    }
}
