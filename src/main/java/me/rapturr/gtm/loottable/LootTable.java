package me.rapturr.gtm.loottable;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class LootTable {

    protected static Map<ItemStack, Integer> items;

    public LootTable() {
        items = new HashMap<>();
    }

    public void addEntry(ItemStack itemStack, int weight) {
        getItems().put(itemStack, weight);
    }

    public static Map<ItemStack, Integer> getItems() {
        return items;
    }

    private int getTotalWeight() {
        int totalWeight = 0;
        for (int i : getItems().values()) {
            totalWeight += i;
        }

        return totalWeight;
    }

    private int getTotalItemsAmount() {
        return getItems().size();
    }

    public static ItemStack rollItem() {
        


        return null;
    }
}
