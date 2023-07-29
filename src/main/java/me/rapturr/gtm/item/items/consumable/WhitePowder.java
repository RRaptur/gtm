package me.rapturr.gtm.item.items.consumable;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.utilities.cooldown.Cooldown;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WhitePowder extends GTMItem {

    Cooldown cooldown;

    public WhitePowder(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
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
        useWhitePowder(player, itemStack);
    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {
        useWhitePowder(player, itemStack);

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

    private void useWhitePowder(Player player, ItemStack itemStack) {
        if (cooldown.hasCooldownTimeLeft(player)) {
            player.sendMessage(cooldown.getCooldownMessage(player));
            return;
        }

        itemStack.setAmount(itemStack.getAmount() -1);
        cooldown.setCooldown(player, 20*1000L);

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 2, false, false));
        player.getWorld().playSound(player.getLocation(), Sound.CAT_HISS, 1f, 2f);
    }
}
