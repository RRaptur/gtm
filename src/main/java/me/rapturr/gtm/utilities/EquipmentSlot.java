package me.rapturr.gtm.utilities;

public enum EquipmentSlot {
    /**
     * Slot numbers are for <= 1.8.8
     */
    HEAD(39),
    CHEST(38),
    LEGS(37),
    FEET(36),

    ;

    EquipmentSlot(int slot) {
        this.slot = slot;
    }

    private final int slot;

    public int getSlot() {
        return slot;
    }

}
