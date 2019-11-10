package me.teamrocket.uno.card;

import me.teamrocket.uno.UnoDuelist;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck
{
    private UnoDuelist plugin;
    private ArrayList<Card> availableCards;

    public Deck(UnoDuelist plugin)
    {
        this.plugin = plugin;
        availableCards = new ArrayList<>();
    }

    public void populateDeck()
    {
        try
        {
            File text = new File(plugin.getDataFolder() + File.separator + "deck.txt");
            Scanner scnr = new Scanner(text);
            while (scnr.hasNextLine())
            {
                String line = scnr.nextLine();
                String[] words = line.split(" ");

                Card.Color color = Card.Color.valueOf(words[0]);
                Card.Type type = Card.Type.valueOf(words[1]);
                Card card = new Card(type, color);

                int numCards = Integer.parseInt(words[2]);
                for (int i = 0; i < numCards; i++)
                {
                    availableCards.add(card);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isEmpty()
    {
        return availableCards.size() == 0;
    }

    public Card nextCard()
    {
        if (isEmpty())
        {
            populateDeck();
        }

        int index = (int) (Math.random() * availableCards.size());
        return availableCards.remove(index);
    }
}