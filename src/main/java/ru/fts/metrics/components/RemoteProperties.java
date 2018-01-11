package ru.fts.metrics.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class RemoteProperties {

    @Value("#{'${queueManagers}'.split(',')}")
    private List<String> queueManagers;

    @Value("#{'${services}'.split(',')}")
    private List<String> needServices;

    @Value("${metrics.host}")
    private String host;


    public List<String> getQueueManagers() {
        return queueManagers;
    }

    public List<String> getNeedServices() {
        return needServices;
    }

    public String getHost() {
        return host;
    }

}
