package me.rapturr.gtm.entity;

import de.tr7zw.nbtapi.NBTEntity;
import me.rapturr.gtm.entity.entities.CarEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class GTMEntityManager {

    private static Map<String, GTMEntity> gtmEntities;

    public GTMEntityManager() {
        gtmEntities = new HashMap<>();

        putGTMEntity("CAR", new CarEntity("CAR", EntityType.MINECART));
    }

    public static void putGTMEntity(String entityID, GTMEntity gtmEntity) {
        getGtmEntities().put(entityID, gtmEntity);
    }

    public static Map<String, GTMEntity> getGtmEntities() {
        return gtmEntities;
    }

    public static boolean isGTMEntity(Entity entity) {
        if (entity == null) {
            return false;
        }

        NBTEntity nbtEntity = new NBTEntity(entity);

        String entityID = nbtEntity.getString("entity-id");
        return entityID != null;
    }

    public static GTMEntity getGTMEntity(String entityID) {
        if (getGtmEntities().get(entityID) == null) {
            return null;
        } else return getGtmEntities().get(entityID);
    }

    public static GTMEntity getGTMEntity(Entity entity) {
        if (!isGTMEntity(entity)) {
            return null;
        }
        NBTEntity nbtEntity = new NBTEntity(entity);
        String entityID = nbtEntity.getString("entity-id");

        if (entityID == null) {
            return null;
        }

        return getGTMEntity(entityID);
    }
}
