package me.rapturr.gtm.utilities.itemfactory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {

    Material material;
    ItemStack itemStack;
    ItemMeta itemMeta;

    public ItemFactory(Material material) {
        this.material = material;
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemFactory(Material material, short durability) {
        this.material = material;
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();

        itemStack.setDurability(durability);
    }

    public ItemStack build() {
        ItemStack itemStack = getItemStack();
        itemStack.setItemMeta(getItemMeta());
        return itemStack;
    }


    public Material getMaterial() {
        return material;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }
}
