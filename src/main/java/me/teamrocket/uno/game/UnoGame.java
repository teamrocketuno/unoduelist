package me.teamrocket.uno.game;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.card.Deck;
import me.teamrocket.uno.players.UnoBot;
import me.teamrocket.uno.players.UnoPlayer;
import me.teamrocket.uno.players.UnoSteve;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class UnoGame
{
    private UnoDuelist plugin;
    private boolean started;
    private Deck deck;
    private Card topCard;
    private UnoSteve[] players;
    private UnoBot[] bots;
    private UnoPlayer currentPlayer;
    private int turnIndex;

    private ArrayList<Mob> mobs;
    private boolean isReversed;

    private Card.Color nextColor;

    public UnoGame(UnoDuelist plugin)
    {
        this.plugin = plugin;
        deck = new Deck(plugin);
        topCard = deck.nextCard();
        started = false;

        players = new UnoSteve[4];
        bots = new UnoBot[4];
        currentPlayer = null;
        turnIndex = 0;

        mobs = new ArrayList<>();
        isReversed = false;

        nextColor = null;
    }

    public boolean doTurn(Card cardPlayed)
    {
        Bukkit.broadcastMessage(currentPlayer.getName() + " played " + cardPlayed.getCardName());
        if (cardPlayed.getColor() != Card.Color.WILD)
        {
            nextColor = null;
        }

        switch (cardPlayed.getType())
        {
            case REVERSE:
                isReversed = !isReversed;
                Bukkit.broadcastMessage("The turn order was reversed!");
                break;
            case PLUS2:
                if (currentPlayer instanceof UnoSteve)
                {
                    if (isReversed)
                    {
                        turnIndex--;
                        if (turnIndex < 0)
                        {
                            turnIndex = 3;
                        }
                        currentPlayer = bots[turnIndex];
                    }
                    else
                    {
                        currentPlayer = bots[turnIndex];
                    }
                }
                else
                {
                    if (isReversed)
                    {
                        currentPlayer = players[turnIndex];
                    }
                    else
                    {
                        turnIndex++;
                        if (turnIndex > 3)
                        {
                            turnIndex = 0;
                        }
                        currentPlayer = players[turnIndex];
                    }
                }

                currentPlayer.drawCards(deck, 2);
                Bukkit.broadcastMessage(currentPlayer.getName() + " was skipped!");
                break;
            case SKIP:
                if (currentPlayer instanceof UnoSteve)
                {
                    if (isReversed)
                    {
                        turnIndex--;
                        if (turnIndex < 0)
                        {
                            turnIndex = 3;
                        }
                        currentPlayer = bots[turnIndex];
                    }
                    else
                    {
                        currentPlayer = bots[turnIndex];
                    }
                }
                else
                {
                    if (isReversed)
                    {
                        currentPlayer = players[turnIndex];
                    }
                    else
                    {
                        turnIndex++;
                        if (turnIndex > 3)
                        {
                            turnIndex = 0;
                        }
                        currentPlayer = players[turnIndex];
                    }
                }

                Bukkit.broadcastMessage(currentPlayer.getName() + " was skipped!");
                break;
        }

        if (currentPlayer instanceof UnoSteve)
        {
            boolean wildPending = cardPlayed.getColor() == Card.Color.WILD;

            if (!wildPending)
            {
                plugin.getAiTask().runTaskLater(plugin, 40L);
            }
            else
            {
                ((UnoSteve) currentPlayer).getPlayer().openInventory(plugin.getSelectColorMenu().getInventory());
                if (cardPlayed.getType() == Card.Type.PLUS4)
                {
                    if (isReversed)
                    {
                        currentPlayer = players[turnIndex];
                    }
                    else
                    {
                        turnIndex++;
                        if (turnIndex > 3)
                        {
                            turnIndex = 0;
                        }
                        currentPlayer = players[turnIndex];
                    }
                    currentPlayer.drawCards(deck, 4);
                    Bukkit.broadcastMessage(currentPlayer.getName() + " was skipped!");
                }
            }

            if (isReversed)
            {
                turnIndex--;
                if (turnIndex < 0)
                {
                    turnIndex = 3;
                }
                currentPlayer = bots[turnIndex];
            }
            else
            {
                currentPlayer = bots[turnIndex];
            }
        }
        else
        {
            if (isReversed)
            {
                currentPlayer = players[turnIndex];
            }
            else
            {
                turnIndex++;
                if (turnIndex > 3)
                {
                    turnIndex = 0;
                }
                currentPlayer = players[turnIndex];
            }
        }

        topCard = cardPlayed;
        return true;
    }

    public void setNextColor(Card.Color color)
    {
        nextColor = color;
    }

    public boolean isCardValid(Card card)
    {
        if (topCard.getColor() == Card.Color.WILD)
        {
            return card.getColor() == nextColor;
        }

        return card.getType() == topCard.getType() || card.getColor() == topCard.getColor() || card.getColor() == Card.Color.WILD;
    }

    public boolean onStart()
    {
        if (started)
        {
            return false;
        }

        started = true;

        bots[0] = new UnoBot(plugin, this, "Skeleboy");
        spawnEntity(EntityType.SKELETON, new Location(Bukkit.getWorld("world"), 233.5, 137, 338.5, 180, 0));
        bots[1] = new UnoBot(plugin, this, "Cweeper");
        spawnEntity(EntityType.CREEPER, new Location(Bukkit.getWorld("world"), 237.5, 137, 338.5, 180, 0));
        bots[2] = new UnoBot(plugin, this, "Zomble");
        spawnEntity(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"), 241.5, 137, 338.5, 180, 0));
        bots[3] = new UnoBot(plugin, this, "Spoder");
        spawnEntity(EntityType.SPIDER, new Location(Bukkit.getWorld("world"), 245.5, 137, 338.5, 180, 0));

        Iterator it = plugin.getServer().getOnlinePlayers().iterator();
        for (int i = 0; i < 4; ++i)
        {
            Player p = (Player) it.next();
            p.teleport(new Location(Bukkit.getWorld("world"), 233.5 + i * 4, 137, 318.5));
            players[i] = new UnoSteve(plugin, this, p);
        }

        // Determine who goes first
        turnIndex = (int) (Math.random() * 4);
        int side = (int) (Math.random() * 2);

        boolean botsFirst = false;
        if (side == 0)
        {
            botsFirst = true;
        }

        if (botsFirst)
        {
            currentPlayer = bots[turnIndex];
            plugin.getAiTask().runTaskLater(plugin, 40L);
        }
        else
        {
            currentPlayer = players[turnIndex];
        }

        Bukkit.broadcastMessage("The game has begun. First up is " + currentPlayer.getName() + "!");
        return true;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public boolean hasStarted()
    {
        return started;
    }

    public UnoPlayer getCurrentPlayer()
    {
        return currentPlayer;
    }

    public UnoSteve getUnoSteve(Player p)
    {
        for (UnoSteve steve : players)
        {
            if (steve.isPlayer(p))
            {
                return steve;
            }
        }

        return null;
    }

    public boolean isYourTurn(UnoPlayer p)
    {
        return p == currentPlayer;
    }

    public Card getTopCard()
    {
        return topCard;
    }

    private void spawnEntity(EntityType type, Location l)
    {
        Mob m = (Mob) l.getWorld().spawnEntity(l, type);
        m.setAI(false);
        m.setInvulnerable(true);
        mobs.add(m);
    }

    public void killMobs()
    {
        for (Mob m : mobs)
        {
            m.damage(10000);
        }

        mobs = new ArrayList<>();
    }

    public Card.Color getNextColor()
    {
        return nextColor;
    }

    public ArrayList<UnoPlayer> getAllPlayers()
    {
        ArrayList<UnoPlayer> listOfPlayers = new ArrayList<>();

        for (UnoPlayer p : bots)
        {
            listOfPlayers.add(p);
        }

        for (UnoPlayer p : players)
        {
            listOfPlayers.add(p);
        }

        return listOfPlayers;
    }
}