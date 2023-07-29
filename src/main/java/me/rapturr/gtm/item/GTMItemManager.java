package me.rapturr.gtm.item;

import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.item.items.armor.hazmat.HazmatBoots;
import me.rapturr.gtm.item.items.armor.hazmat.HazmatChestplate;
import me.rapturr.gtm.item.items.armor.hazmat.HazmatHelmet;
import me.rapturr.gtm.item.items.armor.hazmat.HazmatLeggings;
import me.rapturr.gtm.item.items.armor.riot.RiotBoots;
import me.rapturr.gtm.item.items.armor.riot.RiotChestplate;
import me.rapturr.gtm.item.items.armor.riot.RiotHelmet;
import me.rapturr.gtm.item.items.armor.riot.RiotLeggings;
import me.rapturr.gtm.item.items.consumable.Bandage;
import me.rapturr.gtm.item.items.consumable.CarItem;
import me.rapturr.gtm.item.items.consumable.RareSteak;
import me.rapturr.gtm.item.items.consumable.WhitePowder;
import me.rapturr.gtm.item.items.melee.*;
import me.rapturr.gtm.item.items.special.GasMask;
import me.rapturr.gtm.item.items.special.Rocket;
import me.rapturr.gtm.item.items.TestGTMItem;
import me.rapturr.gtm.item.items.armor.TestGTMArmor;
import me.rapturr.gtm.item.items.special.GrapplingHook;
import me.rapturr.gtm.item.items.special.RocketBoots;
import me.rapturr.gtm.item.items.throwable.Molotov;
import me.rapturr.gtm.utilities.itemfactory.ItemFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GTMItemManager {

    private static Map<String, GTMItem> gtmItems;

    public GTMItemManager() {
        gtmItems = new HashMap<>();

        putGTMItem("TEST_ITEM", new TestGTMItem("Test GTM Item", "TEST_ITEM", false, true, new ItemFactory(Material.BEDROCK), false));
        putGTMItem("KNIFE", new Knife("&cKnife", "KNIFE", false, false, new ItemFactory(Material.GHAST_TEAR), false));
        putGTMItem("ROCKET", new Rocket("&cRocket", "ROCKET", true, true, new ItemFactory(Material.FIREWORK), false));
        putGTMItem("FIRE_AXE", new FireAxe("&cFire Axe", "FIRE_AXE", false, true, new ItemFactory(Material.GOLD_AXE), false, 10));
        putGTMItem("TEST_ARMOR", new TestGTMArmor("Test GTM Armor", "TEST_ARMOR", false, true, new ItemFactory(Material.GLASS), false));
        putGTMItem("FLESH_HOOK", new FleshHook("&6Flesh Hook", "FLESH_HOOK", false, false, new ItemFactory(Material.IRON_HOE), false));
        putGTMItem("KATANA", new Katana("&eKatana", "KATANA", false, true, new ItemFactory(Material.IRON_SWORD), false));
        putGTMItem("RARE_STEAK", new RareSteak("&6Rare Steak", "RARE_STEAK", false, false, new ItemFactory(Material.MUTTON), false));
        putGTMItem("BANDAGE", new Bandage("&fBandage", "BANDAGE", true, false, new ItemFactory(Material.PAPER), false));
        putGTMItem("WHITE_POWDER", new WhitePowder("&eWhite Powder", "WHITE_POWDER", true, false, new ItemFactory(Material.SUGAR), false));
        putGTMItem("CAR_ITEM", new CarItem("&eCar", "CAR_ITEM", false, false, new ItemFactory(Material.MINECART), false));
        putGTMItem("BASEBALL_BAT", new BaseballBat("&2Baseball bat", "BASEBALL_BAT", false, false, new ItemFactory(Material.WOOD_SWORD), false, 50));

        //HAZMAT
        putGTMItem("HAZMAT_HELMET", new HazmatHelmet("&6Hazmat Helmet", "HAZMAT_HELMET", false, false, new ItemFactory(Material.SKULL_ITEM, (short) 3), false, 100));
        putGTMItem("HAZMAT_CHESTPLATE", new HazmatChestplate("&6Hazmat Chestplate", "HAZMAT_CHESTPLATE", false, false, new ItemFactory(Material.GOLD_CHESTPLATE), false, 100));
        putGTMItem("HAZMAT_LEGGINGS", new HazmatLeggings("&6Hazmat Leggings", "HAZMAT_LEGGINGS", false, false, new ItemFactory(Material.GOLD_LEGGINGS), false, 100));
        putGTMItem("HAZMAT_BOOTS", new HazmatBoots("&6Hazmat Boots", "HAZMAT_BOOTS", false, false, new ItemFactory(Material.LEATHER_BOOTS), false, 100));

        //RIOT
        putGTMItem("RIOT_HELMET", new RiotHelmet("&9Riot Helmet", "RIOT_HELMET", false, false, new ItemFactory(Material.SKULL_ITEM, (short) 3), false, 150));
        putGTMItem("RIOT_CHESTPLATE", new RiotChestplate("&9Riot Chestplate", "RIOT_CHESTPLATE", false, false, new ItemFactory(Material.LEATHER_CHESTPLATE), false, 150));
        putGTMItem("RIOT_LEGGINGS", new RiotLeggings("&9Riot Leggings", "RIOT_LEGGINGS", false, false, new ItemFactory(Material.IRON_LEGGINGS), false, 150));
        putGTMItem("RIOT_BOOTS", new RiotBoots("&9Riot Boots", "RIOT_BOOTS", false, false, new ItemFactory(Material.IRON_BOOTS), false, 150));

        //THROWABLES
        putGTMItem("MOLOTOV", new Molotov("&6Molotov", "MOLOTOV", false, false, new ItemFactory(Material.EXP_BOTTLE), false));

        //SPECIAL

        putGTMItem("GRAPPLING_HOOK", new GrapplingHook("&eGrappling Hook", "GRAPPLING_HOOK", false, false, new ItemFactory(Material.FISHING_ROD), false, 30));
        putGTMItem("GAS_MASK", new GasMask("&2Gas Mask", "GAS_MASK", false, false, new ItemFactory(Material.SKULL_ITEM, (short) 3), false, 50));
        putGTMItem("ROCKET_BOOTS", new RocketBoots("&4Rocket Boots", "ROCKET_BOOTS", false, false, new ItemFactory(Material.LEATHER_BOOTS), true, 100));
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
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getType() == Material.AIR) {
            return false;
        }
        if (itemStack.getAmount() == 0) {
            return false;
        }

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
}
