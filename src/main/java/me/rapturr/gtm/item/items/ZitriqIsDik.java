package me.rapturr.gtm.item.items;

import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ZitriqIsDik extends GTMItem {
    public ZitriqIsDik(String displayName, String itemID, boolean isStackable, boolean isShiny, Material material, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, material, hasActiveEffect);
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {

    }

    @Override
    public void onLeftClickAir(Player player, ItemStack itemStack, Event event) {

    }

    @Override
    public void onLeftClickBlock(Player player, ItemStack itemStack, Block block, Event event) {

    }

    @Override
    public void onRightClickAir(Player player, ItemStack itemStack, Event event) {
        player.setVelocity(player.getEyeLocation().getDirection().add(new Vector(0, 0.5, 0)));
    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, Event event) {

    }
}
