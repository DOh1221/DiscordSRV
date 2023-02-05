package net.doh1221;

import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.doh1221.DiscordLoader.*;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {

    public PlayerListener(DiscordLoader instance) {

    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) {
        String message = e.getMessage();
        if(!DiscordLoader.useSCharsD) {
            if((message.startsWith("_") || message.endsWith("_")) || (message.startsWith("*") || message.endsWith("*")) || (message.startsWith("~") || message.endsWith("~"))) {
                message = message.replaceAll("_", "").replaceAll("\\d*", "").replaceAll("~", "");
                jda.getTextChannelById(channel_id).sendMessage(message).submit();
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String name = e.getPlayer().getName().replaceAll("_", "\\_");
        jda.getTextChannelById(channel_id).sendMessage("Игрок **" + name + "** зашёл на сервер!").submit();
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent e) {
        String name = e.getPlayer().getName().replaceAll("_", "\\_");
        jda.getTextChannelById(channel_id).sendMessage("Игрок **" + name + "** покинул сервер").submit();
    }

}
