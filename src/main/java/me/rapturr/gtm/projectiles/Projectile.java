package me.rapturr.gtm.projectiles;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.UUID;

public abstract class Projectile {

    private final String projectileID;
    private Item item;

    public Projectile(String projectileID) {
        this.projectileID = projectileID;

    }

    public void spawnProjectile(Location location, Vector velocity, UUID shooterUUID) {

        item = location.getWorld().dropItem(location, new ItemStack(Material.STONE));
        item.setVelocity(velocity);

        item.setPickupDelay(Integer.MAX_VALUE);

        setString(item, "is-custom-projectile", "true");
        setString(item, "projectile-id", projectileID);
        setString(item, "shooter-uuid", shooterUUID.toString());


        onProjectileSpawn(item, getShooter(shooterUUID));

    }


    public abstract void onProjectileSpawn(Item item, Player player);

    public abstract void onProjectileHit(Snowball snowball, Player player , Entity entity, EntityDamageByEntityEvent event);

    public abstract void onProjectileRemove(Snowball snowball, ProjectileSource shooter);

    public String getProjectileID() {
        return projectileID;
    }

    public Item getItem() {
        return item;
    }

    public Player getShooter(UUID uuid) {
       return Bukkit.getPlayer(uuid);
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
}


