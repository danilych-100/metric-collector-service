package ru.fts.metrics.configuration;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashSocketAppender;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LoggerConfiguration {

    public static final int MAX_LENGTH = 7500;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${logging.logstash.port:#{null}}")
    private Integer logstashPort;

    @Value("${logging.logstash.host:#{null}}")
    private String logstashHost;

    @Value("${logging.logstash.queueSize:#{null}}")
    private Integer queueSize;


    /**
     * Инициализация аппендера для записи в logstash.
     */
    @PostConstruct
    private void init() {
        addLogstashAppender();
    }

    /**
     * Добавляет асинхронный апендер, который записывает логи в logstash.
     */
    private void addLogstashAppender() {
        LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
        logstashAppender.setName("LOGSTASH");
        logstashAppender.setContext(context);

        logstashAppender.setSyslogHost(logstashHost);
        logstashAppender.setPort(logstashPort);

        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setMaxLength(MAX_LENGTH);
        throwableConverter.setRootCauseFirst(true);
        logstashAppender.setThrowableConverter(throwableConverter);

        logstashAppender.start();

        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(context);
        asyncAppender.setName("ASYNC_LOGSTASH");
        asyncAppender.setQueueSize(queueSize);
        asyncAppender.addAppender(logstashAppender);
        asyncAppender.start();

        context.getLogger("ROOT").addAppender(asyncAppender);
    }
}
