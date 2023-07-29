package me.rapturr.gtm.item.items.melee;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BaseballBat extends GTMItem {

    Cooldown cooldown;

    public BaseballBat(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);

        cooldown = new Cooldown();
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {

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
        if (cooldown.hasCooldownTimeLeft(player)) {
            player.sendMessage(cooldown.getCooldownMessage(player));
            event.setCancelled(true);
            return;
        }

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        Vector vector = player.getEyeLocation().getDirection();
        vector.multiply(2f);
        Vector knockback = new Vector(vector.getX(), 0.4, vector.getZ());

        livingEntity.setVelocity(knockback);
        player.getWorld().playSound(livingEntity.getLocation(), Sound.IRONGOLEM_HIT, 1, 1);

        getDurability().removeDurability(1, itemStack, player);
        cooldown.setCooldown(player, 5 * 1000L);
    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        if (cooldown.hasCooldownTimeLeft(player)) {
            player.sendMessage(cooldown.getCooldownMessage(player));
            return;
        }

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        if (!player.isBlocking()) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        Vector vector = player.getEyeLocation().getDirection();
        vector.multiply(1.6f);
        Vector knockback = new Vector(vector.getX(), 1.2, vector.getZ());

        livingEntity.setVelocity(knockback);
        player.getWorld().playSound(livingEntity.getLocation(), Sound.ANVIL_LAND, 1, 1);

        getDurability().removeDurability(1, itemStack, player);
        cooldown.setCooldown(player, 5 * 1000L);


    }
}
