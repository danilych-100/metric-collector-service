package ru.fts.metrics.systemmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;

import java.util.HashMap;
import java.util.Map;

/**
 * Метрики оперативной памяти.
 */
public class RAMMetricSet extends AbstractMetricSet {
    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();

        gauges.put("free_physical_memory_size", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getFreePhysicalMemorySize");
            }
        });

        gauges.put("total_physical_memory_size", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getTotalPhysicalMemorySize");
            }
        });

        return gauges;
    }
}
