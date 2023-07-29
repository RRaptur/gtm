package me.rapturr.gtm.utilities.attributes;

public enum Attribute {

    MAX_HEALTH("maxHealth"),
    KNOCKBACK_RESISTANCE("knockbackResistance"),
    MOVEMENT_SPEED("movementSpeed"),
    ATTACK_DAMAGE("attackDamage"),
    ARMOR("armor"),
    ARMOR_TOUGHNESS("armorToughness"),

    ;

    private final String name;

    Attribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
