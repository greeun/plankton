package com.withwiz.plankton.scheduler;

import java.util.Map;

/**
 * scheduler interface
 */
public interface IScheduler {
    /**
     * start schedule
     */
    void begin();

    /**
     * pause schedule
     */
    void pause();

    /**
     * resume schedule
     */
    void resume();

    /**
     * stop schedule
     */
    void stop();

    /**
     * set data for schedule
     *
     * @param data data map
     */
    void setData(Map data);
}
