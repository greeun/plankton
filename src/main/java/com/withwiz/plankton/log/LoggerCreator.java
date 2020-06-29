package com.withwiz.plankton.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.*;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.*;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Logback Logger creator.<BR>
 * Created by uni4love on 2013. 3. 12..
 */
public class LoggerCreator {
    /**
     * log level: error
     */
    public static final int LEVEL_ERROR = Level.ERROR_INT;

    /**
     * log level: warn
     */
    public static final int LEVEL_WARN = Level.WARN_INT;

    /**
     * log level: info
     */
    public static final int LEVEL_INFO = Level.INFO_INT;

    /**
     * log level: debug
     */
    public static final int LEVEL_DEBUG = Level.DEBUG_INT;

    /**
     * log level: trace
     */
    public static final int LEVEL_TRACE = Level.TRACE_INT;

    /**
     * log level: all
     */
    public static final int LEVEL_ALL = Level.ALL_INT;

    /**
     * log layout default pattern
     */
    private static final String LOG_PATTERN_DEFAULT = "%-5level %date %thread{20} %logger{20} [%C{20}.%M:%L] %msg%n";

    /**
     * filename default pattern
     */
    private static final String FILENAME_PATTERN_DEFAULT = "-%d{yyyy-MM-dd-HH-mm}-%i.log.gz";

    /**
     * return LoggerContext.<BR>
     *
     * @return LoggerContext
     */
    public static LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    /**
     * create Logger and return.<BR>
     *
     * @param loggerName logger name
     * @param pattern    log file path
     * @param level      log level
     * @return Logger
     */
    public static Logger createConsoleLogger(String loggerName, String pattern, int level) {
        // context
        LoggerContext lc = getLoggerContext();
        // pattern
        PatternLayoutEncoder ple = createPatternLayoutEncoder(lc, pattern, false);
        ple.start();
        // appender
        ConsoleAppender consoleAppender
                = createConsoleAppender(loggerName + "-console", lc, ple, false);
        consoleAppender.start();
        return createLogger(loggerName, consoleAppender, level);
    }

    /**
     * create Logger and return.<BR>
     *
     * @param clz     Class
     * @param pattern log file path
     * @param level   log level
     * @return Logger
     */
    public static Logger createConsoleLogger(Class clz, String pattern, int level) {
        return createConsoleLogger(clz.getName(), pattern, level);
    }

    /**
     * create and return file Logger.<BR>
     *
     * @param loggerName logger name
     * @param pattern    log pattern
     * @param level      log level
     * @param filepath   log file path
     * @return Logger
     */
    public static Logger createFileLogger(String loggerName, String pattern, int level, String filepath) {
        // context
        LoggerContext loggerContext = getLoggerContext();
        // pattern
        PatternLayoutEncoder encoder = createPatternLayoutEncoder(loggerContext, pattern, false);
        encoder.start();
        // file appender
        FileAppender<ILoggingEvent> fileAppender
                = createFileAppender(loggerName + "-file", loggerContext, encoder, filepath, false);
        fileAppender.start();
        return createLogger(loggerName, fileAppender, level);
    }

    /**
     * create and return file Logger.<BR>
     *
     * @param clz      Class
     * @param pattern  log pattern
     * @param level    log level
     * @param filepath log file path
     * @return Logger
     */
    public static Logger createFileLogger(Class clz, String pattern, int level, String filepath) {
        return createFileLogger(clz.getName(), pattern, level, filepath);
    }

    /**
     * create and return Rolling file logger.<BR>
     *
     * @param loggerName      logger name
     * @param pattern         log pattern
     * @param level           log level
     * @param filepath        log file path
     * @param filenamePattern filename pattern
     * @return Logger
     */
    public static Logger createRollingFileLogger(String loggerName, String pattern, int level, String filepath,
                                                 String filenamePattern, String maxFileSize, int maxHistory,
                                                 String totalSizeCap) {
        // context
        LoggerContext loggerContext = getLoggerContext();
        // pattern
        PatternLayoutEncoder encoder = createPatternLayoutEncoder(loggerContext, pattern, false);
        encoder.start();
        // rolling file appender
        RollingFileAppender<ILoggingEvent> rollingFileAppender
                = createRollingFileAppender(loggerName + "-rollingfile", loggerContext, encoder, filepath, filenamePattern,
                maxFileSize, maxHistory, totalSizeCap, false);
        rollingFileAppender.start();
        return createLogger(loggerName, rollingFileAppender, level);
    }

