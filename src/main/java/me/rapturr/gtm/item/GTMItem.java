package me.rapturr.gtm.item;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.plugin.NBTAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.UUID;

public abstract class GTMItem {

    private final String displayName;
    private final String itemID; //item id format: test_item
    private final boolean isStackable;
    private final boolean isShiny;
    private final Material material;
    private final boolean hasActiveEffect; //Adding an active effect is very heavy and we shouldn't just add it to any item
    private final boolean hasOffHandSupport; //TODO ADD OFFHAND SUPPORT!

    public GTMItem(String displayName, String itemID, boolean isStackable, boolean isShiny, Material material, boolean hasActiveEffect) {
        this.displayName = displayName;
        this.itemID = itemID;
        this.isStackable = isStackable;
        this.isShiny = isShiny;
        this.material = material;
        this.hasActiveEffect = hasActiveEffect;
        this.hasOffHandSupport = false;
    }

    public void enforceStackability(NBTItem nbtItem) {
        if (!isStackable()) {
            nbtItem.setString("is-stackable", UUID.randomUUID().toString());
        }
    }

    public void enforceActiveEffect(NBTItem nbtItem) {
        if (isHasActiveEffect()) {
            nbtItem.setString("active-effect", "true");
        }
    }

    public void enforceOffHandSupport(NBTItem nbtItem) {
        if (hasOffHandSupport()) {
            nbtItem.setString("offhand-support", "true");
        }
    }

    public void setItemID(NBTItem nbtItem) {
        nbtItem.setString("item-id", getItemID());
    }

    public ItemStack createItemStack(String itemID) {
        GTMItem gtmItem = GTMItemManager.getGTMItem(itemID);

        if (gtmItem == null) {
            return null;
        }

        ItemStack itemStack = new ItemStack(gtmItem.getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(gtmItem.getDisplayName());
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        if (isShiny()) {
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }

        itemStack.setItemMeta(itemMeta);

        //Now for the NBT Part
        NBTItem nbtItem = new NBTItem(itemStack);

       gtmItem.enforceStackability(nbtItem);
       gtmItem.enforceActiveEffect(nbtItem);
       gtmItem.enforceOffHandSupport(nbtItem);
       gtmItem.setItemID(nbtItem);

       //Event on ItemStack create
        gtmItem.onItemStackCreate(nbtItem, nbtItem.getItem());

       return nbtItem.getItem();

    }

    public abstract void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack);

    public abstract void onLeftClickAir(Player player, ItemStack itemStack, Event event);

    public abstract void onLeftClickBlock(Player player, ItemStack itemStack, Block block, Event event);

    public abstract void onRightClickAir(Player player, ItemStack itemStack, Event event);

    public abstract void onRightClickBlock(Player player, ItemStack itemStack, Block block, Event event);


    //Getters:

    public String getDisplayName() {
        return displayName;
    }

    public String getItemID() {
        return itemID;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public boolean isShiny() {
        return isShiny;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isHasActiveEffect() {
        return hasActiveEffect;
    }

    public boolean hasOffHandSupport() {
        return hasOffHandSupport;
    }
}
