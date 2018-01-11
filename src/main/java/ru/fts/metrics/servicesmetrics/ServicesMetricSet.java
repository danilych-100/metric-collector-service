package ru.fts.metrics.servicesmetrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fts.metrics.components.RemoteProperties;
import ru.fts.metrics.servicesmetrics.utils.ServicesGaugeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class ServicesMetricSet implements MetricSet {

    @Autowired
    private RemoteProperties remoteProperties;

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauges = new HashMap<>();

        List<String> runningProcesses = ServicesGaugeUtils.getRunningProcess();

        for(String needService : remoteProperties.getNeedServices()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("serviceName="+needService+";");
            stringBuilder.append("host="+ remoteProperties.getHost()+";");
            if(runningProcesses.contains(needService)){
                stringBuilder.append("available="+"true"+";");
            }else {
                stringBuilder.append("available="+"false"+";");
            }

            gauges.put("service" + "." + needService, new CustomServicesGauge(needService,remoteProperties.getHost()));
        }

        return gauges;
    }


}
