package me.rapturr.gtm.item.items;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TestGTMItem extends GTMItem {
    public TestGTMItem(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect);
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        Bukkit.broadcastMessage("ItemStack created!");
    }

    @Override
    public void onLeftClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        player.sendMessage("LEFT_CLICK_AIR");
    }

    @Override
    public void onLeftClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {
        player.sendMessage("LEFT_CLICK_BLOCK");

    }

    @Override
    public void onRightClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        player.sendMessage("RIGHT_CLICK_AIR");

    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {
        player.sendMessage("RIGHT_CLICK_BLOCK");

    }

    @Override
    public void onEntityInteract(Player player, Entity entity, PacketEvent event) {
        player.sendMessage("ENTITY_INTERACT");

    }

    @Override
    public void onHitEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        player.sendMessage("HIT_ENTITY");

    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        player.sendMessage("HIT_BY_ENTITY");

    }
}
