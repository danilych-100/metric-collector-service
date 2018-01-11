package ru.fts.metrics.servicesmetric;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fts.metrics.components.RemoteProperties;

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

        List<String> runningProcesses = getRunningProcess();

        for(String needService : remoteProperties.getNeedServices()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("serviceName="+needService+";");
            stringBuilder.append("host="+ remoteProperties.getHost()+";");
            if(runningProcesses.contains(needService)){
                stringBuilder.append("available="+"true"+";");
            }else {
                stringBuilder.append("available="+"false"+";");
            }

            gauges.put("service", new Gauge<String>() {
                @Override
                public String getValue() {
                    return stringBuilder.toString();
                }
            });
        }

        return gauges;
    }

    private List<String> getRunningProcess() {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("tasklist.exe /SVC /fo csv /nh");
            return extractProcessListFormProcess(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<String> extractProcessListFormProcess(Process p) throws IOException {
        List<String> processes = new ArrayList<String>();
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("CP866")));
        while ((line = input.readLine()) != null) {
            if (!line.trim().equals("")) {
                line = line.substring(1);
                line = line.substring(0, line.length() - 1);
                line = line.substring(line.lastIndexOf("\"") + 1);
                if (line.contains(",")) {
                    StringTokenizer tok = new StringTokenizer(line, ",");
                    while (tok.hasMoreTokens()) {
                        processes.add(tok.nextToken());
                    }
                } else {
                    processes.add(line);
                }
            }

        }
        input.close();
        return processes;
    }
}
