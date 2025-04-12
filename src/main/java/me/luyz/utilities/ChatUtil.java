package me.luyz.utilities;

import me.luyz.hooks.placeholder.PlaceholderHook;
import org.bukkit.entity.*;
import me.clip.placeholderapi.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.apache.commons.lang.*;
import org.bukkit.inventory.*;

public final class ChatUtil {

    public static String NORMAL_LINE;

    public static String translate(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public static String[] translate(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = translate(array[i]);
        }
        return array;
    }

    public static String placeholder(final Player player, final String text) {
        return translate(PlaceholderHook.isEnabled() ? PlaceholderAPI.setPlaceholders(player, text) : text);
    }

    public static List<String> placeholder(final Player player, final List<String> text) {
        return translate(PlaceholderHook.isEnabled() ? PlaceholderAPI.setPlaceholders(player, (List)text) : text);
    }

    public static List<String> translate(final List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }


    public static void sendMessage(final CommandSender sender, final String text) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            player.sendMessage(translate(text));
        }
        else {
            sender.sendMessage(translate(text));
        }
    }

    public static void sendMessage(final CommandSender sender, final String[] array) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            player.sendMessage(translate(array));
        }
        else {
            sender.sendMessage(translate(array));
        }
    }

    public static String strip(final String text) {
        return ChatColor.stripColor(text);
    }

    public static void broadcast(final String text) {
        Bukkit.broadcastMessage(translate(text));
    }

    public static void logger(final String text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public static void logger(final String[] text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public static String capitalize(final String str) {
        return WordUtils.capitalize(str);
    }

    public static String toReadable(final Enum<?> enu) {
        return capitalize(enu.name().replace('_', ' ').toLowerCase());
    }

    public static String toReadable(final ItemStack itemStack) {
        return (itemStack.getItemMeta().getDisplayName() != null) ? itemStack.getItemMeta().getDisplayName() : toReadable((Enum<?>)itemStack.getType());
    }

    private ChatUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        ChatUtil.NORMAL_LINE = "&7&m-----------------------------";
    }

}