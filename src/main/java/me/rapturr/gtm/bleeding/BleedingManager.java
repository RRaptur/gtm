package me.rapturr.gtm.bleeding;

import me.rapturr.gtm.GTM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BleedingManager {

    private static BleedingManager instance;
    protected Map<UUID, Bleeding> bleeders;

    public BleedingManager() {
        instance = this;
        this.bleeders = new HashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                    if (!(entity instanceof LivingEntity)) {
                        continue;
                    }
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (!isBleeding(livingEntity)) {
                        continue;
                    }
                    getBleeding(livingEntity).bleed(livingEntity);
                }
            }
        }.runTaskTimer(GTM.getInstance(), 0, 0);
    }

    private Map<UUID, Bleeding> getBleeders() {
        return bleeders;
    }

    public static BleedingManager getInstance() {
        return instance;
    }

    public boolean isBleeding(LivingEntity livingEntity) {
        UUID uuid = livingEntity.getUniqueId();
        if (uuid == null) {
            return false;
        }
        if (!getBleeders().containsKey(uuid)) {
            return false;
        }
        Bleeding bleeding = getBleeders().get(uuid);
        if (bleeding.getDuration() > System.currentTimeMillis()) {
            removeBleeder(livingEntity);
        }
        return bleeding.getDuration() > System.currentTimeMillis();
    }

    public Bleeding getBleeding(LivingEntity livingEntity) {
        UUID uuid = livingEntity.getUniqueId();
        if (uuid == null) {
            return null;
        }
        if (!getBleeders().containsKey(uuid)) {
            return null;
        }
        return getBleeders().get(uuid);
    }

    public void putBleeder(LivingEntity livingEntity, long duration, long intensity) {
        UUID uuid = livingEntity.getUniqueId();
        if (uuid == null) {
            return;
        }
        long time = System.currentTimeMillis();
        getBleeders().put(uuid, new Bleeding(duration + time, intensity + time));
    }

    public void removeBleeder(LivingEntity livingEntity) {
        UUID uuid = livingEntity.getUniqueId();
        if (uuid == null) {
            return;
        }
        if (!getBleeders().containsKey(uuid)) {
            return;
        }
        getBleeders().remove(uuid);
    }
}
