package me.rapturr.gtm.listeners;

import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.item.GTMItemManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
}
