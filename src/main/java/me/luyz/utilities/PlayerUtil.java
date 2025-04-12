package me.luyz.utilities;

import java.text.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import org.bukkit.inventory.*;

public final class PlayerUtil {

    public static DecimalFormat DECIMAL_FORMAT;

    public static String getHealth(final Player player) {
        return PlayerUtil.DECIMAL_FORMAT.format(player.getHealth() / 2.0);
    }

    public static int getPing(final Player player) {
        try {
            final String a = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            final Class<?> b = Class.forName("org.bukkit.craftbukkit." + a + ".entity.CraftPlayer");
            final Object c = b.getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
            return (int)c.getClass().getDeclaredField("ping").get(c);
        }
        catch (Exception ex) {
            return 0;
        }
    }

    public static boolean isInventoryFull(final Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public static void reset(final Player player) {
        player.setHealth(20.0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.setGameMode(GameMode.ADVENTURE);
        player.setInvulnerable(false);
    }

    public static void clear(final Player player, final boolean armor, final boolean inventory) {
        if (armor) {
            player.getInventory().setArmorContents((ItemStack[])null);
        }
        if (inventory) {
            player.getInventory().clear();
        }
    }

    public static void denyMovement(final Player player) {
        player.setWalkSpeed(0.0f);
        player.setFlySpeed(0.0f);
        player.setFoodLevel(1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
    }

    public static void allowMovement(final Player player) {
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.setFoodLevel(20);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    public static void hideAndShowPlayer(final Player player, final Player target) {
        for (final Player online : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(online);
        }
        player.showPlayer(target);
    }

    public static void showPlayers(final Player player) {
        for (final Player online : Bukkit.getOnlinePlayers()) {
            player.showPlayer(online);
        }
    }

    public static Inventory customPlayerInventory(final Player target) {
        final Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder)target, 54, "Inventory of " + target.getName());
        inventory.setContents(target.getInventory().getContents());
        inventory.setItem(45, (target.getInventory().getHelmet() == null) ? null : target.getInventory().getHelmet());
        inventory.setItem(46, (target.getInventory().getChestplate() == null) ? null : target.getInventory().getChestplate());
        inventory.setItem(47, (target.getInventory().getLeggings() == null) ? null : target.getInventory().getLeggings());
        inventory.setItem(48, (target.getInventory().getBoots() == null) ? null : target.getInventory().getBoots());
        return inventory;
    }

    private PlayerUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        PlayerUtil.DECIMAL_FORMAT = new DecimalFormat("#.#");
    }


}
