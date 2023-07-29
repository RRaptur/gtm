package me.rapturr.gtm.item.items.armor.hazmat;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.ArmorType;
import me.rapturr.gtm.utilities.CustomGameProfile;
import me.rapturr.gtm.utilities.attributes.Attribute;
import me.rapturr.gtm.utilities.attributes.AttributeModifier;
import me.rapturr.gtm.utilities.attributes.Operation;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HazmatHelmet extends GTMItem {
    public HazmatHelmet(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);
    }

    String[] skin = new String[]{"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjkxNmNhYzZmMTBhYWI3MmVmMzFhNmE2OWJlYzI1YmQ1MGM2YzcwNzM1NjgyZDI3YmVlZmRhY2Q1NDdlY2M1ZCJ9fX0=", "f916cac6f10aab72ef31a6a69bec25bd50c6c70735682d27beefdacd547ecc5d"};
    CustomGameProfile gameProfile = new CustomGameProfile(UUID.randomUUID(), "", skin);

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        itemStack.setItemMeta(gameProfile.getCustomSkullMeta(itemStack));

        setString(itemStack, "armor-set", "hazmat");
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

        if (!hasArmorSet(player, "hazmat")) {
            return;
        }

        event.setCancelled(true);
    }
}
