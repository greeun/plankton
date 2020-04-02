package com.withwiz.plankton.interfaces.network.client;

/**
 * client interface
 */
public interface IClient<T> {
    /**
     * connect
     */
    void connect() throws Exception;

    /**
     * disconnect
     */
    void disconnect();

    /**
     * send T to Weight Module
     *
     * @param data send data
     */
    void send(T data);
}
