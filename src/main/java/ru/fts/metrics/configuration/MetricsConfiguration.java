package ru.fts.metrics.configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fts.metrics.mqmetrics.MQMetricSet;
import ru.fts.metrics.servicesmetrics.ServicesMetricSet;
import ru.fts.metrics.systemmetrics.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration extends MetricsConfigurerAdapter {

    private static final String PROP_METRIC_REG_SYSTEM_CPU = "cpu";

    private static final String PROP_METRIC_REG_SYSTEM_SWAP = "swap";

    private static final String PROP_METRIC_REG_SYSTEM_RAM = "system.memory";

    private static final String PROP_METRIC_REG_SYSTEM_HDD = "hdd";

    public static final String METRICS_LOGGER_NAME = "metrics";

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MetricRegistry metricRegistry = new MetricRegistry();
    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Override
    @Bean("metricRegistry")
    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

    @Value("${logging.report-frequency:#{null}}")
    private Long reportFrequency;

    @Autowired
    private ServicesMetricSet servicesMetricSet;

    @Autowired
    private MQMetricSet mqMetricSet;


    @PostConstruct
    private void init() throws IOException {
        logger.debug("Registering JVM gauges");
        metricRegistry.register(PROP_METRIC_REG_SYSTEM_CPU, new CpuMetricSet());
        metricRegistry.register(PROP_METRIC_REG_SYSTEM_SWAP, new SwapMemoryMetricSet());
        metricRegistry.register(PROP_METRIC_REG_SYSTEM_RAM, new RAMMetricSet());
        metricRegistry.register(PROP_METRIC_REG_SYSTEM_HDD, new HDDMetricSet());
        metricRegistry.registerAll(servicesMetricSet);
        metricRegistry.registerAll(mqMetricSet);

        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger(METRICS_LOGGER_NAME))
                .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(reportFrequency, TimeUnit.SECONDS);
    }
}
