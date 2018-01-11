package ru.fts.metrics.systemmetrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * Базовый класс для кастомных метрик.
 */
public abstract class AbstractMetricSet implements MetricSet {


    protected final OperatingSystemMXBean os;

    public AbstractMetricSet() {
        this(ManagementFactory.getOperatingSystemMXBean());
    }

    private AbstractMetricSet(final OperatingSystemMXBean os) {
        this.os = os;
    }

    @Override
    public abstract Map<String, Metric> getMetrics();

    /**
     * Вызывает метод, по его имени.
     *
     * @param name
     * @return
     */
    protected Number invoke(final String name) {
        try {
            final Method method = os.getClass().getDeclaredMethod(name);
            method.setAccessible(true);
            return (Number) method.invoke(os);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        catch (IllegalAccessException e) {
            return null;
        }
        catch (InvocationTargetException e) {
            return null;
        }
    }
}
