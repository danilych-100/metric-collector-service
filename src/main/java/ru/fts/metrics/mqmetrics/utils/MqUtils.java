package ru.fts.metrics.mqmetrics.utils;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;

import java.io.IOException;

/**
 * Created by Danil on 10.01.2018.
 */
public class MqUtils {

    private MqUtils(){}

    public static String[] getQueueNames(String qManagerName)  {
        PCFMessageAgent agent = null;
        try {
            agent = new PCFMessageAgent(qManagerName);
        } catch (MQException e) {

        }
        PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_NAMES);

        request.addParameter(CMQC.MQCA_Q_NAME, "*");
        request.addParameter(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);

        PCFMessage[] responses = new PCFMessage[0];
        try {
            responses = agent.send(request);
        } catch (MQException | IOException e) {

        }
        return (String[]) responses[0].getParameterValue(CMQCFC.MQCACF_Q_NAMES);
    }
}
