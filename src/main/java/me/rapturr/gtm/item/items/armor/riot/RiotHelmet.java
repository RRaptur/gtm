package me.rapturr.gtm.item.items.armor.riot;

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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RiotHelmet extends GTMItem {
    public RiotHelmet(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);
    }

    String[] skin = new String[]{"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBkZWQ2MzIwMDNiMTkwMGJiMzYxYThjYzUyMDNhOGQxZTM0YTI2ZTYyMzZlYjM0OGVlNTc3NGY4YzdjYzBjNiJ9fX0=","40ded632003b1900bb361a8cc5203a8d1e34a26e6236eb348ee5774f8c7cc0c6"};
    CustomGameProfile gameProfile = new CustomGameProfile(UUID.randomUUID(), "", skin);



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

    }
}
