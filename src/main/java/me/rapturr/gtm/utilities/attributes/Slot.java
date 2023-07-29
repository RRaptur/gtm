package me.rapturr.gtm.utilities.attributes;

public enum Slot {
    HAND("hand"),
    HEAD("head"),
    CHEST("chest"),
    LEGS("legs"),
    FEET("feet");

    private final String slot;

    Slot(String slot) {
        this.slot = slot;
    }

    public String getName() {
        return slot;
    }
}
