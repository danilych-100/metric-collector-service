package ru.fts.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.fts.metrics.util.ExternalLibraryInitializer;

import java.io.IOException;


@SpringBootApplication
@ComponentScan(basePackages = {"ru.fts.metrics"})
@EnableAutoConfiguration
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
       /* ExternalLibraryInitializer hacker = new ExternalLibraryInitializer(System.getProperty("mq.libraries.path"));
        hacker.init();*/
        SpringApplication app = new SpringApplication(Application.class);
        new SimpleCommandLinePropertySource(args);
        app.run(args).getEnvironment();
    }

}
