package me.rapturr.gtm.item.items.special;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Rocket extends GTMItem {

    Cooldown cooldown;

    public Rocket(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect);

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

        if (!cooldown.hasCooldownTimeLeft(player)) {

            itemStack.setAmount(itemStack.getAmount() -1);

            Vector playerVector = player.getEyeLocation().getDirection();

            Vector finalVector = new Vector(playerVector.getX(), 0f, playerVector.getZ());
            finalVector.multiply(3f);
            finalVector.setY(1.3f);

            player.setVelocity(finalVector);

            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);

            cooldown.setCooldown(player, 5000L);

        } else player.sendMessage(cooldown.getCooldownMessage(player));
    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {
        event.setCancelled(true);
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
