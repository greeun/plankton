package com.withwiz.plankton.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Spring dynamic scheduler abstract class
 */
public abstract class ASpringDynamicScheduler implements IScheduler {
    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * ThreadPoolTaskScheduler
     */
    private ThreadPoolTaskScheduler scheduler;

    /**
     * scheduled job
     */
    protected IScheduledJob job = null;

    /**
     * ScheduledFuture
     */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * data map
     */
    Map data = null;

    /**
     * start scheduler
     */
    @Override
    public void begin() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduledFuture = scheduler.schedule(getRunnable(), getTrigger());
    }

    /**
     * stop scheduler
     */
    @Override
    public void stop() {
        if (scheduler != null)
            scheduler.shutdown();
    }

    /**
     * pause scheduler
     */
    @Override
    public void pause() {
        log.warn("This scheduler does NOT support \"pause\" function.");
    }

    /**
     * resume scheduler
     */
    @Override
    public void resume() {
        log.warn("This scheduler does NOT support \"resume\" function.");
    }

    @Override
    public void setData(Map data) {
        this.data = data;
    }

    private Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    processJob(data);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        };
    }

    public void reset(boolean mayInterruptIfRunning) {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(mayInterruptIfRunning);
            this.scheduledFuture = null;
        }
        this.begin();
    }

    /**
     * process scheduled job
     *
     * @param data for scheduled job
     * @throws Exception
     */
    public abstract void processJob(Map data) throws Exception;

    /**
     * get trigger for every time job
     *
     * @return Trigger
     */
    public abstract Trigger getTrigger();
}
