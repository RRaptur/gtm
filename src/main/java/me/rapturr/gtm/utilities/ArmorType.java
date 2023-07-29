package me.rapturr.gtm.utilities;

import me.rapturr.gtm.item.GTMItemManager;
import me.rapturr.gtm.listeners.ArmorListener;
import org.bukkit.inventory.ItemStack;

public enum ArmorType{
    HELMET(39), CHESTPLATE(38), LEGGINGS(37), BOOTS(36);

    private final int slot;

    ArmorType(int slot){
        this.slot = slot;
    }

    /**
     * Attempts to match the ArmorType for the specified ItemStack.
     *
     * @param itemStack The ItemStack to parse the type of.
     * @return The parsed ArmorType, or null if not found.
     */
    public static ArmorType matchType(final ItemStack itemStack) {

        if(ArmorListener.isAirOrNull(itemStack)) {
            return null;
        }
        if (!GTMItemManager.isGTMItem(itemStack)) {

            String type = itemStack.getType().name();

            if(type.endsWith("_HELMET") || type.endsWith("_SKULL") || type.endsWith("_HEAD")) return HELMET;
            if(type.endsWith("_CHESTPLATE") || type.equals("ELYTRA")) return CHESTPLATE;
            if(type.endsWith("_LEGGINGS")) return LEGGINGS;
            if(type.endsWith("_BOOTS")) return BOOTS;
            else return null;
        }
        return null;
    }

    public int getSlot(){
        return slot;
    }

}