package com.withwiz.plankton.server.netty.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerDomainSocketChannel;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerDomainSocketChannel;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Server utility for netty
 */
public class ServerUtil {
    /**
     * create a acceptor(boss?) EventLoopGroup for netty.<BR>
     *
     * @return EventLoopGroup
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
     * return ServerChannelClass for current platform(Windows or Linux or MacOS
     *
     * @return ServerChannel class
     */
    public static Class getServerChannelClass(boolean isUseNativeIO, boolean isUseUnixDomainSocket) {
        if (isUseNativeIO && Epoll.isAvailable()) {
            return isUseUnixDomainSocket ? EpollServerDomainSocketChannel.class : EpollServerSocketChannel.class;
        } else if (isUseNativeIO && KQueue.isAvailable()) {
            return isUseUnixDomainSocket ? KQueueServerDomainSocketChannel.class : KQueueServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }
}
