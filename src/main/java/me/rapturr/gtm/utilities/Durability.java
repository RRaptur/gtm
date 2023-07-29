package me.rapturr.gtm.utilities;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class Durability {

    /**
     * DOESN'T WORK ON STACKABLE ITEMS!
     */

    int durability;
    int maxDurability;

    public Durability(int durability) {
        this.durability = durability;
        this.maxDurability = durability;
    }

    public void enforceDurability(ItemStack itemStack) {
        setInteger(itemStack, "durability", getDurability());
        setInteger(itemStack, "max-durability", getMaxDurability());

        displayDurability(itemStack);
    }

    public void removeDurability(int amount, ItemStack itemStack, Player player) {
        if (!hasDurability(itemStack)) {
            return;
        }

        if (getDurability(itemStack) <= 1) {

            player.getInventory().remove(itemStack);
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
        }

        else setDurability(getDurability(itemStack) - amount, itemStack);
    }

    public int getDurability(ItemStack itemStack) {
        return getInteger(itemStack, "durability");
    }

    public int getMaxDurability(ItemStack itemStack) {
        return getInteger(itemStack, "max-durability");
    }

    public boolean hasDurability(ItemStack itemStack) {
        return getInteger(itemStack, "max-durability") != null;
    }

    public void setDurability(int amount, ItemStack itemStack) {
        setInteger(itemStack, "durability", amount);
        displayDurability(itemStack);
    }

    public void setMaxDurability(int amount, ItemStack itemStack) {
        setInteger(itemStack, "max-durability", amount);
        displayDurability(itemStack);
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public void displayDurability(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        List<String> lore = itemMeta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }
        if (lore.isEmpty()) {
            lore.add("");
            lore.add("");
        }

        int size = lore.size();
        lore.set(size -2, "");
        lore.set(size -1, ChatColor.GRAY + "Durability: " + getDurability(itemStack) + "/" + getMaxDurability(itemStack));

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public Integer getInteger(ItemStack itemStack, String key)   {
        NBTItem nbtItem = new NBTItem(itemStack);
        Integer integer = nbtItem.getInteger(key);

        if (integer== null) {
            return null;
        } else return integer;
    }

    public void setInteger(ItemStack itemStack, String key, Integer value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(key, value);

        ItemStack updatedItemStack = nbtItem.getItem();

        ItemMeta updatedItemMeta = updatedItemStack.getItemMeta();
        itemStack.setItemMeta(updatedItemMeta);

    }

    public int getDurability() {
        return durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }
}
