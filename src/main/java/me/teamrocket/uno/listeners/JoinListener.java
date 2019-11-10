package me.teamrocket.uno.listeners;

import me.teamrocket.uno.locale.Locale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        p.sendTitle(Locale.JOIN_TITLE.msg(), Locale.JOIN_SUBTITLE.msg(), 10, 30, 20);
    }
}