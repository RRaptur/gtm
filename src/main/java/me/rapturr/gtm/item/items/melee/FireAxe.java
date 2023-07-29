package me.rapturr.gtm.item.items.melee;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.bleeding.BleedingManager;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.attributes.Attribute;
import me.rapturr.gtm.utilities.attributes.AttributeModifier;
import me.rapturr.gtm.utilities.attributes.Operation;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;

public class FireAxe extends GTMItem {

    Cooldown cooldown;

    public FireAxe(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);

        cooldown = new Cooldown();
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        addAttribute(new AttributeModifier(Attribute.MOVEMENT_SPEED, -0.25d, Operation.ADD_SCALAR));
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
        if (cooldown.hasCooldownTimeLeft(player)) {
            return;
        }

        if (block.getType() != Material.HAY_BLOCK) {
            return;
        }

        block.setType(Material.AIR);
        block.getWorld().playSound(block.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 1);

        cooldown.setCooldown(player, 10*1000L);
        getDurability().removeDurability(1, itemStack, player);
    }

    @Override
    public void onEntityInteract(Player player, Entity entity, PacketEvent event) {

    }

    @Override
    public void onHitEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }
}
