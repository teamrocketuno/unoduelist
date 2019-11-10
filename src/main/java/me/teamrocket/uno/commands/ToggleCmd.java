package me.teamrocket.uno.commands;

import me.teamrocket.uno.UnoDuelist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCmd implements CommandExecutor
{
    private UnoDuelist plugin;

    public ToggleCmd(UnoDuelist plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            return true;
        }

        if (plugin.toggledPlayers.contains((Player) sender))
        {
            plugin.toggledPlayers.remove((Player) sender);
            sender.sendMessage("OFF");
        }
        else
        {
            plugin.toggledPlayers.add((Player) sender);
            sender.sendMessage("ON");
        }

        return true;
    }
}