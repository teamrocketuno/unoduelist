package me.teamrocket.uno.players;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.game.UnoGame;
import me.teamrocket.uno.gui.InventoryMenu;
import org.bukkit.entity.Player;

public class UnoSteve extends UnoPlayer
{
    private Player player;
    private InventoryMenu menu;

    public UnoSteve(UnoDuelist plugin, UnoGame game, Player p)
    {
        super(plugin, game);
        this.player = p;
        menu = new InventoryMenu(this);
        plugin.getServer().getPluginManager().registerEvents(menu, plugin);
    }

    public String getName()
    {
        return player.getPlayerListName();
    }

    public boolean isPlayer(Player p)
    {
        return p.getName().equals(player.getName());
    }

    public void openMenu()
    {
        menu.populateMenu();
        player.openInventory(menu.getInventory());
    }

    public Player getPlayer()
    {
        return player;
    }
}