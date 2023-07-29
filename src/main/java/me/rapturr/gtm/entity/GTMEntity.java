package me.rapturr.gtm.entity;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class GTMEntity {

    private final String entityID;
    private final EntityType entityType;
    private Entity entity;

    /**
     * @param entityID entity id
     * @param entityType type of entity
     */

    public GTMEntity(String entityID, EntityType entityType) {
        this.entityID = entityID;
        this.entityType = entityType;
    }

    public void createEntity(World world, Location location) {
        assert  entityType.getEntityClass() != null;
        entity = world.spawn(location, entityType.getEntityClass());

        this.onSpawn(entity);
    }

    public String getString(Entity entity, String key)   {
        NBTEntity nbtEntity = new NBTEntity(entity);
        String string = nbtEntity.getString(key);

        if (string == null) {
            return null;
        } else return string;
    }

    public Integer getInteger(Entity entity, String key)   {
        NBTEntity nbtEntity = new NBTEntity(entity);
        Integer integer = nbtEntity.getInteger(key);

        if (integer == null) {
            return null;
        } else return integer;
    }

    public void setString(Entity entity, String key, String value) {
        NBTEntity nbtEntity = new NBTEntity(entity);
        nbtEntity.setString(key, value);
    }

    public void setInteger(Entity entity, String key, Integer value) {
        NBTEntity nbtEntity = new NBTEntity(entity);
        nbtEntity.setInteger(key, value);
    }

    public abstract void onSpawn(Entity entity);

    public abstract void onHit(Entity entity, Entity damager, EntityDamageByEntityEvent event);

    public abstract void onDamaged(Entity entity, Entity damager, EntityDamageByEntityEvent event);

    public abstract void onDeath(LivingEntity entity, EntityDeathEvent event);



}
