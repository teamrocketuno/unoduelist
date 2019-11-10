package me.teamrocket.uno.runnables;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.players.UnoBot;
import me.teamrocket.uno.players.UnoPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class AITask extends BukkitRunnable
{
    private UnoDuelist plugin;

    public AITask(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        if (plugin.getUnoGame().hasStarted())
        {
            UnoPlayer unoPlayer = plugin.getUnoGame().getCurrentPlayer();
            if (unoPlayer instanceof UnoBot)
            {
                UnoBot bot = (UnoBot) unoPlayer;
                bot.doAI();
            }
        }
    }
}