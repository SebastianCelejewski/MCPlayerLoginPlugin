package pl.sebcel.minecraft.playerlogin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerLoginPlugin extends JavaPlugin implements Listener {

    private final static String TOPIC_ID_ENV_VARIABLE_NAME = "MCLoginPluginSNSTopicID";
    private AwsSnsMessageSender messageSender = new AwsSnsMessageSender();

    @Override
    public void onEnable() {
        getLogger().info("PlayerLoginPlugin initialization started");

        getServer().getPluginManager().registerEvents(this, this);
        String topicID = System.getenv().get(TOPIC_ID_ENV_VARIABLE_NAME);
        if (topicID == null) {
            throw new RuntimeException("Failed to initialize PlayerLoginPlugin. Environment variable " + TOPIC_ID_ENV_VARIABLE_NAME + " is not set.");
        }
        messageSender.initialize(topicID);
        messageSender.sendMessage("PlayerLocationPlugin enabled on server " + getServer().getServerName());

        getLogger().info("PlayerLoginPlugin enabled.");
    }

    @Override
    public void onDisable() {
        messageSender.sendMessage("PlayerLocationPlugin disabled on server " + getServer().getServerName());
        getLogger().info("PlayerLocationPlugin disabled.");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        String message = "Player " + event.getPlayer().getDisplayName() + " logged in on server " + getServer().getServerName();
        getLogger().info(message);
        messageSender.sendMessage(message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String message = "Player " + event.getPlayer().getDisplayName() + " logged out from server " + getServer().getServerName();
        getLogger().info(message);
        messageSender.sendMessage(message);
    }
}