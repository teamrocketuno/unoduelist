package me.teamrocket.uno.files;

import me.teamrocket.uno.UnoDuelist;

import java.io.File;

public class PluginFiles
{
    private UnoDuelist plugin;

    public PluginFiles(UnoDuelist duelist)
    {
        plugin = duelist;
    }

    public void generateFolder()
    {
        try
        {
            File pluginFolder = plugin.getDataFolder();
            if (!pluginFolder.exists())
            {
                pluginFolder.mkdir();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getFiles()
    {
        try
        {
            File deckFile = new File(plugin.getDataFolder() + File.separator + "deck.txt");
            if (!deckFile.exists())
            {
                plugin.saveResource("deck.txt", false);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}