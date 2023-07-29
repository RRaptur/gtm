package me.rapturr.gtm.item.items.special;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.item.materials.Materials;
import me.rapturr.gtm.utilities.Durability;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class GrapplingHook extends GTMItem {

    Cooldown cooldown;

    public GrapplingHook(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect, int durability) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect, durability);

        this.cooldown = new Cooldown();
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

    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onRodCast(Player player, ItemStack itemStack, PlayerFishEvent.State state, PlayerFishEvent event) {
        if (state == PlayerFishEvent.State.CAUGHT_FISH) {
            event.setExpToDrop(0);

            if (event.getCaught().getType() != EntityType.DROPPED_ITEM) {
                event.setCancelled(true);
                return;
            }

            Item item = (Item) event.getCaught();
            item.setItemStack(Materials.CHEMICALS.getItem());
            item.setCustomName(Materials.CHEMICALS.getDisplayName());
            item.setCustomNameVisible(true);

            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            player.sendMessage(ChatColor.GREEN + "I should't eat this...");

            getDurability().removeDurability(1, itemStack, player);
        }

        if (event.getState() == PlayerFishEvent.State.FISHING) {
            if (cooldown.hasCooldownTimeLeft(player)) {
                player.sendMessage(cooldown.getCooldownMessage(player));
                event.setCancelled(true);
                return;
            }
        }

        if (state == PlayerFishEvent.State.FAILED_ATTEMPT || state == PlayerFishEvent.State.IN_GROUND) {
            Location playerLocation = player.getLocation();
            Location hookLocation = event.getHook().getLocation();

            Vector playerVector = new Vector(playerLocation.getX(), 0, playerLocation.getZ());
            Vector hookVector = new Vector(hookLocation.getX(), 0, hookLocation.getZ());

            Vector finalVector = hookVector.subtract(playerVector);

            finalVector.multiply(0.2f);
            finalVector.setY(0.1f);

            getDurability().removeDurability(1, itemStack, player);
            cooldown.setCooldown(player, 10 * 1000L);
            player.setVelocity(finalVector);
        }

        if (state == PlayerFishEvent.State.CAUGHT_ENTITY) {
            Entity entity = event.getCaught();

            if(!(entity instanceof LivingEntity)) {
                return;
            }

            Location playerLocation = player.getLocation();
            Location entityLocation = entity.getLocation();

            Vector playerVector = new Vector(playerLocation.getX(), 0, playerLocation.getZ());
            Vector entityVector = new Vector(entityLocation.getX(), 0, entityLocation.getZ());

            Vector finalVector = playerVector.subtract(entityVector);

            finalVector.multiply(0.1f);
            finalVector.setY(0.1f);

            getDurability().removeDurability(1, itemStack, player);
            cooldown.setCooldown(player, 10 * 1000L);
            entity.setVelocity(finalVector);
        }
    }
}
