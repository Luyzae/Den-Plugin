package me.luyz.utilities;

import java.util.*;
import org.bukkit.entity.*;
import com.google.common.collect.*;

public final class CooldownUtil {

    private static final Table<String, UUID, Long> cooldowns;

    public static boolean hasCooldown(final String name, final Player player) {
        return CooldownUtil.cooldowns.contains((Object)name, (Object)player.getUniqueId()) && (long)CooldownUtil.cooldowns.get((Object)name, (Object)player.getUniqueId()) > System.currentTimeMillis();
    }

    public static void setCooldown(final String name, final Player player, final int seconds) {
        CooldownUtil.cooldowns.put(name, player.getUniqueId(), System.currentTimeMillis() + seconds * 1000L);
    }

    public static long getCooldown(final String name, final Player player) {
        return (long)CooldownUtil.cooldowns.get((Object)name, (Object)player.getUniqueId()) - System.currentTimeMillis();
    }

    public static void removeCooldown(final String name, final Player player) {
        CooldownUtil.cooldowns.remove((Object)name, (Object)player.getUniqueId());
    }

    private CooldownUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Table<String, UUID, Long> getCooldowns() {
        return CooldownUtil.cooldowns;
    }

    static {
        cooldowns = (Table)HashBasedTable.create();
    }

}
