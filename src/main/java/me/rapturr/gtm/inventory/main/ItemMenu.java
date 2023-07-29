package me.rapturr.gtm.inventory.main;

import me.rapturr.gtm.inventory.InventoryMenu;
import me.rapturr.gtm.item.GTMItem;
import me.rapturr.gtm.item.GTMItemManager;
import me.rapturr.gtm.item.GTMItems;
import me.rapturr.gtm.item.materials.Materials;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class ItemMenu extends InventoryMenu {
    public ItemMenu(String title, int size) {
        super(title, size);
        for (Materials materials : Materials.values()) {
            if (materials == null) {
                continue;
            }

            getInventory().addItem(materials.getItem());
        }
        for (GTMItems gtmItems : GTMItems.values()) {
            if (GTMItemManager.getGTMItem(gtmItems.name()) == null) {
                continue;
            }

            GTMItem gtmItem = GTMItemManager.getGTMItem(gtmItems.name());

            if (gtmItem == null) {
                continue;
            }
            getInventory().addItem(gtmItem.createItemStack(gtmItems.name()));
        }
    }

    @Override
    public void onInventoryOpen(Player player, Inventory inventory, InventoryOpenEvent event) {
        player.playSound(player.getLocation(), Sound.HORSE_ARMOR, 1, 1);
    }

    @Override
    public void onInventoryClose(Player player, Inventory inventory, InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryDrag(Player player, Inventory inventory, InventoryDragEvent event) {

    }

    @Override
    public void onInventoryClick(Player player, Inventory inventory, InventoryClickEvent event) {

    }

    @Override
    public void onInventoryItemSource(Inventory inventory, InventoryMoveItemEvent event) {

    }

    @Override
    public void onInventoryItemDestination(Inventory inventory, InventoryMoveItemEvent event) {

    }
}
