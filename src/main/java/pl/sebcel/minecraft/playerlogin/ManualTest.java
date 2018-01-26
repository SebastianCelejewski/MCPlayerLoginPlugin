package pl.sebcel.minecraft.playerlogin;

import java.util.Date;

public class ManualTest {

    public static void main(String[] args) {
        System.out.println("Sending a test notification");
        AwsSnsMessageSender messageSender = new AwsSnsMessageSender();
        String topicID = System.getenv().get("MCLoginPluginSNSTopicID");
        messageSender.initialize(topicID);
        messageSender.sendMessage("Test message sent at " + new Date().toString());
        System.out.println("Test notification has been sent successfully");
    }
}
