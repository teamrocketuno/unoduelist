package me.teamrocket.uno.players;

import me.teamrocket.uno.UnoDuelist;
import me.teamrocket.uno.card.Card;
import me.teamrocket.uno.game.UnoGame;
import me.teamrocket.uno.locale.Locale;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class UnoBot extends UnoPlayer
{
    private String name;

    public UnoBot(UnoDuelist plugin, UnoGame game, String name)
    {
        super(plugin, game);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void doAI()
    {
        ArrayList<Card> validCards = new ArrayList<>();
        int highestPriority = -1;

        for (int i = 0; i < hand.size(); i++)
        {
            if (game.isCardValid(hand.get(i)))
            {
                validCards.add(hand.get(i));
                if (highestPriority == -1)
                {
                    highestPriority = hand.get(i).getType().priority;
                }
                else
                {
                    int priorityCheck = hand.get(i).getType().priority;
                    if (highestPriority < priorityCheck)
                    {
                        highestPriority = priorityCheck;
                    }
                }
            }
        }

        if (validCards.isEmpty()) // Draw a card
        {
            drawCard(game.getDeck());
            plugin.getAiTask().runTaskLater(plugin, 10L);
        }
        else // Play a card
        {
            ArrayList<Card> matchPriority = new ArrayList<>();
            for (Card c : validCards)
            {
                if (c.getType().priority == highestPriority)
                {
                    matchPriority.add(c);
                }
            }

            Card cardToPlay = matchPriority.get((int) (Math.random() * matchPriority.size()));
            hand.remove(cardToPlay);
            game.doTurn(cardToPlay);

            if (cardToPlay.getColor() == Card.Color.WILD)
            {
                ArrayList<Card.Color> colorsToChoose = new ArrayList<>();
                for (Card c : hand)
                {
                    if (c.getColor() == Card.Color.WILD && colorsToChoose.isEmpty())
                    {
                        colorsToChoose.add(Card.Color.RED);
                        colorsToChoose.add(Card.Color.BLUE);
                        colorsToChoose.add(Card.Color.GREEN);
                        colorsToChoose.add(Card.Color.YELLOW);
                    }
                    else if (c.getColor() != Card.Color.WILD)
                    {
                        colorsToChoose.add(c.getColor());
                    }
                }

                Card.Color nextColor = colorsToChoose.get(((int) Math.random() * colorsToChoose.size()));

                game.setNextColor(nextColor);
                Bukkit.broadcastMessage(Locale.CHOOSE_COLOR.msg(name, nextColor.name));
            }
        }
    }

    public void sayUnoAI()
    {
        // SAYING UNO AI HERE
    }
}