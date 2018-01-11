package ru.fts.metrics.servicesmetrics.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Danil on 11.01.2018.
 */
public class ServicesGaugeUtils {

    private ServicesGaugeUtils(){}

    public static List<String> getRunningProcess() {
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
