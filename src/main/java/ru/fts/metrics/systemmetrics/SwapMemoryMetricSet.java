package ru.fts.metrics.systemmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;

import java.util.HashMap;
import java.util.Map;

/**
 * Метрики файла подкачки.
 */
public class SwapMemoryMetricSet extends AbstractMetricSet {
    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();

        gauges.put("total_swap_space_size", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getTotalSwapSpaceSize");
            }
        });

        gauges.put("free_swap_space_size", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getFreeSwapSpaceSize");
            }
        });

        return gauges;
    }
}
