package me.luyz.utilities.item;

import org.bukkit.inventory.*;
import me.luyz.utilities.*;
import java.util.*;
import org.bukkit.enchantments.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class ItemBuilder
{
    private final ItemStack itemStack;

    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(final String material) {
        this.itemStack = new ItemStack(Material.matchMaterial(material), 1);
    }

    public ItemBuilder(final int material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemBuilder(final Material material, final int data) {
        this.itemStack = new ItemStack(material, 1, (short)data);
    }

    public ItemBuilder(final Material material, final int amount, final int data) {
        this.itemStack = new ItemStack(material, amount, (short)data);
    }

    public ItemBuilder setName(String name) {
        if (name != null) {
            name = ChatUtil.translate(name);
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.setDisplayName(name);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        if (lore != null) {
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore((List)ChatUtil.translate(lore));
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        if (lore != null) {
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore((List)ChatUtil.translate(Arrays.asList(lore)));
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setEnchant(final boolean enchanted) {
        if (enchanted) {
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setEnchant(final boolean enchanted, final int level) {
        if (enchanted) {
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, level, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setEnchant(final boolean enchanted, final Enchantment enchant, final int level) {
        if (enchanted) {
            final ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(enchant, level, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setData(final int dur) {
        this.itemStack.setDurability((short)dur);
        return this;
    }

    public ItemBuilder setOwner(final String owner) {
        if (this.itemStack.getType() == Material.SKULL_ITEM) {
            final SkullMeta meta = (SkullMeta)this.itemStack.getItemMeta();
            meta.setOwner(owner);
            this.itemStack.setItemMeta((ItemMeta)meta);
        }
        return this;
    }

    public ItemBuilder setArmorColor(final Color color) {
        if (color == null) {
            return this;
        }
        if (!this.itemStack.getType().name().startsWith("LEATHER")) {
            throw new IllegalArgumentException("setArmorColor() only applicable for LeatherArmor");
        }
        final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        this.itemStack.setItemMeta((ItemMeta)leatherArmorMeta);
        return this;
    }

    public static ItemStack getSkull(final String name) {
        return new ItemBuilder(Material.SKULL_ITEM).setData(3).setOwner(name).build();
    }

    public ItemStack build() {
        return this.itemStack;
    }
}