package me.teamrocket.uno;

import me.teamrocket.uno.commands.StartCmd;
import me.teamrocket.uno.commands.ToggleCmd;
import me.teamrocket.uno.files.PluginFiles;
import me.teamrocket.uno.game.UnoGame;
import me.teamrocket.uno.gui.SelectColorMenu;
import me.teamrocket.uno.listeners.ChatUnoListener;
import me.teamrocket.uno.listeners.HotbarListener;
import me.teamrocket.uno.listeners.JoinListener;
import me.teamrocket.uno.listeners.MoveListener;
import me.teamrocket.uno.listeners.PvPListener;
import me.teamrocket.uno.listeners.RCVillagerListener;
import me.teamrocket.uno.runnables.AITask;
import me.teamrocket.uno.runnables.ActionbarTask;
import me.teamrocket.uno.runnables.BossBarTask;
import me.teamrocket.uno.runnables.ScoreboardTask;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class UnoDuelist extends JavaPlugin
{
    private UnoGame game;
    private BossBarTask barTask;
    private SelectColorMenu scm;

    //temp
    public ArrayList<Player> toggledPlayers;

    @Override
    public void onEnable()
    {
        //TEMP!
        toggledPlayers = new ArrayList<>();

        PluginFiles pf = new PluginFiles(this);
        barTask = new BossBarTask(this);
        ActionbarTask actionbarTask = new ActionbarTask(this);
        scm = new SelectColorMenu(this);

        pf.generateFolder();
        pf.getFiles();

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ChatUnoListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarListener(this), this);
        getServer().getPluginManager().registerEvents(new RCVillagerListener(this), this);
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);

        barTask.runTaskTimer(this, 0L, 20L);
        actionbarTask.runTaskTimer(this, 0L, 1L);
        new ScoreboardTask(this).runTaskTimer(this, 0L, 20L);

        getCommand("start").setExecutor(new StartCmd(this));
        getCommand("toggle").setExecutor(new ToggleCmd(this));

        game = new UnoGame(this);
    }

    @Override
    public void onDisable()
    {
        barTask.removeAllPlayers();
        game.killMobs();
    }

    public UnoGame getUnoGame()
    {
        return game;
    }

    public AITask getAiTask()
    {
        return new AITask(this);
    }

    public SelectColorMenu getSelectColorMenu()
    {
        return scm;
    }
}