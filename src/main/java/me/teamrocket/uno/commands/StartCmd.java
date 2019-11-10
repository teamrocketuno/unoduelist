package me.teamrocket.uno.commands;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCmd implements CommandExecutor
{
    private UnoDuelist plugin;

    public StartCmd(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (plugin.getUnoGame().hasStarted())
        {
            sender.sendMessage(Locale.ALREADY_STARTED.msg());
            return true;
        }

        Bukkit.broadcastMessage(Locale.STARTING.msg(sender.getName()));
        plugin.getUnoGame().onStart();

        return true;
    }
}