package me.rapturr.gtm.item.materials;

import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public enum Materials {

    GUNPOWDER("&eExplosive Material", Collections.singletonList(ChatColor.DARK_GRAY + "Materials"), new ItemFactory(Material.SULPHUR), false),
    GLASS("&eGlass", Collections.singletonList(ChatColor.DARK_GRAY + "Materials"), new ItemFactory(Material.GLASS_BOTTLE), false),
    CLOTH("&eCloth", Collections.singletonList(ChatColor.DARK_GRAY + "Materials"), new ItemFactory(Material.PAPER), false),
    CHEMICALS("&eChemicals", Collections.singletonList(ChatColor.DARK_GRAY + "Materials"), new ItemFactory(Material.INK_SACK, (short) 12), true),
    GOLD_BAR("&eGold Bar", Collections.singletonList(ChatColor.DARK_GRAY + "Economy"), new ItemFactory(Material.GOLD_INGOT), true),
    GOLD_COIN("&eGold Coin", Collections.singletonList(ChatColor.DARK_GRAY + "Economy"),new ItemFactory(Material.DOUBLE_PLANT), false),
    ;

    private final String displayName;
    private final List<String> lore;
    private final ItemFactory itemFactory;
    private final boolean isShiny;

    Materials(String displayName, List<String> lore, ItemFactory itemFactory, boolean isShiny) {
        this.displayName = coloredString(displayName);
        this.lore = lore;
        this.itemFactory = itemFactory;
        this.isShiny = isShiny;
    }

    public ItemStack getItem() {
        ItemStack itemStack = getItemFactory().build();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(getDisplayName());
        itemMeta.setLore(getLore());
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS);

        if (isShiny()) {
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    public boolean isShiny() {
        return isShiny;
    }

    private String coloredString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
