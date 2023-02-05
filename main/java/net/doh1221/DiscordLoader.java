package net.doh1221;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DiscordLoader extends JavaPlugin {

    public Properties properties;
    public Reader reader;
    public static String bot_token;
    public static long channel_id;
    public static boolean isMessageAllowed;
    public static boolean useSCharsM;
    public static boolean useSCharsD;
    public static boolean useRoles;

    public EntityListener entityListener = new EntityListener(this);
    public PlayerListener playerListener = new PlayerListener(this);

    public static JDA jda;

    Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {

        log.info("DiscordSRV enabled! Sending discord message...");

        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.High, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);


        String path = "plugins/DiscordSRV/settings.properties";

        File file = new File("plugins/DiscordSRV/settings.properties");
        File folder = new File("plugins/DiscordSRV/");

        try {
            if(!folder.exists()) {
                folder.mkdir();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
            reader = new FileReader(path);
            this.properties = new Properties();
            properties.load(reader);

            properties.setProperty("bot_token", properties.getProperty("bot_token", "0"));
            properties.setProperty("channelID", properties.getProperty("channelID", "0"));
            properties.setProperty("send_discord_messages", properties.getProperty("send_discord_messages", "false"));
            properties.setProperty("use_minecraft_chars", properties.getProperty("use_minecraft_chars", "true"));
            properties.setProperty("use_discord_chars", properties.getProperty("use_discord_chars", "true"));
            properties.setProperty("show_roles", properties.getProperty("user_roles", "false"));

            this.bot_token = properties.getProperty("bot_token");
            this.channel_id = Long.parseLong(properties.getProperty("channelID"));
            this.isMessageAllowed = Boolean.parseBoolean(properties.getProperty("send_discord_messages"));
            this.useSCharsM = Boolean.parseBoolean(properties.getProperty("translate_minecraft_chars"));
            this.useSCharsD = Boolean.parseBoolean(properties.getProperty("translate_discord_chars"));
            this.useRoles = Boolean.parseBoolean(properties.getProperty("show_roles"));

            properties.store(new FileWriter(path), "DiscordSRV properties");
            jda = new JDABuilder(bot_token).setActivity(Activity.playing("Armlix.ru | InfdevShield"))
                    .addEventListeners(new MessageHandler())
                    .build();
            jda.setAutoReconnect(true);
            jda.awaitReady().getTextChannelById(channel_id).sendMessage("Сервер онлайн!").submit();
        } catch (IOException | LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

        log.info("DiscordSRV disabled! Sending discord message...");
        jda.getTextChannelById(channel_id).sendMessage("Сервер оффлайн!").timeout(5, TimeUnit.SECONDS).submit();
    }

    public static void sendMessageD(String message) throws InterruptedException {
        if(!useSCharsD) {
            if((message.startsWith("_") || message.endsWith("_")) || (message.startsWith("*") || message.endsWith("*")) || (message.startsWith("~") || message.endsWith("~"))) {
                message = message.replaceAll("_", "").replaceAll("\\d*", "").replaceAll("~", "");
                jda.awaitReady().getTextChannelById(channel_id).sendMessage(message).submit();
            }
        }
    }

    public static void sendMessageM(String name, String message) {
        if(!useSCharsM) {
            if((message.startsWith("_") || message.endsWith("_")) || (message.startsWith("*") || message.endsWith("*")) || (message.startsWith("~") || message.endsWith("~"))) {
                message = message.replaceAll("_", "").replaceAll("\\d*", "").replaceAll("~", "");
                Bukkit.broadcastMessage(name + " " + message);
            }
        }
    }

}
