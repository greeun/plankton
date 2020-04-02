package com.withwiz.plankton.network.server.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default UDP server class.<BR>
 * Created by uni4love on 2016. 12. 16..
 */
public class DefaultNettyUdpServer extends AbstractNettyUdpServer {

    /**
     * loggger
     */
    private final Logger logger = LoggerFactory.getLogger(DefaultNettyUdpServer.class);

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
    public DefaultNettyUdpServer() {
    }

    @Override
    public boolean isUseNativeIO() {
        logger.debug("isUseNativeIO: {}", isUseNativeIO);
        return isUseNativeIO;
    }

    @Override
    public int getWorkerThreadSize() {
        logger.debug("workerThreadSize: {}", workerThreadSize);
        return workerThreadSize;
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

    @Override
    public int getPort() {
        return port;
    }
}
