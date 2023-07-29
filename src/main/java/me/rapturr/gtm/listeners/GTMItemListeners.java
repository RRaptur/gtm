package me.rapturr.gtm.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.rapturr.gtm.GTM;
import me.rapturr.gtm.events.ArmorEquipEvent;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.item.GTMItemManager;
import me.rapturr.gtm.utilities.EquipmentSlot;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;


public class GTMItemListeners implements Listener {

    public GTMItemListeners(GTM plugin) {
        pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketType packetType = event.getPacketType();
                PacketContainer container = event.getPacket();
                Player player = event.getPlayer();

                if (packetType != PacketType.Play.Client.USE_ENTITY) {
                    return;
                }
                if (player == null) {
                    return;
                }

                String interactType = container.getModifier().read(1).toString();

                if (!interactType.equalsIgnoreCase("INTERACT")) {
                    return;
                }

                /*
                if version > 1.8.9
                String handType = container.getModifier().read(3).toString();

                if (!handType.equalsIgnoreCase("MAIN_HAND")) {
                    return;
                }
                 */

                Integer entityID = container.getIntegers().read(0);

                if (entityID == null) {
                    return;
                }

                World world = ((CraftWorld) player.getWorld()).getHandle();

                net.minecraft.server.v1_8_R3.Entity craftEntity = world.a(entityID);

                Entity entity = craftEntity.getBukkitEntity();

                if (entity == null) {
                    return;
                }

                PlayerInventory inventory = player.getInventory();

                ItemStack mainHand = inventory.getItemInHand();

                if (mainHand == null) {
                    return;
                }

                if (!GTMItemManager.isGTMItem(mainHand)) {
                    return;
                }

                GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

                if (gtmItem == null) {
                    return;
                }

                gtmItem.onEntityInteract(player, entity, event);
            }
        });
    }

    ProtocolManager pm = GTM.getProtocolManager();

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        ItemStack interactedItemStack = event.getItem();

        //Null checks and checking if the item is a gtm item.

        if (interactedItemStack == null) {
            return;
        }
        if (!GTMItemManager.isGTMItem(interactedItemStack)) {
            return;
        }
        GTMItem gtmItem = GTMItemManager.getGTMItem(interactedItemStack);

        if (gtmItem == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        if (!interactedItemStack.isSimilar(inventory.getItemInHand())) {
            return;
        }

        Action action = event.getAction();
        Block block = event.getClickedBlock();

        switch (action) {

            case LEFT_CLICK_AIR:
                gtmItem.onLeftClickAir(player, interactedItemStack, event);
                break;
            case LEFT_CLICK_BLOCK:
                gtmItem.onLeftClickBlock(player, interactedItemStack, block, event);
                break;
            case RIGHT_CLICK_AIR:
                gtmItem.onRightClickAir(player, interactedItemStack, event);
                break;
            case RIGHT_CLICK_BLOCK:
                gtmItem.onRightClickBlock(player, interactedItemStack, block, event);
                break;
        }
    }


    @EventHandler
    public void onEntityDamageEntityItem(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }
        if (!(damager instanceof Player)) {
            return;
        }

        Player player = (Player) damager;
        ItemStack mainHand = player.getItemInHand();

        if (mainHand == null) {
            return;
        }

        if (!GTMItemManager.isGTMItem(mainHand)) {
            return;
        }

        GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

        if (gtmItem == null) {
            return;
        }

        gtmItem.onHitEntity(player, hurt, mainHand, event);
    }

    @EventHandler
    public void onEntityDamageEntityArmor(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }
        if (!(damager instanceof Player)) {
            return;
        }

        Player player = (Player) damager;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());
            if (itemStack == null) {
                continue;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) {
                continue;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                continue;
            }

            gtmItem.onHitEntity(player, hurt, itemStack, event);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityArmor(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }
        if (!(hurt instanceof Player)) {
            return;
        }
        Player player = (Player) hurt;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());
            if (itemStack == null) {
                continue;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) {
                continue;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                continue;
            }

            gtmItem.onHitByEntity(player, damager, itemStack, event);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityItem(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }
        if (!(hurt instanceof Player)) {
            return;
        }

        Player player = (Player) hurt;
        ItemStack mainHand = player.getItemInHand();

        if (mainHand == null) {
            return;
        }

        if (!GTMItemManager.isGTMItem(mainHand)) {
            return;
        }

        GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

        if (gtmItem == null) {
            return;
        }

        gtmItem.onHitByEntity(player, damager, mainHand, event);
    }

        /*


    @EventHandler
    public void onEntityDamageEntity2(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }
        if (!(hurt instanceof Player)) {
            return;
        }

        Player player = (Player) hurt;
        ItemStack mainHand = player.getItemInHand();

        if (mainHand == null) {
            return;
        }

        if (!GTMItemManager.isGTMItem(mainHand)) {
            return;
        }

        GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

        if (gtmItem == null) {
            return;
        }

        gtmItem.onHitByEntity(player, damager, mainHand, event);
    }


    }

    @EventHandler
    public void onEntityDamageEntity4(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity hurt = event.getEntity();

        if (damager == null || hurt == null) {
            return;
        }

        if (damager instanceof Player) {

            Player player = (Player) damager;

            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());
                if (itemStack == null) {
                    continue;
                }
                if (!GTMItemManager.isGTMItem(itemStack)) {
                    continue;
                }
                GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

                if (gtmItem == null) {
                    continue;
                }

                gtmItem.onHitEntity(player, hurt, itemStack, event);
            }

            ItemStack mainHand = player.getInventory().getItemInHand();
            if (!GTMItemManager.isGTMItem(mainHand)) {
                return;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

            if (gtmItem == null) {
                return;
            }

            gtmItem.onHitEntity(player, hurt, mainHand, event);
        }

        if (hurt instanceof Player) {

            Player player = (Player) hurt;

            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());
                if (itemStack == null) {
                    continue;
                }
                if (!GTMItemManager.isGTMItem(itemStack)) {
                    continue;
                }
                GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

                if (gtmItem == null) {
                    continue;
                }

                gtmItem.onHitByEntity(player, damager, itemStack, event);
            }

            ItemStack mainHand = player.getInventory().getItemInHand();
            if (mainHand == null) {
                return;
            }
            if (!GTMItemManager.isGTMItem(mainHand)) {
                return;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

            if (gtmItem == null) {
                return;
            }

            gtmItem.onHitByEntity(player, damager, mainHand, event);
        }
     */

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack newArmorPiece = event.getNewArmorPiece();
        ItemStack oldArmorPiece = event.getOldArmorPiece();

        if (player == null) {
            return;
        }

        if (GTMItemManager.isGTMItem(newArmorPiece)) {
            GTMItem gtmItem = GTMItemManager.getGTMItem(newArmorPiece);
            if (gtmItem != null) {
                gtmItem.onEquip(player, newArmorPiece, event);
            }

        }

        if (GTMItemManager.isGTMItem(oldArmorPiece)) {
            GTMItem gtmItem = GTMItemManager.getGTMItem(oldArmorPiece);
            if (gtmItem != null) {
                gtmItem.onUnEquip(player, oldArmorPiece, event);
            }
        }
    }

    @EventHandler
    public void onRodUse(PlayerFishEvent event) {
        Player player = event.getPlayer();

        if (player == null) {
            return;
        }

        ItemStack mainHand = player.getItemInHand();

        if (!GTMItemManager.isGTMItem(mainHand)) {
            return;
        }

        GTMItem gtmItem = GTMItemManager.getGTMItem(mainHand);

        if (gtmItem == null) {
            return;
        }

        PlayerFishEvent.State state = event.getState();

        gtmItem.onRodCast(player, mainHand, state, event);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (player == null || itemStack == null) {
            return;
        }

        if (!GTMItemManager.isGTMItem(itemStack)) {
            return;
        }
        GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

        if (gtmItem == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        if (!itemStack.isSimilar(inventory.getItemInHand())) {
            return;
        }

        gtmItem.onConsume(player, itemStack, event);


    }

    @EventHandler
    public void removeArmorPoints(EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
    }

    @EventHandler
    public void onDamageItem(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        ItemStack itemStack = player.getItemInHand();

        if (!GTMItemManager.isGTMItem(itemStack)) {
            return;
        }

        GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

        if (gtmItem == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        if (!itemStack.isSimilar(inventory.getItemInHand())) {
            return;
        }

        gtmItem.onDamage(player, itemStack, event);
    }

    @EventHandler
    public void onDamageArmor(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());
            if (itemStack == null) {
                continue;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) {
                continue;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                continue;
            }

            gtmItem.onDamage(player, itemStack, event);
        }

    }

    @EventHandler
    public void onToggleFlightArmor(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.getInventory().getItem(equipmentSlot.getSlot());

            if (itemStack == null) {
                continue;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) {
                continue;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                continue;
            }

            gtmItem.onToggleFlight(player, itemStack, event);
        }
    }

    public static void activeListener() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    activeEffect(player);
                }
            }
        }.runTaskTimer(GTM.getInstance(), 0, 0);
    }

    private static void activeEffect(Player player) {
        PlayerInventory inventory = player.getInventory();
        for(EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = inventory.getItem(equipmentSlot.getSlot());

            if (itemStack == null) {
                continue;
            }
            if (!GTMItemManager.isGTMItem(itemStack)) {
                continue;
            }
            GTMItem gtmItem = GTMItemManager.getGTMItem(itemStack);

            if (gtmItem == null) {
                continue;
            }

            gtmItem.activeEffect(player, itemStack, equipmentSlot);
        }
    }

    /*
    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack newArmorPiece = event.getNewArmorPiece();
        if (player == null) {
            return;
        }
        if (newArmorPiece == null) {
            return;
        }
        if (!GTMItemManager.isGTMItem(newArmorPiece)) {
            return;
        }
        GTMItem gtmItem = GTMItemManager.getGTMItem(newArmorPiece);
        if (gtmItem == null) {
            return;
        }
        gtmItem.onEquip(player, newArmorPiece, event);
    }

    @EventHandler
    public void onArmorUnEquip(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack oldArmorPiece = event.getOldArmorPiece();
        if (player == null) {
            return;
        }
        if (oldArmorPiece == null) {
            return;
        }
        if (!GTMItemManager.isGTMItem(oldArmorPiece)) {
            return;
        }
        GTMItem gtmItem = GTMItemManager.getGTMItem(oldArmorPiece);
        if (gtmItem == null) {
            return;
        }
        gtmItem.onUnEquip(player, oldArmorPiece, event);
    }
     */
}




