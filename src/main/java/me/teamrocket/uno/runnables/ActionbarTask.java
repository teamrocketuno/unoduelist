package me.teamrocket.uno.runnables;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.game.UnoGame;
import me.teamrocket.uno.locale.Locale;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionbarTask extends BukkitRunnable
{
    private UnoDuelist plugin;

    public ActionbarTask(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        UnoGame g = plugin.getUnoGame();
        if (g.hasStarted())
        {
            for (Player p : plugin.getServer().getOnlinePlayers())
            {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Locale.TURN_ACTIONBAR.msg(g.getCurrentPlayer().getName())));
            }
        }
    }
}