    /**
     * create and return Rolling file logger.<BR>
     *
     * @param clz      Class
     * @param pattern  log pattern
     * @param level    log level
     * @param filepath log file path
     * @return Logger
     */
    public static Logger createRollingFileLogger(Class clz, String pattern,
                                                 int level, String filepath, String filenamePattern,
                                                 String maxFileSize, int maxHistory, String totalSizeCap) {
        return createRollingFileLogger(clz.getName(), pattern, level, filepath, filenamePattern, maxFileSize,
                maxHistory, totalSizeCap);
    }

    /**
     * create Logger and return.<BR>
     *
     * @param loggerName logger name
     * @param appender   appender
     * @param level      log level
     * @return Logger
     */
    public static Logger createLogger(String loggerName, Appender<ILoggingEvent> appender, int level) {
        Logger logger = createLoggerWithoutAppender(loggerName, level);
        logger.addAppender(appender);
        return logger;
    }

    /**
     * create Logger and return.<BR>
     *
     * @param clz      Class
     * @param appender appender
     * @param level    log level
     * @return Logger
     */
    public static Logger createLogger(Class clz, Appender<ILoggingEvent> appender, int level) {
        Logger logger = createLogger(clz.getName(), appender, level);
        logger.addAppender(appender);
        return logger;
    }

    /**
     * create logger without appender.<BR>
     *
     * @param loggerName logger name
     * @param level      log level
     * @return Logger
     */
    public static Logger createLoggerWithoutAppender(String loggerName, int level) {
        Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
        logger.setLevel(Level.toLevel(level));
        return logger;
    }

    /**
     * create logger without appender.<BR>
     *
     * @param clz   Class
     * @param level log level
     * @return Logger
     */
    public static Logger createLoggerWithoutAppender(Class clz, int level) {
        return createLoggerWithoutAppender(clz.getName(), level);
    }

