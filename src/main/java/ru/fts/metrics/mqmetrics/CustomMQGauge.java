package ru.fts.metrics.mqmetrics;

import com.codahale.metrics.Gauge;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import ru.fts.metrics.mqmetrics.utils.MqUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danil on 10.01.2018.
 */
public class CustomMQGauge implements Gauge<String> {

    private String qManagerName;

    private String qName;

    public CustomMQGauge(String qManagerName, String qName) {
        this.qManagerName = qManagerName;
        this.qName = qName;
    }

    @Override
    public String getValue() {
        String[] names = new String[0];

        names = MqUtils.getQueueNames(qManagerName);

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

        StringBuilder stringBuilder = new StringBuilder();
        for (QueueMonitorStatus status : qstatuses) {
            if (status.getQueueName().trim().equals(qName.trim())) {
                stringBuilder.append("qname=" + status.getQueueName().trim() + ";");
                stringBuilder.append("available=" + status.isAvailable() + ";");
                stringBuilder.append("depth=" + status.getDepth() + ";");
                stringBuilder.append("hasError=" + status.isHasErrors() + ";");
                break;
            }
        }

        return stringBuilder.toString();
    }
}
