package me.rapturr.gtm.utilities.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static CooldownManager instance;
    protected Map<UUID, Cooldown> cooldowns;

    public CooldownManager() {
        instance = this;
        cooldowns = new HashMap<>();
    }

    public static CooldownManager getInstance() {
        return instance;
    }

    public Map<UUID, Cooldown> getCooldowns() {
        return cooldowns;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }
}
