package me.rapturr.gtm.entity.entities;

import me.rapturr.gtm.entity.GTMEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

public class CarEntity extends GTMEntity {

    /**
     * @param entityID   entity id
     * @param entityType type of entity
     */
    public CarEntity(String entityID, EntityType entityType) {
        super(entityID, entityType);
    }

    @Override
    public void onSpawn(Entity entity) {
        if (!(entity instanceof Minecart)) {
            return;
        }

        Minecart minecart = (Minecart) entity;
        minecart.setDerailedVelocityMod(new Vector(0, -1, 0));
    }

    @Override
    public void onHit(Entity entity, Entity damager, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onDamaged(Entity entity, Entity damager, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onDeath(LivingEntity entity, EntityDeathEvent event) {

    }
}
