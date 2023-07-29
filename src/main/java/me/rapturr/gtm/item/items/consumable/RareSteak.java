package me.rapturr.gtm.item.items.consumable;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.listeners.ProjectileListeners;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RareSteak extends GTMItem {

    Cooldown cooldown;

    public RareSteak(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect);

        cooldown = new Cooldown();
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {

    }

    @Override
    public void onConsume(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {
        if (cooldown.hasCooldownTimeLeft(player)) {
            player.sendMessage(cooldown.getCooldownMessage(player));
            return;
        }

        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
        player.sendMessage(ChatColor.GREEN + "Perfect medium-rare!");

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30*20, 0, false, false));

        int foodLevel = player.getFoodLevel();
        player.setFoodLevel(foodLevel + 8);
        player.setSaturation(20);

        cooldown.setCooldown(player, 60*1000L);
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
}
