package me.teamrocket.uno.gui;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.players.UnoSteve;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectColorMenu implements InventoryHolder, Listener
{
    private UnoDuelist plugin;
    private Inventory inv;

    public SelectColorMenu(UnoDuelist plugin)
    {
        this.plugin = plugin;
        inv = Bukkit.createInventory(this, 9, "Pick a Color!");
        populateMenu();
    }

    public void populateMenu()
    {
        ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metaRed = red.getItemMeta();
        metaRed.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lRED"));
        red.setItemMeta(metaRed);

        ItemStack blue = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta metaBlue = blue.getItemMeta();
        metaBlue.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lBLUE"));
        blue.setItemMeta(metaBlue);

        ItemStack green = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta metaGreen = green.getItemMeta();
        metaGreen.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lGREEN"));
        green.setItemMeta(metaGreen);

        ItemStack yellow = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metaYellow = yellow.getItemMeta();
        metaYellow.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lYELLOW"));
        yellow.setItemMeta(metaYellow);

        inv.setItem(1, red);
        inv.setItem(3, yellow);
        inv.setItem(5, green);
        inv.setItem(7, blue);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().getHolder() != this)
        {
            return;
        }

        e.setCancelled(true);

        switch (e.getSlot())
        {
            case 1: // red
                plugin.getUnoGame().setNextColor(Card.Color.RED);
                plugin.getAiTask().runTaskLater(plugin, 40L);
                e.getWhoClicked().closeInventory();
                break;
            case 3: // yellow
                plugin.getUnoGame().setNextColor(Card.Color.YELLOW);
                plugin.getAiTask().runTaskLater(plugin, 40L);
                e.getWhoClicked().closeInventory();
                break;
            case 5: // green
                plugin.getUnoGame().setNextColor(Card.Color.GREEN);
                plugin.getAiTask().runTaskLater(plugin, 40L);
                e.getWhoClicked().closeInventory();
                break;
            case 7: // blue
                plugin.getUnoGame().setNextColor(Card.Color.BLUE);
                plugin.getAiTask().runTaskLater(plugin, 40L);
                e.getWhoClicked().closeInventory();
                break;
        }
    }

    @Override
    public Inventory getInventory()
    {
        return inv;
    }
}
