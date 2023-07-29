package me.rapturr.gtm.entity;

import org.bukkit.Location;

public enum GTMEntities {

    CAR,

    ;

    public void getGTMEntity(Location location) {

        GTMEntity gtmEntity = GTMEntityManager.getGTMEntity(toString());

        if (gtmEntity == null) {
            return;
        }

        gtmEntity.createEntity(location.getWorld(), location);
    }

}
