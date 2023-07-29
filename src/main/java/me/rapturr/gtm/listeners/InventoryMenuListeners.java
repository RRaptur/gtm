package me.rapturr.gtm.listeners;

import me.rapturr.gtm.inventory.InventoryMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class InventoryMenuListeners implements Listener {

    private boolean isInventoryMenu(Inventory inventory) {
        return (inventory.getHolder() instanceof InventoryMenu);
    }

    private InventoryMenu getInventoryMenu(Inventory inventory) {
        return (InventoryMenu) inventory.getHolder();
    }


    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (isInventoryMenu(inventory)) {
            getInventoryMenu(inventory).onInventoryOpen(player, inventory, event);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if (isInventoryMenu(inventory)) {
            getInventoryMenu(inventory).onInventoryClose(player, inventory, event);
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (isInventoryMenu(inventory) && event.getCurrentItem() != null) {
            getInventoryMenu(inventory).onInventoryClick(player, inventory, event);
        }
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (isInventoryMenu(inventory)) {
            getInventoryMenu(inventory).onInventoryDrag(player, inventory, event);
        }
        if (inventory != player.getInventory()) {
            return;
        }

    }

    @EventHandler
    private void onInventoryMove(InventoryMoveItemEvent event) {
        Inventory source = event.getSource();
        Inventory destination = event.getDestination();

        if (isInventoryMenu(source)) {
            getInventoryMenu(source).onInventoryItemSource(source, event);
        }

        if (isInventoryMenu(destination)) {
            getInventoryMenu(destination).onInventoryItemDestination(destination, event);
        }
    }
}
