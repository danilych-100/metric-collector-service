package ru.fts.metrics.systemmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;

import java.util.HashMap;
import java.util.Map;

/**
 * Метрики процессора.
 */
public class CpuMetricSet extends AbstractMetricSet {

    public static final int CPU_IN_PROCENTIL = 100;

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();

        gauges.put("available_processors", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return os.getAvailableProcessors();
            }
        });

        gauges.put("system_load_average", new Gauge<Double>() {
            @Override
            public Double getValue() {
                return os.getSystemLoadAverage();
            }
        });

        gauges.put("system_cpu_load", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getSystemCpuLoad").doubleValue() * CPU_IN_PROCENTIL;
            }
        });

        gauges.put("processor_cpu_load", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getProcessCpuLoad");
            }
        });

        gauges.put("process_cpu_time", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return invoke("getProcessCpuTime");
            }
        });

        return gauges;
    }
}
