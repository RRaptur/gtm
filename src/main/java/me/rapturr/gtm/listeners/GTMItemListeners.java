package me.rapturr.gtm.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.rapturr.gtm.GTM;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.item.GTMItemManager;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import javax.swing.*;

public class GTMItemListeners implements Listener {

    @EventHandler
    private void playerInteract(PlayerInteractEvent event) {
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

        boolean hasOffHandSupport = gtmItem.hasOffHandSupport();
        PlayerInventory inventory = player.getInventory();

        if(interactedItemStack != inventory.getItemInMainHand() && hasOffHandSupport) {
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

    ProtocolManager pm = GTM.getProtocolManager();
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

                String handType = container.getModifier().read(3).toString();

                if (!handType.equalsIgnoreCase("MAIN_HAND")) {
                    return;
                }

                Integer entityID = container.getIntegers().read(0);

                if (entityID == null) {
                    return;
                }

                WorldServer world = ((CraftWorld) player.getWorld()).getHandle();

                net.minecraft.server.v1_12_R1.Entity nmsEntity = world.getEntity(entityID);

                if (nmsEntity == null) {
                    return;
                }

                Entity entity = nmsEntity.getBukkitEntity();

                PlayerInventory inventory = player.getInventory();

                ItemStack mainHand = inventory.getItemInMainHand();

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
}




