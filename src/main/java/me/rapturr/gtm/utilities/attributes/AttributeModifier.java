package me.rapturr.gtm.utilities.attributes;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class AttributeModifier {

    static AttributeModifier instance;

    Attribute attribute;
    double amount;
    Operation operation;
    Slot slot;

    /**
     * @param attribute the attribute to be modified
     * @param amount    the amount to be modified in double
     * @param operation the specified modify operation
     */
    public AttributeModifier(Attribute attribute, double amount, Operation operation) {
        instance = this;

        this.attribute = attribute;
        this.amount = amount;
        this.operation = operation;
    }

    public static AttributeModifier getInstance() {
        return instance;
    }

    public NBTTagCompound getAttributes() {
        NBTTagCompound attribute = new NBTTagCompound();

        attribute.set("AttributeName", new NBTTagString("generic." + getAttribute().getName()));
        attribute.set("Name", new NBTTagString("generic." + getAttribute().getName()));
        attribute.set("Amount", new NBTTagDouble(getAmount()));
        attribute.set("Operation", new NBTTagInt(getOperation().getInteger()));
        attribute.set("UUIDLeast", new NBTTagInt(894654));
        attribute.set("UUIDMost", new NBTTagInt(2872));

        return attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public double getAmount() {
        return amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public Slot getSlot() {
        return slot;
    }
}
