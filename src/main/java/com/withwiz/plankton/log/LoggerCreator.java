package com.withwiz.plankton.log;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.*;

/**
 * Logback Logger creator.<BR>
 * Created by uni4love on 2013. 3. 12..
 */
public class LoggerCreator
{
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
	private static final String LOG_PATTERN_DEFAULT = "%date %level [%thread] %logger{10} [%file:%line] %msg%n";

	/**
	 * filename default pattern
	 */
	private static final String FILENAME_PATTERN_DEFAULT = "%d{yyyy-MM-dd_HH}";

	/**
	 * create Logger and return.<BR>
	 *
	 * @param clz
	 *            Class
	 * @param pattern
	 *            log file path
	 * @param level
	 *            log level
	 * @return Logger
	 */
	public static Logger getConsoleLogger(Class clz, String pattern, int level)
	{
		// context
		LoggerContext lc = getLoggerContext();
		// pattern
		PatternLayoutEncoder ple = getPatternLayoutEncoder(lc, pattern);
		// appender
		ConsoleAppender consoleAppender = getConsoleAppender(lc, ple);
		return getLogger(clz, consoleAppender, level);
	}

	/**
	 * create and return file Logger.<BR>
	 *
	 * @param clz
	 *            Class
	 * @param pattern
	 *            log pattern
	 * @param level
	 *            log level
	 * @param filepath
	 *            log file path
	 * @return Logger
	 */
	public static Logger getFileLogger(Class clz, String pattern, int level,
			String filepath)
	{
		// context
		LoggerContext lc = getLoggerContext();
		// pattern
		PatternLayoutEncoder ple = getPatternLayoutEncoder(lc, pattern);
		// file appender
		FileAppender<ILoggingEvent> fileAppender = getFileAppender(lc, ple,
				filepath);
		return getLogger(clz, fileAppender, level);
	}

	/**
	 * create and return Rolling file logger.<BR>
	 *
	 * @param clz
	 *            Class
	 * @param pattern
	 *            log pattern
	 * @param level
	 *            log level
	 * @param filepath
	 *            log file path
	 * @return Logger
	 */
	public static Logger getRollingFileLogger(Class clz, String pattern,
			int level, String filepath, String filenamePattern)
	{
		// context
		LoggerContext lc = getLoggerContext();
		// pattern
		PatternLayoutEncoder ple = getPatternLayoutEncoder(lc, pattern);
		// rolling file appender
		RollingFileAppender<ILoggingEvent> rollingFileAppender = getRollingFileAppender(
				lc, ple, filepath, filenamePattern);
		return getLogger(clz, rollingFileAppender, level);
	}

	/**
	 * create Logger and return.<BR>
	 * 
	 * @param clz
	 *            Class
	 * @param appender
	 *            appender
	 * @param level
	 *            log level
	 * @return Logger
	 */
	public static Logger getLogger(Class clz, Appender<ILoggingEvent> appender,
			int level)
	{
		// logger
		Logger logger = getLoggerWithoutAppender(clz, level);
		logger.addAppender(appender);
		return logger;
	}

	/**
	 * create logger without appender.<BR>
	 * 
	 * @param clz
	 *            Class
	 * @param level
	 *            log level
	 * @return Logger
	 */
	protected static Logger getLoggerWithoutAppender(Class clz, int level)
	{
		// logger
		Logger logger = (Logger) LoggerFactory.getLogger(clz);
		logger.setLevel(Level.toLevel(level));
		return logger;
	}

	/**
	 * return LoggerContext.<BR>
	 *
	 * @return LoggerContext
	 */
	protected static LoggerContext getLoggerContext()
	{
		return (LoggerContext) LoggerFactory.getILoggerFactory();
	}

	/**
	 * return PatternLayoutEncoder.<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param pattern
	 *            pattern
	 * @return PatternLayoutEncoder
	 */
	protected static PatternLayoutEncoder getPatternLayoutEncoder(
			LoggerContext context, String pattern)
	{
		PatternLayoutEncoder encoder = getPatternLayoutEncoder(pattern);
		encoder.setContext(context);
		encoder.start();
		return encoder;
	}

	/**
	 * return PatternLayoutEncoder.<BR>
	 *
	 * @param pattern
	 *            pattern(if pattern is null, uses default pattern.
	 * @return PatternLayoutEncoder
	 */
	protected static PatternLayoutEncoder getPatternLayoutEncoder(
			String pattern)
	{
		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		if (pattern == null)
		{
			ple.setPattern(LOG_PATTERN_DEFAULT);
		}
		else
		{
			ple.setPattern(pattern);
		}
		return ple;
	}

	/**
	 * return ConsoleAppender.<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param logPatternLayoutEncoder
	 *            PatternLayoutEncoder
	 * @return ConsoleAppender
	 */
	protected static ConsoleAppender getConsoleAppender(LoggerContext context,
			PatternLayoutEncoder logPatternLayoutEncoder)
	{
		ConsoleAppender consoleAppender = new ConsoleAppender();
		consoleAppender.setContext(context);
		consoleAppender.setEncoder(logPatternLayoutEncoder);
		return consoleAppender;
	}

