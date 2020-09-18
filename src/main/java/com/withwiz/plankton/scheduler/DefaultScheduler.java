package com.withwiz.plankton.scheduler;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * scheduler
 */
public class DefaultScheduler implements IScheduler, Runnable {
    /**
     * logger
     */
    Logger log = (Logger)LoggerFactory.getLogger(DefaultScheduler.class.getName());

    /**
     * scheduled job
     */
    IScheduledJob scheduledJob = null;

    /**
     * interval: 1 seconds
     */
    int interval = 100;

    /**
     * running status
     */
    boolean isRunning = false;

    /**
     * data map
     */
    Map data = null;

    /**
     * constructor
     */
    public DefaultScheduler(IScheduledJob scheduledJob) {
        this.scheduledJob = scheduledJob;

    }

    /**
     * constructor
     */
    public DefaultScheduler(IScheduledJob scheduledJob, int interval) {
        this(scheduledJob);
        this.interval = interval;

    }

    @Override
    public void begin() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        isRunning = true;
        log.info("Scheduler started by interval: {}", interval);
        while(isRunning) {
            if(scheduledJob == null) {
                log.error("A Scheduler job is NULL!");
                break;
            }
            try {
                scheduledJob.execute(data);
            } catch (Exception e) {
                log.error("{}", e);
                isRunning = false;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                log.error("{}", e);
                Thread.currentThread().interrupt();
            }
        }
        log.info("Scheduler ended.");
    }

    public IScheduledJob getScheduledJob() {
        return scheduledJob;
    }

    public void setScheduledJob(IScheduledJob scheduledJob) {
        this.scheduledJob = scheduledJob;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {
        setRunning(false);
    }

    @Override
    public void setData(Map data) {

    }
}
