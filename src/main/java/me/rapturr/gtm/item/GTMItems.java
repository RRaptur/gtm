package me.rapturr.gtm.item;

import org.bukkit.inventory.ItemStack;

public enum GTMItems {

    /**
     * TEST ITEMS
     */
    TEST_ITEM,
    TEST_ARMOR,

    /**
     *  MELEE WEAPONS
     */
    KNIFE,
    BASEBALL_BAT,
    MACHETE,
    FLESH_HOOK,
    KATANA,
    FIRE_AXE,

    /**
     *  BOWS
     */
    CROSSBOW,
    LONGBOW,
    BOOMERANG,

    /**
     * THROWABLES
     */
    GRENADE,
    MOLOTOV,
    MUD_BALL,
    ROTTEN_EGG,

    /**
     * CONSUMABLES
     */
    ROCKET,
    RARE_STEAK,
    BANDAGE,
    WHITE_POWDER,

    /**
     * CLASS SETS
     */

    /**
     * HAZMAT
     */
    HAZMAT_HELMET,
    HAZMAT_CHESTPLATE,
    HAZMAT_LEGGINGS,
    HAZMAT_BOOTS,
    /**
     * RIOT
     */
    RIOT_HELMET,
    RIOT_CHESTPLATE,
    RIOT_LEGGINGS,
    RIOT_BOOTS,

    GRAPPLING_HOOK,
    CAR_ITEM,
    GAS_MASK,
    ROCKET_BOOTS,

    ;

    public ItemStack getItem() {

        GTMItem gtmItem = GTMItemManager.getGTMItem(toString());

        if (gtmItem == null) {
            return null;
        }

        return gtmItem.createItemStack(toString());
    }
}
