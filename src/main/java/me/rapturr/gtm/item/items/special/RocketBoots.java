package me.rapturr.gtm.item.items.special;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.EquipmentSlot;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class RocketBoots extends GTMItem {
    public RocketBoots(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(Color.RED);
        itemStack.setItemMeta(meta);

        setString(itemStack, "double-jump", "true");
    }

    @Override
    public void onLeftClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onLeftClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {

    }

    @Override
    public void onRightClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {

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
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        getDurability().removeDurability(1, itemStack, player);
        event.setCancelled(true);
    }

    @Override
    public void activeEffect(Player player, ItemStack itemStack, EquipmentSlot equipmentSlot) {
        if (!player.isOnGround()) {
            return;
        }
        setString(itemStack, "double-jump", "true");
        player.setAllowFlight(true);
    }

    @Override
    public void onToggleFlight(Player player, ItemStack itemStack, PlayerToggleFlightEvent event) {
        String canDoubleJump = getString(itemStack, "double-jump");

        if (!canDoubleJump.equalsIgnoreCase("true")) {
            return;
        }
        event.setCancelled(true);
        player.setAllowFlight(false);
        setString(itemStack, "double-jump", "false");
        player.setVelocity(player.getEyeLocation().getDirection().multiply(1.2).add(new Vector(0, 0.3, 0)));
        getDurability().removeDurability(1, itemStack, player);
        player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1, 1);
        player.getWorld().playEffect(player.getLocation(), Effect.CLOUD, 10);
    }
}
