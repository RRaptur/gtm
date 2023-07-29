package me.rapturr.gtm.item.items.special;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.ArmorType;
import me.rapturr.gtm.utilities.CustomGameProfile;
import me.rapturr.gtm.utilities.attributes.Attribute;
import me.rapturr.gtm.utilities.attributes.AttributeModifier;
import me.rapturr.gtm.utilities.attributes.Operation;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GasMask extends GTMItem {

    String[] skin = new String[]{"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU0ODAzNTBlNDhkMzU4MWYzYTA2NDAyMDk5NWUwYjFlMTg2MGM3ZWZkOWQxNDg4YTYzYjc3OTNlOTY1YTZlZiJ9fX0=", "be480350e48d3581f3a064020995e0b1e1860c7efd9d1488a63b7793e965a6ef"};
    CustomGameProfile gameProfile = new CustomGameProfile(UUID.randomUUID(), "", skin);

    public GasMask(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);

    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        itemStack.setItemMeta(gameProfile.getCustomSkullMeta(itemStack));
    }

    @Override
    public void onLeftClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onLeftClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {

    }

    @Override
    public void onRightClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        equipItem(player, itemStack, ArmorType.HELMET);
    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {
        equipItem(player, itemStack, ArmorType.HELMET);
    }

    @Override
    public void onEntityInteract(Player player, Entity entity, PacketEvent event) {

    }

    @Override
    public void onHitEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        getDurability().removeDurability(1, itemStack, player);
    }

    @Override
    public void onDamage(Player player, ItemStack itemStack, EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.POISON) {
            return;
        }

        player.playSound(player.getLocation(), Sound.HORSE_BREATHE, 1, 1);
        event.setCancelled(true);
    }
}