	/**
	 * return FileAppender.<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param logPatternLayoutEncoder
	 *            PatternLayoutEncoder
	 * @param filepath
	 *            file path
	 * @return FileAppender
	 */
	protected static FileAppender getFileAppender(LoggerContext context,
			PatternLayoutEncoder logPatternLayoutEncoder, String filepath)
	{
		FileAppender fileAppender = new FileAppender();
		fileAppender.setContext(context);
		fileAppender.setEncoder(logPatternLayoutEncoder);
		fileAppender.setFile(filepath);
		fileAppender.start();
		return fileAppender;
	}

	/**
	 * return RollingFileAppender.<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param logPatternLayoutEncoder
	 *            log pattern layout encoder
	 * @param filepath
	 *            file path
	 * @return RollingFileAppender
	 */
	protected static RollingFileAppender getRollingFileAppender(
			LoggerContext context, PatternLayoutEncoder logPatternLayoutEncoder,
			String filepath, String filenamePattern)
	{
		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
		rollingFileAppender.setContext(context);
		rollingFileAppender.setEncoder(logPatternLayoutEncoder);
		rollingFileAppender.setFile(filepath);
		rollingFileAppender.setAppend(true);

		if (filenamePattern == null)
		{
			filenamePattern = getDefaultFilenamePattern(filepath);
		}
		RollingPolicy rollingPolicy = getTimeBasedRollingPolicy(context,
				filenamePattern);
		rollingPolicy.setParent(rollingFileAppender);

		rollingFileAppender.setRollingPolicy(rollingPolicy);
		// rollingFileAppender.setTriggeringPolicy();
		rollingFileAppender.start();
		return rollingFileAppender;
	}

	/**
	 * return default filename pattern<BR>
	 *
	 * @param filepath
	 *            file path
	 * @return filename pattern
	 */
	protected static String getDefaultFilenamePattern(String filepath)
	{
		String filepathPattern = filepath + FILENAME_PATTERN_DEFAULT;
		return filepathPattern;
	}

	/**
	 * return FixedWindowRollingPolicy.<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param fileAppender
	 *            FileAppender
	 * @param filenamePattern
	 *            filename pattern
	 * @return FixedRollingPolicy
	 */
	protected static RollingPolicy getFixedWindowRollingPolicy(
			LoggerContext context, FileAppender fileAppender,
			String filenamePattern)
	{
		FixedWindowRollingPolicy fwRollingPolicy = new FixedWindowRollingPolicy();
		fwRollingPolicy.setContext(context);
		fwRollingPolicy.setFileNamePattern(filenamePattern);
		fwRollingPolicy.setParent(fileAppender);
		fwRollingPolicy.start();
		return fwRollingPolicy;
	}

	/**
	 * return TimeBasedRollingPolicy<BR>
	 *
	 * @param context
	 *            LoggerContext
	 * @param filenamePattern
	 *            file name pattern
	 * @return TimeBasedRollingPolicy
	 */
	protected static RollingPolicy getTimeBasedRollingPolicy(
			LoggerContext context, String filenamePattern)
	{
		TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
		timeBasedRollingPolicy.setContext(context);
		timeBasedRollingPolicy.setFileNamePattern(filenamePattern);
		return timeBasedRollingPolicy;
	}

	/**
	 * return size based triggering policy<BR>
	 *
	 * @param size
	 *            file size string(ex: "2MB", "1024KB")
	 * @return SizeBasedTriggeringPolicy
	 */
	protected static TriggeringPolicy getSizeBasedTriggeringPolicy(long size)
	{
		SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
		triggeringPolicy.setMaxFileSize(new FileSize(size));
		triggeringPolicy.start();
		return triggeringPolicy;
	}

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		String filepath = System.getProperty("user.home") + "/test.log";
		String filenamePattern = System.getProperty("user.home")
				+ "/out-%d{yyyy-MM-dd_HH}.log";
		Logger log1 = LoggerCreator.getConsoleLogger(LoggerCreator.class, null,
				LoggerCreator.LEVEL_DEBUG);
		log1.debug("This is debug.");
		log1.info("This is info.");
		log1.error("This is error.");

		Logger log2 = LoggerCreator.getFileLogger(LoggerCreator.class, null,
				LoggerCreator.LEVEL_DEBUG, filepath);
		log2.debug("This is debug: log2");
		log2.info("This is info: log2");
		log2.error("This is error: log2");

		Logger log3 = LoggerCreator.getRollingFileLogger(LoggerCreator.class,
				null, LoggerCreator.LEVEL_ALL, filepath, filenamePattern);
		log3.debug("This is debug: log3");
		log3.info("This is info: log3");
		log3.error("This is error: log3");
	}
}
