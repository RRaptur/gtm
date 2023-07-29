package me.rapturr.gtm.utilities.attributes;

public enum Operation {

    /**
     * ADD NUMBER WILL SHOW: +amount stat
     */
    ADD_NUMBER(0),
    /**
     * ADD SCALAR WILL SHOW: +amount% stat
     */
    ADD_SCALAR(1),
    /**
     * MULTIPY SCALAR 1 WILL SHOW: +amount% stat
     */
    MULTIPLY_SCALAR_1(2);

    private final int integer;

    Operation(Integer integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }
}
