package com.withwiz.plankton.network;

import com.withwiz.plankton.network.server.netty.util.NettyNetworkUtil;
import io.netty.util.internal.PlatformDependent;

/**
 * Network utility
 */
public class NetworkUtil extends NettyNetworkUtil {
    public static boolean isMacOs() {
        return PlatformDependent.isOsx();
    }

    public static boolean isWindows() {
        return PlatformDependent.isWindows();
    }

    public static boolean isAndroid() {
        return PlatformDependent.isAndroid();
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0 ? true : false;
    }
}
