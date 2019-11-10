package me.teamrocket.uno.players;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.card.Deck;
import me.teamrocket.uno.game.UnoGame;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public abstract class UnoPlayer
{
    protected UnoDuelist plugin;
    protected UnoGame game;
    protected ArrayList<Card> hand;

    public UnoPlayer(UnoDuelist plugin, UnoGame game)
    {
        this.plugin = plugin;
        this.game = game;
        hand = new ArrayList<>();
        firstHand(game.getDeck());
    }

    public void firstHand(Deck deck)
    {
        for (int i = 0; i < 7; i++)
        {
            hand.add(deck.nextCard());
        }
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public abstract String getName();

    public UnoGame getGame()
    {
        return game;
    }

    public void drawCard(Deck d)
    {
        Card c = d.nextCard();
        hand.add(c);

        Bukkit.broadcastMessage(getName() + " drew a card.");
    }

    public void drawCards(Deck d, int numCards)
    {
        for (int i = 0; i < numCards; i++)
        {
            hand.add(d.nextCard());
        }

        Bukkit.broadcastMessage(getName() + " drew " + numCards + " cards!");
    }
}