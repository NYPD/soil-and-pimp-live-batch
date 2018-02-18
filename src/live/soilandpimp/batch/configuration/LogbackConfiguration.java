package live.soilandpimp.batch.configuration;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.net.SMTPAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.net.SMTPAppenderBase;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
public class LogbackConfiguration {

    private final String encoderPattern = "%d{[yyyy-MM-dd HH:mm:ss.SSS]} [%-5level]\\(%F{0}:%M\\(\\):%L\\) - %msg%n";
    private final String filePattern = "/logs/" + AppConstants.PROJECT_NAME + "/soilandpimpbatch.%d{yyyy-MM-dd}.log";
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Autowired
    private Environment springEnviroment;
    @Autowired(required = false)
    private List<Appender<ILoggingEvent>> appenders;

    @Bean
    @DevelopmentProfile
    public ConsoleAppender<ILoggingEvent> consoleAppender() {

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setName("console");
        consoleAppender.setEncoder(getDefaultPattern());
        consoleAppender.setContext(loggerContext);
        consoleAppender.start();

        return consoleAppender;

    }

    @Bean
    @DevelopmentProfile
    @ProductionProfile
    public RollingFileAppender<ILoggingEvent> rollingFileAppender() {

        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
        rollingFileAppender.setName("rolling");
        rollingFileAppender.setContext(loggerContext);

        TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<>();
        timeBasedRollingPolicy.setFileNamePattern(filePattern);
        timeBasedRollingPolicy.setMaxHistory(60);
        timeBasedRollingPolicy.setParent(rollingFileAppender);
        timeBasedRollingPolicy.setContext(loggerContext);
        timeBasedRollingPolicy.start();

        rollingFileAppender.setEncoder(getDefaultPattern());
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.start();

        return rollingFileAppender;

    }

    @Bean
    @ProductionProfile
    public SMTPAppenderBase<ILoggingEvent> smptAppender() {

        SMTPAppender smtpAppender = new SMTPAppender();
        smtpAppender.setContext(loggerContext);
        smtpAppender.setName("e-mail");
        smtpAppender.setFrom("exceptions@soilandpimp.live");
        smtpAppender.addTo("devs@soilandpimp.live");
        smtpAppender.setSubject(AppConstants.APPLICATION_NAME + " Exception");
        smtpAppender.setSMTPHost("localhost");
        smtpAppender.setSMTPPort(25);
        smtpAppender.setSSL(false);
        smtpAppender.setSTARTTLS(false);

        smtpAppender.setAsynchronousSending(false);
        smtpAppender.setLayout(getDefaultPattern().getLayout());

        smtpAppender.start();

        return smtpAppender;

    }

    private PatternLayoutEncoder getDefaultPattern() {

        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern(encoderPattern);
        patternLayoutEncoder.start();

        return patternLayoutEncoder;

    }

    @PostConstruct
    public void initLogbackAppenders() {

        if (appenders == null) return;

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.detachAndStopAllAppenders();

        for (Appender<ILoggingEvent> appender : appenders)
            root.addAppender(appender);

        String[] activeProfiles = springEnviroment.getActiveProfiles();

        boolean isDevelopment = Arrays.stream(activeProfiles)
                                      .filter(x -> AppConstants.DEV_PROFILE.equals(x))
                                      .findAny()
                                      .orElse(null) != null;

        Level loggingLevel = isDevelopment? Level.DEBUG : Level.INFO;

        root.setLevel(loggingLevel);

    }

}
