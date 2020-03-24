package com.withwiz.plankton.server.netty.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.*;
import io.netty.channel.kqueue.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Network utility for netty
 */
public class NettyNetworkUtil {
    /**
     * create a acceptor(boss?) EventLoopGroup for netty.<BR>
     *
     * @param threadSize    thread size
     * @param isUseNativeIO true/false
     * @return EventLoopGroup netty EventLoopGroup
     */
    public static EventLoopGroup createEventLoopGroup(int threadSize, boolean isUseNativeIO) {
        if (isUseNativeIO && Epoll.isAvailable()) {
            return new EpollEventLoopGroup(threadSize);
        } else if (isUseNativeIO && KQueue.isAvailable()) {
            return new KQueueEventLoopGroup(threadSize);
        }
        return new NioEventLoopGroup(threadSize);
    }

    /**
     * return ServerSocketChannel class for current platform(Windows or Linux or MacOS)
     *
     * @param isUseNativeIO         true/false
     * @param isUseUnixDomainSocket true/false
     * @return ServerSocketChannel class
     */
    public static Class getServerSocketChannelClass(boolean isUseNativeIO, boolean isUseUnixDomainSocket) {
        if (isUseNativeIO && Epoll.isAvailable()) {
            return isUseUnixDomainSocket ? EpollServerDomainSocketChannel.class : EpollServerSocketChannel.class;
        } else if (isUseNativeIO && KQueue.isAvailable()) {
            return isUseUnixDomainSocket ? KQueueServerDomainSocketChannel.class : KQueueServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }

    /**
     * return SocketChannel class for current platform(Windows or Linux or MacOS)
     *
     * @param isUseNativeIO         true/false
     * @param isUseUnixDomainSocket true/false
     * @return SocketChannel class
     */
    public static Class getSocketChannelClass(boolean isUseNativeIO, boolean isUseUnixDomainSocket) {
        if (isUseNativeIO && Epoll.isAvailable()) {
            return isUseUnixDomainSocket ? EpollDomainSocketChannel.class : EpollSocketChannel.class;
        } else if (isUseNativeIO && KQueue.isAvailable()) {
            return isUseUnixDomainSocket ? KQueueDomainSocketChannel.class : KQueueSocketChannel.class;
        }
        return NioSocketChannel.class;
    }

    /**
     * return SocketChannel class for current platform(Windows or Linux or MacOS)
     *
     * @param isUseNativeIO         true/false
     * @param isUseUnixDomainSocket true/false
     * @return SocketChannel class
     */
    public static Class getClientSocketChannelClass(boolean isUseNativeIO, boolean isUseUnixDomainSocket) {
        return getSocketChannelClass(isUseNativeIO, isUseUnixDomainSocket);
    }
}
