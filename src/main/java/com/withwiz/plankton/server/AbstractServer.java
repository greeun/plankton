package com.withwiz.plankton.server;

/**
 * Server common interface.<BR>
 * Created by uni4love on 2016. 12. 12..
 */
abstract public class AbstractServer implements IServer {
    /**
     * port
     */
    protected int port = 10001;

    @Override
    public void start(int port) {
        setPort(port);
        start();
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * get port
     *
     * @return port number
     */
    public abstract int getPort();
}
