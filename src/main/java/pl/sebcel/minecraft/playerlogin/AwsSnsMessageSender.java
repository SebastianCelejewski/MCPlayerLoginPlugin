package pl.sebcel.minecraft.playerlogin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;

public class AwsSnsMessageSender {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AmazonSNSClient snsClient;
    private boolean initialized = false;
    private String queueID;

    public void initialize(String queueID) {
        snsClient = new AmazonSNSClient(new EnvironmentVariableCredentialsProvider());
        snsClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        initialized = true;
        this.queueID = queueID;
    }

    public void sendMessage(String message) {
        if (initialized) {
            snsClient.publish(queueID, df.format(new Date()) + ": " + message);
        }
    }
}