package me.rapturr.gtm.projectiles;

import me.rapturr.gtm.projectiles.projectiles.Test;

import java.util.HashMap;
import java.util.Map;

public class ProjectileManager {

    ProjectileManager instance;
   private static Map<String, Projectile> projectiles;

    public ProjectileManager() {
        instance = this;
        projectiles = new HashMap<>();

        putSnowball("TEST", new Test("TEST"));
    }

    public void putSnowball(String snowballID, Projectile projectile) {
        getProjectiles().put(snowballID, projectile);
    }

    public static Projectile getSnowball(Projectiles projectiles) {
       if (!getProjectiles().containsKey(projectiles.name())) {
           return null;
       }

       return getProjectiles().get(projectiles.name());
    }

    public ProjectileManager getInstance() {
        return instance;
    }

    public static Map<String, Projectile> getProjectiles() {
        return projectiles;
    }
}
