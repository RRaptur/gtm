package me.rapturr.gtm.item.items.melee;

import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.projectiles.ProjectileManager;
import me.rapturr.gtm.projectiles.Projectiles;
import me.rapturr.gtm.utilities.attributes.Attribute;
import me.rapturr.gtm.utilities.attributes.AttributeModifier;
import me.rapturr.gtm.utilities.attributes.Operation;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Katana extends GTMItem {

    Map<UUID, UUID> targetMap;
    Map<UUID, Integer> comboMap;

    public Katana(String displayName, String itemID, boolean isStackable, boolean isShiny, ItemFactory itemFactory, boolean hasActiveEffect) {
        super(displayName, itemID, isStackable, isShiny, itemFactory, hasActiveEffect);

        targetMap = new HashMap<>();
        comboMap = new HashMap<>();

    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {
        addAttribute(new AttributeModifier(Attribute.ATTACK_DAMAGE, 5, Operation.ADD_NUMBER));
        addAttribute(new AttributeModifier(Attribute.MOVEMENT_SPEED, 0.2d, Operation.ADD_SCALAR));

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

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        UUID targetUUID = entity.getUniqueId();

        //Player has not combo-ed anyone yet
        if (!targetMap.containsKey(playerUUID)) {
            targetMap.put(playerUUID, targetUUID);
        }

        //Player has not started any combo yet
        if (!comboMap.containsKey(playerUUID)) {
            comboMap.put(playerUUID, 0);
        }

        //If the target does not match the last target
        if (targetMap.get(playerUUID) != targetUUID) {
            targetMap.put(playerUUID, targetUUID);
            comboMap.put(playerUUID, 0);
        }

        if (targetMap.get(playerUUID) == targetUUID) {
            comboMap.put(playerUUID, comboMap.get(playerUUID) + 1);
        }

        if (comboMap.get(playerUUID) == 5) {
            comboMap.remove(playerUUID);
            targetMap.remove(playerUUID);

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4*20, 2, false, false));
            player.getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        }
    }

    @Override
    public void onHitByEntity(Player player, Entity entity, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }
}
