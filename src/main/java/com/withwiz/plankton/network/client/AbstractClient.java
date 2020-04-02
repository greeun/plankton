package com.withwiz.plankton.network.client;

import com.withwiz.plankton.interfaces.network.client.IClient;

/**
 * Client common abstract class.<BR>
 *
 * @param <T>
 */
public abstract class AbstractClient<T> implements IClient<T> {
    /**
     * return connected status
     *
     * @return true/false
     */
    public abstract boolean isConnected();
}
