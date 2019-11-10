package me.teamrocket.uno.card;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Card
{
    public enum Type
    {
        ONE(2, "1"),
        TWO(2, "2"),
        THREE(2, "3"),
        FOUR(2, "4"),
        FIVE(2, "5"),
        SIX(2, "6"),
        SEVEN(2, "7"),
        EIGHT(2, "8"),
        NINE(2, "9"),
        SKIP(3, "SKIP"),
        REVERSE(3, "REVERSE"),
        PLUS2(4, "+2"),
        PLUS4(0, "+4"),
        WILD(1, "CARD");

        public int priority;
        public String name;

        Type(int priority, String name)
        {
            this.priority = priority;
            this.name = name;
        }
    }

    public enum Color
    {
        RED("&c&lRED "),
        GREEN("&a&lGREEN "),
        BLUE("&3&lBLUE "),
        YELLOW("&6&lYELLOW "),
        WILD("&c&lW&6&lI&a&lL&3&lD &5&l");

        public String name;

        Color(String name)
        {
            this.name = name;
        }
    }

    private Card.Type type;
    private Card.Color color;

    public Card(Type type, Color color)
    {
        this.type = type;
        this.color = color;
    }

    public Type getType()
    {
        return type;
    }

    public Color getColor()
    {
        return color;
    }

    public ItemStack getItem()
    {
        Material mat;
        switch (color)
        {
            case RED:
                mat = Material.RED_STAINED_GLASS_PANE;
                break;
            case BLUE:
                mat = Material.BLUE_STAINED_GLASS_PANE;
                break;
            case GREEN:
                mat = Material.GREEN_STAINED_GLASS_PANE;
                break;
            case YELLOW:
                mat = Material.YELLOW_STAINED_GLASS_PANE;
                break;
            default:
                mat = Material.BLACK_STAINED_GLASS_PANE;
                break;
        }

        ItemStack result = new ItemStack(mat);
        if (type == Type.SKIP || type == Type.REVERSE || type == Type.PLUS2 || type == Type.PLUS4)
        {
            result.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }

        switch (type)
        {
            case TWO:
                result.setAmount(2);
                break;
            case THREE:
                result.setAmount(3);
                break;
            case FOUR:
                result.setAmount(4);
                break;
            case FIVE:
                result.setAmount(5);
                break;
            case SIX:
                result.setAmount(6);
                break;
            case SEVEN:
                result.setAmount(7);
                break;
            case EIGHT:
                result.setAmount(8);
                break;
            case NINE:
                result.setAmount(9);
                break;
        }

        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(getCardName());
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", Math.random(), AttributeModifier.Operation.ADD_SCALAR));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        result.setItemMeta(meta);

        return result;
    }

    public String getCardName()
    {
        return ChatColor.translateAlternateColorCodes('&', color.name + type.name);
    }
}