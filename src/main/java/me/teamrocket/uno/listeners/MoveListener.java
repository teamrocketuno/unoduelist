package me.teamrocket.uno.listeners;

import me.teamrocket.uno.UnoDuelist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener
{
    private UnoDuelist plugin;

    public MoveListener(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (plugin.getUnoGame().hasStarted())
        {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ())
            {
                e.setCancelled(true);
            }
        }
    }
}