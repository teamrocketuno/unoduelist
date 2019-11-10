package me.teamrocket.uno.locale;

import org.bukkit.ChatColor;

public enum Locale
{
    JOIN_TITLE("&c&lUNO &f&lDuelist!"),
    JOIN_SUBTITLE("&f&oby teamrocket"),
    BOSS_BAR_CARD("&f&lCard on top: &r{0}"),
    ALREADY_STARTED("&cThe game has already started."),
    STARTING("{0} is starting the game!"),
    TURN_ACTIONBAR("It is &l{0}'s&f turn."),
    NOT_YOUR_TURN("&cIt isn't your turn!"),
    INVALID_CARD("&cYou cannot play this card!"),
    START_BOSSBAR("&fUse &l/start&f to begin!"),
    START_BOSSBAR2("Talk to the &lmayor&f to begin!"),
    CHOOSE_COLOR("{0} chose the color {1}");

    private String msg;

    Locale(String msg)
    {
        this.msg = msg;
    }

    public String msg(String... args)
    {
        String manip = msg;
        int index = 0;
        for (String rep : args)
        {
            String replaceMarker = "{" + index + "}";

            int indexOf = manip.indexOf(replaceMarker);
            if (indexOf != -1)
            {
                manip = manip.substring(0, indexOf) + rep + manip.substring(indexOf + replaceMarker.length());
            }

            index++; // increase index for replace marker
        }

        return ChatColor.translateAlternateColorCodes('&', manip);
    }
}