    /**
     * return PatternLayoutEncoder.
     *
     * @param context LoggerContext
     * @param pattern pattern
     * @return PatternLayoutEncoder
     */
    public static PatternLayoutEncoder createPatternLayoutEncoder(LoggerContext context,
                                                                  String pattern, boolean isAutoStart) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(pattern);
        encoder.setCharset(Charset.forName("UTF-8"));
        if (isAutoStart)
            encoder.start();
        return encoder;
    }

    /**
     * return PatternLayout
     *
     * @param context     LoggerContext
     * @param pattern     pattern string
     * @param isAutoStart is auto start
     * @return PatternLayout
     */
    public static PatternLayout createPatternLayout(LoggerContext context, String pattern, boolean isAutoStart) {
        PatternLayout layout = new PatternLayout();
        layout.setContext(context);
        if (pattern == null) {
            layout.setPattern(LOG_PATTERN_DEFAULT);
        } else {
            layout.setPattern(pattern);
        }
        if (isAutoStart)
            layout.start();
        return layout;
    }

    /**
     * return ConsoleAppender.<BR>
     *
     * @param context                 LoggerContext
     * @param logPatternLayoutEncoder PatternLayoutEncoder
     * @return ConsoleAppender
     */
    public static ConsoleAppender createConsoleAppender(String name, LoggerContext context,
                                                        PatternLayoutEncoder logPatternLayoutEncoder,
                                                        boolean isAutoStart) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setName(name);
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(logPatternLayoutEncoder);
        if (isAutoStart)
            consoleAppender.start();
        return consoleAppender;
    }

    /**
     * return FileAppender.<BR>
     *
     * @param context                 LoggerContext
     * @param logPatternLayoutEncoder PatternLayoutEncoder
     * @param filepath                file path
     * @return FileAppender
     */
    public static FileAppender createFileAppender(String name, LoggerContext context,
                                                  PatternLayoutEncoder logPatternLayoutEncoder, String filepath,
                                                  boolean isAutoStart) {
        FileAppender fileAppender = new FileAppender();
        fileAppender.setName(name);
        fileAppender.setContext(context);
        fileAppender.setEncoder(logPatternLayoutEncoder);
        fileAppender.setFile(filepath);
        if (isAutoStart)
            fileAppender.start();
        return fileAppender;
    }

    /**
     * return RollingFileAppender.<BR>
     *
     * @param context  LoggerContext
     * @param encoder  log pattern layout encoder
     * @param filepath file path
     * @return RollingFileAppender
     */
    public static RollingFileAppender createRollingFileAppender(
            String name, LoggerContext context, Encoder<ILoggingEvent> encoder, String filepath,
            String filenamePattern, String maxFileSize, int maxHistory, String totalSizeCap, boolean isAutoStart) {
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setName(name);
        rollingFileAppender.setContext(context);
        rollingFileAppender.setAppend(true);
        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.setFile(filepath);

        //rolling policy
        if (filenamePattern == null) {
            filenamePattern = getDefaultFilenamePattern(filepath);
        }
        TimeBasedRollingPolicy rollingPolicy
                = createTimeBasedRollingPolicy(context, filenamePattern, maxHistory, totalSizeCap, false);
        rollingPolicy.setParent(rollingFileAppender);

        //trigger policy
        SizeAndTimeBasedFNATP<ILoggingEvent> triggerPolicy
                = createSizeAndTimeBasedFnatpPolicy(context, maxFileSize, false);
        triggerPolicy.setTimeBasedRollingPolicy(rollingPolicy);
        rollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(triggerPolicy);

        rollingPolicy.start();
        triggerPolicy.start();
        rollingFileAppender.setRollingPolicy(rollingPolicy);

        if (isAutoStart)
            rollingFileAppender.start();
        return rollingFileAppender;
    }

    /**
     * return default filename pattern<BR>
     *
     * @param filepath file path
     * @return filename pattern
     */
    public static String getDefaultFilenamePattern(String filepath) {
        return filepath + FILENAME_PATTERN_DEFAULT;
    }

    /**
     * return FixedWindowRollingPolicy.<BR>
     *
     * @param context         LoggerContext
     * @param fileAppender    FileAppender
     * @param filenamePattern filename pattern
     * @return FixedRollingPolicy
     */
    public static RollingPolicy createFixedWindowRollingPolicy(LoggerContext context, FileAppender fileAppender,
                                                               String filenamePattern, boolean isAutoStart) {
        FixedWindowRollingPolicy policy = new FixedWindowRollingPolicy();
        policy.setContext(context);
        policy.setFileNamePattern(filenamePattern);
        policy.setParent(fileAppender);
        if (isAutoStart)
            policy.start();
        return policy;
    }

    /**
     * return TimeBasedRollingPolicy<BR>
     *
     * @param context         LoggerContext
     * @param filenamePattern file name pattern
     * @return TimeBasedRollingPolicy
     */
    public static TimeBasedRollingPolicy createTimeBasedRollingPolicy(
            LoggerContext context, String filenamePattern, int maxHistory, String totalSizeCap,
            boolean isAutoStart) {
        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<ILoggingEvent>();
        policy.setContext(context);
        policy.setFileNamePattern(filenamePattern);
        policy.setMaxHistory(maxHistory);
        policy.setTotalSizeCap(FileSize.valueOf(totalSizeCap));
        if (isAutoStart)
            policy.start();
        return policy;
    }

    /**
     * return size based triggering policy<BR>
     *
     * @param context LoggerContext
     * @param maxSize file max size string(ex: "2MB", "1024KB")
     * @return SizeBasedTriggeringPolicy
     */
    public static TriggeringPolicy createSizeBasedTriggeringPolicy(
            LoggerContext context, long maxSize, boolean isAutoStart) {
        SizeBasedTriggeringPolicy<ILoggingEvent> policy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
        policy.setContext(context);
        policy.setMaxFileSize(new FileSize(maxSize));
        if (isAutoStart)
            policy.start();
        return policy;
    }

    /**
     * return size based triggering policy<BR>
     *
     * @param context     LoggerContext
     * @param size        file size string(ex: "2MB", "1024KB")
     * @param isAutoStart policy auto start
     * @return SizeAndTimeBasedFNATP
     */
    public static SizeAndTimeBasedFNATP createSizeAndTimeBasedFnatpPolicy(
            LoggerContext context, String size, boolean isAutoStart) {
        SizeAndTimeBasedFNATP<ILoggingEvent> policy = new SizeAndTimeBasedFNATP<>();
        policy.setContext(context);
        policy.setMaxFileSize(FileSize.valueOf(size));
        if (isAutoStart)
            policy.start();
        return policy;
    }

    /**
     * get ROOT logger
     *
     * @return Logger
     */
    public static Logger getRootLogger() {
        return (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    /**
     * get ROOT logger
     *
     * @param pattern format
     * @param level   level
     */
    public static void getRootLogger(String pattern, String level) {
        Logger logger = getRootLogger();
        setLoggerFormat(logger, pattern);
        setLoggerLevel(logger, level);
    }

    /**
     * get ROOT logger
     *
     * @param pattern format
     * @param level   level
     */
    public static void getRootLogger(String pattern, int level) {
        Logger logger = getRootLogger();
        setLoggerFormat(logger, pattern);
        setLoggerLevel(logger, level);
    }

    /**
     * set ROOT logger level
     *
     * @param level level
     */
    public static void setRootLoggerLevel(String level) {
        setRootLoggerLevel(Level.toLevel(level));
    }

    /**
     * set ROOT logger level
     *
     * @param level log level
     */
    public static void setRootLoggerLevel(Level level) {
        getRootLogger().setLevel(level);
    }


    /**
     * set logger format
     *
     * @param logger Logger
     * @param layout log format pattern layout
     */
    public static void setLoggerFormat(Logger logger, Layout layout) {
        OutputStreamAppender<ILoggingEvent> appender = null;
        LayoutWrappingEncoder ple = null;
        // pattern
        for (Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders(); it.hasNext(); ) {
            appender = (OutputStreamAppender<ILoggingEvent>) it.next();
            ple = (LayoutWrappingEncoder) appender.getEncoder();
            ple.setLayout(layout);
        }
    }

    /**
     * set logger format
     *
     * @param logger  Logger
     * @param pattern log format pattern
     */
    public static void setLoggerFormat(Logger logger, String pattern) {
        PatternLayout layout = new PatternLayout();
        setLoggerFormat(logger, layout);
    }

    /**
     * set logger level
     *
     * @param logger Logger
     * @param level  level
     */
    public static void setLoggerLevel(Logger logger, String level) {
        setLoggerLevel(logger, Level.toLevel(level));
    }

    /**
     * set logger level
     *
     * @param logger Logger
     * @param level  level
     */
    public static void setLoggerLevel(Logger logger, int level) {
        setLoggerLevel(logger, Level.toLevel(level));
    }

    /**
     * set logger level
     *
     * @param logger Logger
     * @param level  level
     */
    public static void setLoggerLevel(Logger logger, Level level) {
        logger.setLevel(level);
    }

    /**
     * set ROOT logger layout
     *
     * @param layout layout
     */
    public static void setRootLoggerLayout(Layout layout) {
        ((LayoutWrappingEncoder) (((ConsoleAppender) (getRootLogger().getAppender("console"))).getEncoder())).setLayout(layout);
    }

    /**
     * set ROOT logger layout
     *
     * @param pattern layout pattern
     */
    public static void setRootLoggerLayout(String pattern) {
        PatternLayout layout = createPatternLayout(getRootLogger().getLoggerContext(), pattern, true);
        ((LayoutWrappingEncoder) (((ConsoleAppender) (getRootLogger().getAppender("console"))).getEncoder())).setLayout(layout);
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        String filepath2 = System.getProperty("user.home") + "/test2.log";
        String filepath3 = System.getProperty("user.home") + "/test3.log";
        String logFormat = LOG_PATTERN_DEFAULT;
        String filenamePattern = FILENAME_PATTERN_DEFAULT;

        //root logger
        Logger rootLogger = LoggerCreator.getRootLogger();
        rootLogger.setLevel(Level.INFO);//
        LoggerCreator.setLoggerFormat(rootLogger, LOG_PATTERN_DEFAULT);
        LoggerCreator.getRootLogger(LOG_PATTERN_DEFAULT, LoggerCreator.LEVEL_DEBUG);

        Logger log1 = LoggerCreator.createConsoleLogger(LoggerCreator.class, logFormat, LoggerCreator.LEVEL_DEBUG);
        log1.debug("This is debug.");
        log1.info("This is info.");
        log1.error("This is error.");

        Logger log2 = LoggerCreator.createFileLogger("TEST-file", logFormat, LoggerCreator.LEVEL_INFO, filepath2);
        log2.debug("This is debug: log2");
        log2.info("This is info: log2");
        log2.error("This is error: log2");

        Logger log3 = LoggerCreator.createRollingFileLogger("TEST-rollingfile",
                logFormat, LoggerCreator.LEVEL_WARN, filepath3, filepath3 + filenamePattern, "10KB", 3,
                "4MB");
        LoggerCreator.setRootLoggerLayout(logFormat);
        log3.debug("This is debug: log3");
        log3.info("This is info: log3");
        log3.error("This is error: log3");

        for (long i = 0; i < 10000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                log3.error("interrupt error", ex);
            }
//            MDC.put("vhost", vhosts[(int)i%3]);
            log3.warn("wow logger~ " + i);
        }
    }
}
