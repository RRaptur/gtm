package me.rapturr.gtm.item.items.armor;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.events.ArmorEquipEvent;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.ArmorType;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TestGTMArmor extends GTMItem  {
    public TestGTMArmor(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect);
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
        equipItem(player, itemStack, ArmorType.HELMET);
    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {

    }

    @Override
    public void onEntityInteract(Player player, Entity entity, PacketEvent event) {

    }

    @Override
    public void onHitEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        player.sendMessage("hit entity");
    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {
        player.sendMessage("hit by entity");

    }

    @Override
    public void onEquip(Player player, ItemStack itemStack, ArmorEquipEvent event) {

    }

    @Override
    public void onUnEquip(Player player, ItemStack itemStack, ArmorEquipEvent event) {

    }
}
