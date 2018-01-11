package ru.fts.metrics.systemmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Метрики жесткого диска.
 */
public class HDDMetricSet extends AbstractMetricSet {
    private File root = FileSystemView.getFileSystemView().getHomeDirectory();

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();
        gauges.put("memory.free", new Gauge<Long>() {

            @Override
            public Long getValue() {
                return root.getFreeSpace();
            }
        });

        gauges.put("memory.total", new Gauge<Long>() {

            @Override
            public Long getValue() {
                return root.getTotalSpace();
            }
        });

        gauges.put("memory.usage", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return root.getUsableSpace();
            }
        });

        return gauges;
    }
}
