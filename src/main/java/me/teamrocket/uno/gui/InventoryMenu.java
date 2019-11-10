package me.teamrocket.uno.gui;

import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.locale.Locale;
import me.teamrocket.uno.players.UnoSteve;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMenu implements InventoryHolder, Listener
{
    private UnoSteve steve;
    private Inventory inv;

    public InventoryMenu(UnoSteve steve)
    {
        this.steve = steve;
        inv = Bukkit.createInventory(this, 54, "Your Deck");
    }

    public void populateMenu()
    {
        inv.clear();
        for (Card c : steve.getHand())
        {
            inv.addItem(c.getItem());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().getHolder() != this)
        {
            return;
        }

        e.setCancelled(true);

        if (e.getRawSlot() > 53)
        {
            return;
        }

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
        {
            return;
        }

        if (steve.getGame().isYourTurn(steve))
        {
            if (steve.getGame().isCardValid(steve.getHand().get(e.getRawSlot())))
            {
                steve.getGame().doTurn(steve.getHand().remove(e.getRawSlot()));
            }
            else
            {
                e.getWhoClicked().sendMessage(Locale.INVALID_CARD.msg());
            }
        }
        else
        {
            e.getWhoClicked().sendMessage(Locale.NOT_YOUR_TURN.msg());
        }

        e.getWhoClicked().closeInventory();
    }

    @Override
    public Inventory getInventory()
    {
        return inv;
    }
}