package net.doh1221;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;

public class MessageHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        String message;
        if (e.getMember() != null) {
            if(e.getMessage().getChannel().getIdLong() == DiscordLoader.channel_id) {
                System.out.println(e.getMessage().getChannel().getIdLong() + " " + DiscordLoader.channel_id);
                message = "[§9Discord §7| §e" + e.getMember().getNickname() + "] " + e.getMessage().getContentDisplay();
                if(!DiscordLoader.useSCharsM) {
                    if((message.startsWith("_") || message.endsWith("_")) || (message.startsWith("*") || message.endsWith("*")) || (message.startsWith("~") || message.endsWith("~"))) {
                        message = message.replaceAll("_", "").replaceAll("\\d*", "").replaceAll("~", "");
                        Bukkit.broadcastMessage(message);
                    }
                }
            }
        }
    }

}
