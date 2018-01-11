package ru.fts.metrics.mqmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fts.metrics.components.RemoteProperties;
import ru.fts.metrics.mqmetrics.*;
import ru.fts.metrics.mqmetrics.utils.MqUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class MQMetricSet implements MetricSet {

    @Autowired
    private RemoteProperties remoteProperties;

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();

        for (String qManagerName : remoteProperties.getQueueManagers()) {
            String[] names = MqUtils.getQueueNames(qManagerName);

            List<QueueMonitorConfig> queueMonitorConfs = new ArrayList<QueueMonitorConfig>();
            for (String name : names) {
                if (!name.contains("SYSTEM") && !name.contains("AMQ")) {
                    QueueMonitorConfig queueMonitorConfig = new QueueMonitorConfig();
                    queueMonitorConfig.setQueueName(name);
                    queueMonitorConfig.setMaxDepth(5);
                    queueMonitorConfig.setMaxInterval(5);
                    queueMonitorConfs.add(queueMonitorConfig);
                }
            }

            QManagerMonitorConfig config = new QManagerMonitorConfig();
            config.setQManagerName(qManagerName);
            config.setQueueMonitorConfigs(queueMonitorConfs);

            QueueManagerMonitor queueManagerMonitor = new QueueManagerMonitor(config);
            List<QueueMonitorStatus> qstatuses = queueManagerMonitor.monitoring().getQueueStatuses();

            for (QueueMonitorStatus status : qstatuses) {
                gauges.put(qManagerName + "." + status.getQueueName().trim(), new CustomMQGauge(qManagerName, status
                        .getQueueName().trim()));
            }
        }
            /*List<String> wmiClassesList = WMI4Java.get().listClasses();
            List<String> wmiClassesList1 = WMI4Java.get().namespace("root/WMI").listClasses();
            Map<String, String> wmiObjectProperties = WMI4Java.get().getWMIObject("Win32_BIOS");*/


        return gauges;
    }

}
