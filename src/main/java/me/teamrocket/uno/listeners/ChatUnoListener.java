package me.teamrocket.uno.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatUnoListener implements Listener
{
    public ChatUnoListener()
    {

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        String message = " " + e.getMessage() + " ";
        if (message.toLowerCase().contains(" uno "))
        {
            Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + " said UNO!");
            e.setCancelled(true);
        }
    }
}