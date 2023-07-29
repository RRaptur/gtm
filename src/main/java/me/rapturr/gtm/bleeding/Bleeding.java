package me.rapturr.gtm.bleeding;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Bleeding {

    final long duration;
    final long intensity;

    long currentIntensity;

    /**
     * @param duration the duration of the bleeding effect in ticks
     * @param intensity the intensity of the bleeding
     */
    public Bleeding(long duration, long intensity) {
        this.duration = duration;
        this.intensity = intensity;

        this.currentIntensity = intensity;
    }

    public void bleed(LivingEntity livingEntity) {
        if (getDuration() < System.currentTimeMillis()) {
            return;
        }
        if (getIntensity() < System.currentTimeMillis()) {
            return;
        }

        setCurrentIntensity(getIntensity() + System.currentTimeMillis());
        livingEntity.setHealth(livingEntity.getHealth() - 1);

    }

    public void setCurrentIntensity(long currentIntensity) {
        this.currentIntensity = currentIntensity;
    }

    public long getCurrentIntensity() {
        return currentIntensity;
    }

    public long getIntensity() {
        return intensity;
    }

    public long getDuration() {
        return duration;
    }
}
