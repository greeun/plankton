package com.withwiz.plankton.interfaces.network.server;

/**
 * Server component interface.<BR>
 * Created by uni4love on 2016. 12. 12..
 */
public interface IServer {
    /**
     * server start method
     */
    void start();

    /**
     * server start method
     *
     * @param port binding port
     */
    void start(int port);

    /**
     * server stop method
     */
    void stop();
}
