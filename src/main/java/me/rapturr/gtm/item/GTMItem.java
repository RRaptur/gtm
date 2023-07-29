package me.rapturr.gtm.item;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.events.ArmorEquipEvent;
import me.rapturr.gtm.listeners.ProjectileListeners;
import me.rapturr.gtm.utilities.ArmorType;
import me.rapturr.gtm.utilities.Durability;
import me.rapturr.gtm.utilities.EquipmentSlot;
import me.rapturr.gtm.utilities.attributes.AttributeModifier;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.Slot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class GTMItem {

    private final String displayName;
    private final String itemID; //item id format: test_item
    private final boolean isStackable;
    private final boolean isShiny;
    private final ItemFactory itemFactory;
    private final boolean hasActiveEffect; //Adding an active effect is very heavy and we shouldn't just add it to any item
    private final NBTTagList modifiers;
    private Durability durability;
    private List<String> lore;

    /**
     *
     * @param displayName the item name shown in-game
     * @param itemID the item identifier for item communication
     * @param isStackable if the item is stackable
     * @param isShiny if the item has item-glint
     * @param itemFactory the item displayed in-game
     * @param hasActiveEffect if the item has an active effect
     * @param durability items durability
     */

    public GTMItem(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.itemID = itemID;
        this.isStackable = isStackable;
        this.isShiny = isShiny;
        this.itemFactory = itemFactory;
        this.hasActiveEffect = hasActiveEffect;
        this.modifiers = new NBTTagList();
        this.durability = new Durability(durability);
        this.lore = new ArrayList<>();
    }

    /**
     *
     * @param displayName the item name shown in-game
     * @param itemID the item identifier for item communication
     * @param isStackable if the item is stackable
     * @param isShiny if the item has item-glint
     * @param itemFactory the item displayed in-game
     * @param hasActiveEffect if the item has an active effect
     */

    public GTMItem(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.itemID = itemID;
        this.isStackable = isStackable;
        this.isShiny = isShiny;
        this.itemFactory = itemFactory;
        this.hasActiveEffect = hasActiveEffect;
        this.modifiers = new NBTTagList();
        this.lore = new ArrayList<>();
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

    public void setItemID(NBTItem nbtItem) {
        nbtItem.setString("item-id", getItemID());
    }

    public ItemStack createItemStack(String itemID) {
        GTMItem gtmItem = GTMItemManager.getGTMItem(itemID);

        if (gtmItem == null) {
            return null;
        }

        ItemStack itemStack = itemFactory.build();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.spigot().setUnbreakable(true);
        itemMeta.setDisplayName(gtmItem.getDisplayName());
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

        if (isShiny()) {
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }

        itemStack.setItemMeta(itemMeta);

        if (durability != null) {
           durability.enforceDurability(itemStack);
        }

        //Now for the NBT part
        NBTItem nbtItem = new NBTItem(itemStack);

        gtmItem.enforceStackability(nbtItem);
        gtmItem.enforceActiveEffect(nbtItem);
        gtmItem.setItemID(nbtItem);

        //Event on ItemStack create
        gtmItem.onItemStackCreate(nbtItem, nbtItem.getItem());

        //Add Attributes
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(nbtItem.getItem());
        NBTTagCompound compound = nmsItemStack.getTag();
        if (compound == null) {
            compound = new NBTTagCompound();
            nmsItemStack.setTag(compound);
            compound = nmsItemStack.getTag();
        }
        compound.set("AttributeModifiers", getModifiers());
        nmsItemStack.setTag(compound);

        return CraftItemStack.asBukkitCopy(nmsItemStack);

    }

    public void addAttribute(AttributeModifier attributeModifier) {
        modifiers.add(attributeModifier.getAttributes());
    }

    public abstract void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack);

    public abstract void onLeftClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event);

    /**
     * DOES NOT WORK IN GAMEMODE ADVENTURE
     */
    public abstract void onLeftClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event);

    public abstract void onRightClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event);

    public abstract void onEntityInteract(Player player, Entity entity, PacketEvent event);

    public abstract void onHitEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event);

    public abstract void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event);

    public void onEquip(Player player, ItemStack itemStack, ArmorEquipEvent event) {

    }

    public void onUnEquip(Player player, ItemStack itemStack, ArmorEquipEvent event) {

    }

    public void onRodCast(Player player, ItemStack itemStack, PlayerFishEvent.State state, PlayerFishEvent event) {

    }

    public void onConsume(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    public void onDamage(Player player, ItemStack itemStack, EntityDamageEvent event) {

    }

    public void onToggleFlight(Player player, ItemStack itemStack, PlayerToggleFlightEvent event) {

    }

    public void activeEffect(Player player, ItemStack itemStack, EquipmentSlot equipmentSlot) {

    }

    public void equipItem(Player player, ItemStack itemStack, ArmorType armorType) {
        Inventory inventory = player.getInventory();
        //Checks if the equipmentSlot is empty
        if (inventory.getItem(armorType.getSlot()) != null) {
            return;
        }
        ItemStack helmet = itemStack.clone();
        inventory.setItem(armorType.getSlot(), helmet);
        player.getInventory().remove(itemStack);
        player.updateInventory();

        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.HOTBAR, armorType, null, itemStack);
        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
        if (armorEquipEvent.isCancelled()) {
            armorEquipEvent.setCancelled(true);
        }
    }
    public String getString(ItemStack itemStack, String key)   {
        NBTItem nbtItem = new NBTItem(itemStack);
        String string = nbtItem.getString(key);

        if (string == null) {
            return null;
        } else return string;
    }

    public Integer getInteger(ItemStack itemStack, String key)   {
        NBTItem nbtItem = new NBTItem(itemStack);
        Integer integer = nbtItem.getInteger(key);

        if (integer == null) {
            return null;
        } else return integer;
    }

    public void setString(ItemStack itemStack, String key, String value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(key, value);

        ItemMeta meta = nbtItem.getItem().getItemMeta();

        itemStack.setItemMeta(meta);
    }

    public void setInteger(ItemStack itemStack, String key, Integer value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(key, value);

        ItemMeta meta = nbtItem.getItem().getItemMeta();

        itemStack.setItemMeta(meta);
    }

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

    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    public boolean isHasActiveEffect() {
        return hasActiveEffect;
    }

    public NBTTagList getModifiers() {
        return modifiers;
    }

    public Durability getDurability() {
        return durability;
    }

    public List<String> getLore() {
        return lore;
    }

    public static boolean hasArmorSet(Player player, String armorSet) {

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());

            if (itemStack == null) { //Is not wearing a piece in this slot so doesn't have an armor-set equipped.
                return false;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) { //Checking if piece is gtm-item.
                continue;
            }

            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                return false;
            }

            String armor = gtmItem.getString(itemStack, "armor-set");

            if (armorSet == null) {
                return false;
            }

            if (!armorSet.equalsIgnoreCase(armor)) {
                return false;
            }
        }
        return true;
    }

}
