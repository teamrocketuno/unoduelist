package me.teamrocket.uno.listeners;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.locale.Locale;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HotbarListener implements Listener
{
    private UnoDuelist plugin;

    public HotbarListener(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemHeldEvent(PlayerItemHeldEvent e)
    {
        if (plugin.toggledPlayers.contains(e.getPlayer()))
        {
            switch (e.getNewSlot())
            {
                case 0:
                    plugin.getUnoGame().getUnoSteve(e.getPlayer()).openMenu();
                    break;
                case 1:
                    if (plugin.getUnoGame().getUnoSteve(e.getPlayer()) == plugin.getUnoGame().getCurrentPlayer())
                    {
                        plugin.getUnoGame().getUnoSteve(e.getPlayer()).drawCard(plugin.getUnoGame().getDeck());
                    }
                    else
                    {
                        e.getPlayer().sendMessage(Locale.NOT_YOUR_TURN.msg());
                    }
                    break;
            }

            if (e.getNewSlot() != 8)
            {
                e.getPlayer().getInventory().setHeldItemSlot(8);
            }
        }
    }
}