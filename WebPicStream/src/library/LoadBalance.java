package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is for loadBalance and R/W Splitting between databases,
 * using weighted Round Robin algorithm.
 * @author Group 10
 */
public class LoadBalance
{
    private String[] SlaveIP;
    private int[] weight;
    private int[] effectiveWeight;
    private int[] currentWeight;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String ping_regex = "(时间=)(.*)(ms)";

    static final String USER = "admin";
    static final String PASS = "eshopool";

    /**
     * This method is used for load balance by inputted IP pool.
     * @param SlaveIP the IP pool
     */
    public LoadBalance(String[] SlaveIP)
    {

        this.SlaveIP = SlaveIP;
        int numOfSlave = SlaveIP.length;

        //measure latency between databases
        Double[] latency = new Double[numOfSlave];
        weight = new int[numOfSlave];
        effectiveWeight = new int[numOfSlave];
        currentWeight = new int[numOfSlave];
        double sum = 0.0;

        for(int i = 0; i< numOfSlave; i++) {
            latency[i] = pingIP(SlaveIP[i]);
            sum += latency[i];
            currentWeight[i] = 0;
        }


        //give weights to write databases
        for(int i = 0; i < latency.length; i++)
        {
            if(latency[i] == 100)
            {
                weight[i] = 0;
                continue;
            }
            if(sum > latency[i]) weight[i] = (int)Math.round(sum - latency[i]);
            else weight[i] = (int)Math.round(latency[i]);
        }

        effectiveWeight = weight.clone();

    }

    /**Get the IP of one database for reading data<P/>
     * @return IP of one database for reading
     *
     */
    public int readIP()
    {
        int max= 0;
        int maxIndex = 0;
        int totalWeight = 0;
        for(int i = 0; i < SlaveIP.length; i++)
        {
            currentWeight[i] += effectiveWeight[i];
            totalWeight += effectiveWeight[i];
            if(currentWeight[i] > max)
            {
                max = currentWeight[i];
                maxIndex = i;
            }
        }

        currentWeight[maxIndex] -=  totalWeight;

        return maxIndex;
    }

    /**
     * reduce the weight of one write database.<P/>
     * database that fails three times will be given a weight of 0.
     * @param index index of the database
     */
    public void effectWeightDown(int index)
    {
        if(effectiveWeight[index] > (0.33 * weight[index]))
            effectiveWeight[index] = (int) (0.69 * effectiveWeight[index]);
        else
            effectiveWeight[index] = 0;

    }

    /**
     * return to the initial weight for a write database<P/>
     * @param index index of the database
     */
    public void effectWeightUp(int index)
    {
        if(effectiveWeight[index] < weight[index])
            effectiveWeight[index] = weight[index];
    }

    public Double pingIP(String ip){
        Runtime runtime = Runtime.getRuntime();
        Double result = 0.0;
        try {
            String line = null;
            Process process = runtime.exec("ping " + ip);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String judge1 = "无法访问目标主机";
            judge1 = new String(judge1.getBytes("gbk"),"utf-8");

            String judge2 = "请求超时";
            judge2 = new String(judge2.getBytes("gbk"),"utf-8");

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains(judge1) || line.contains(judge2)) {
                    result = 100.0;
                    break;
                } else if(line.contains("TTL")) {
                    Pattern r = Pattern.compile(ping_regex);
                    Matcher m = r.matcher(line);
                    if (m.find()) result = Double.parseDouble(m.group(2));
                    break;
                }
            }
        } catch (IOException e) {
            result = 100.0;
        }
        return result;
    }

}
