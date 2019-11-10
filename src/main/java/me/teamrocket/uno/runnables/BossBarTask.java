package me.teamrocket.uno.runnables;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.card.Deck;
import me.teamrocket.uno.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarTask extends BukkitRunnable
{
    private UnoDuelist plugin;
    private BossBar bar;
    private Deck deck;

    public BossBarTask(UnoDuelist plugin)
    {
        this.plugin = plugin;
        bar = Bukkit.createBossBar("title", BarColor.BLUE, BarStyle.SOLID);
        deck = new Deck(plugin);
    }

    @Override
    public void run()
    {
        if (plugin.getUnoGame().hasStarted())
        {
            Card c = plugin.getUnoGame().getTopCard();

            if (c.getColor() == Card.Color.WILD)
            {
                bar.setColor(BarColor.PURPLE);
            }
            else
            {
                bar.setColor(BarColor.valueOf(c.getColor().name()));
            }

            if (plugin.getUnoGame().getNextColor() != null)
            {
                bar.setColor(BarColor.valueOf(plugin.getUnoGame().getNextColor().name()));
            }

            bar.setTitle(Locale.BOSS_BAR_CARD.msg(c.getCardName()));
        }
        else
        {
            bar.setColor(BarColor.WHITE);
            bar.setTitle(Locale.START_BOSSBAR2.msg());
        }

        for (Player p : plugin.getServer().getOnlinePlayers())
        {
            if (!bar.getPlayers().contains(p))
            {
                bar.addPlayer(p);
            }
        }
    }

    public void removeAllPlayers()
    {
        bar.removeAll();
    }
}