package ru.fts.metrics.servicesmetrics;

import com.codahale.metrics.Gauge;
import ru.fts.metrics.servicesmetrics.utils.ServicesGaugeUtils;

import java.util.List;

/**
 * Created by Danil on 11.01.2018.
 */
public class CustomServicesGauge implements Gauge<String> {

    private String needService;
    private String host;

    public CustomServicesGauge(String needService, String host){
        this.needService = needService;
        this.host = host;
    }

    @Override
    public String getValue() {
        List<String> runningProcesses = ServicesGaugeUtils.getRunningProcess();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("serviceName="+needService+";");
        stringBuilder.append("host="+ host+";");
        if(runningProcesses.contains(needService)){
            stringBuilder.append("available="+"true"+";");
        }else {
            stringBuilder.append("available="+"false"+";");
        }

        return stringBuilder.toString();
    }
}
