package com.withwiz.plankton.scheduler;

import ch.qos.logback.classic.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
 * scheduler with quartz
 */
public class QuartzScheduler implements IScheduler {
    /**
     * logger
     */
    Logger log = (Logger)LoggerFactory.getLogger(QuartzScheduler.class.getName());

    /**
     * scheduled job
     */
    Class<Job> scheduledJob = null;

    /**
     * interval: 1 seconds
     */
    int interval = 1000;

    /**
     * running status
     */
    boolean isRunning = false;

    /**
     * quartz scheduler
     */
    Scheduler scheduler = null;

    /**
     * data map
     */
    Map data = null;

    /**
     * constructor
     */
    public QuartzScheduler(Class<Job> jobClass) {
        this.scheduledJob = jobClass;

    }

    /**
     * constructor
     */
    public QuartzScheduler(Class<Job> jobClass, int interval) {
        this(jobClass);
        this.interval = interval;

    }

    /**
     * start scheduler.
     */
    public void begin() {
        JobDetail job = JobBuilder.newJob(scheduledJob)
                .withIdentity("scheduledJob").build();
//                .usingJobData("jobSays", "Hello World!")
//                .usingJobData("myFloatValue", 3.141f).build();
        job.getJobDataMap().putAll(data);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("default-trigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(interval).repeatForever()).build();

        try {
            Properties prop = new Properties();
            prop.setProperty("org.quartz.threadPool.threadCount", "1");
            SchedulerFactory schFactory = new StdSchedulerFactory(prop);
            scheduler = schFactory.getScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("{}", e);
        }
    }

    @Override
    public void pause() {
        try {
            scheduler.pauseAll();
            log.warn("scheduler paused.");
        } catch (SchedulerException e) {
            log.error("{}", e);
        }
    }

    @Override
    public void resume() {
        try {
            scheduler.resumeAll();
            log.warn("scheduler resumed.");
        } catch (SchedulerException e) {
            log.error("{}", e);
        }
    }

    @Override
    public void stop() {
        try {
            scheduler.shutdown();
            log.warn("scheduler stopped.");
        } catch (SchedulerException e) {
            log.error("{}", e);
        }
    }

    @Override
    public void setData(Map data) {
        this.data = data;
    }
}
