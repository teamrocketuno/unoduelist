package me.teamrocket.uno.listeners;

import me.teamrocket.uno.UnoDuelist;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class RCVillagerListener implements Listener
{
    private UnoDuelist plugin;

    public RCVillagerListener(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e)
    {
        if (e.getRightClicked().getType() == EntityType.VILLAGER)
        {
            Bukkit.broadcastMessage("Help us, heroes.");
            e.setCancelled(true);
        }

        if (e.getRightClicked().getType() == EntityType.WANDERING_TRADER)
        {
            Bukkit.broadcastMessage("Fellow Players, wll you help us in this time of chaos?");
            if (!plugin.getUnoGame().hasStarted())
            {
                plugin.getUnoGame().onStart();
            }
            e.setCancelled(true);

        }
    }
}
