package me.rapturr.gtm.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryMenu implements InventoryHolder {

    public final String title;
    public final int size;
    Inventory inventory;


    public InventoryMenu(String title, int size) {
        this.title = title;
        this.size = size;
        inventory = Bukkit.createInventory(this, size, title);
    }


    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory(final HumanEntity entity) {
        entity.openInventory(inventory);
    }

    public void setItemStack(ItemStack item, int slot) {
        inventory.setItem(slot, item);
    }

    public abstract void onInventoryOpen(Player player, Inventory inventory, InventoryOpenEvent event);

    public abstract void onInventoryClose(Player player, Inventory inventory, InventoryCloseEvent event);

    public abstract void onInventoryDrag(Player player, Inventory inventory, InventoryDragEvent event);

    public abstract void onInventoryClick(Player player, Inventory inventory, InventoryClickEvent event);

    public abstract void onInventoryItemSource(Inventory inventory, InventoryMoveItemEvent event);

    public abstract void onInventoryItemDestination(Inventory inventory, InventoryMoveItemEvent event);
}
