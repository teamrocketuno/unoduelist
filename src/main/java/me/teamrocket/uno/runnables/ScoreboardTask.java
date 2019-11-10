package me.teamrocket.uno.runnables;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.players.UnoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardTask extends BukkitRunnable
{
    private UnoDuelist plugin;
    private Objective scObjective;
    private Scoreboard scoreboard;

    public ScoreboardTask(UnoDuelist plugin)
    {
        this.plugin = plugin;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scObjective = scoreboard.registerNewObjective("NumCards", "dummy", "Number of Cards");
    }

    @Override
    public void run()
    {
        if (plugin.getUnoGame().hasStarted())
        {
            for (Player p : plugin.getServer().getOnlinePlayers())
            {
                p.setScoreboard(scoreboard);
            }

            scObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            for (UnoPlayer unoPlayer : plugin.getUnoGame().getAllPlayers())
            {
                scObjective.getScore(unoPlayer.getName()).setScore(unoPlayer.getHand().size());
            }
        }
    }
}