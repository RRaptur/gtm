package me.rapturr.gtm.item;

import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.items.TestGTMItem;
import me.rapturr.gtm.item.items.ZitriqIsDik;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class GTMItemManager {

    private static Map<String, GTMItem> gtmItems;

    public GTMItemManager() {
        gtmItems = new HashMap<>();

        putGTMItem("TEST_ITEM", new TestGTMItem("Test GTM Item", "TEST_ITEM", false, true, Material.BEDROCK, false));
        putGTMItem("ZITRIQ", new ZitriqIsDik(ChatColor.RED + "Zitriq is Smelly", "ZITRIQ", false, true, Material.STICK, false));
    }

    public static void putGTMItem(String itemID, GTMItem gtmItem) {
        getGtmItems().put(itemID, gtmItem);
    }

    public static GTMItem getGTMItem(String itemID) {
        if (getGtmItems().get(itemID) == null) {
            return null;
        } else return getGtmItems().get(itemID);
    }

    private static Map<String, GTMItem> getGtmItems() {
        return gtmItems;
    }

    public static boolean isGTMItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        String itemID = nbtItem.getString("item-id");
        return itemID != null;
    }

    public static GTMItem getGTMItem(ItemStack itemStack) {
        if (!isGTMItem(itemStack)) {
            return null;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        String itemID = nbtItem.getString("item-id");

        if (itemID == null) {
            return null;
        }

        return getGTMItem(itemID);

    }

    public static void giveGTMItem(GTMItems gtmItems, Player player) {
        String itemID = gtmItems.toString();
        GTMItem gtmItem = getGTMItem(itemID);
        if (gtmItem == null) {
            return;
        }
        ItemStack itemStack = gtmItem.createItemStack(itemID);

        PlayerInventory inventory = player.getInventory();
        inventory.addItem(itemStack);
    }

}
