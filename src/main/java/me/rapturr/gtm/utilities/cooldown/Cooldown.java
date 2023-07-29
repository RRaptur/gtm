package me.rapturr.gtm.utilities.cooldown;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {

    protected Map<UUID, Long> cooldown;

    public Cooldown() {
        this.cooldown = new HashMap<>();
    }

    public void setCooldown(Player player, Long amount) {
        getCooldown().put(player.getUniqueId(), amount + getTime());
    }

    public boolean hasCooldownTimeLeft(Player player) {
        UUID uuid = player.getUniqueId();

        if (!getCooldown().containsKey(uuid)) {
            return false;
        }

        return getCooldown().get(uuid) >= getTime();
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public Map<UUID, Long> getCooldown() {
        return cooldown;
    }

    public String getCooldownMessage(Player player) {
        long cooldownLeft = (getCooldown().get(player.getUniqueId()) - getTime());

        String weaponName = player.getItemInHand().getItemMeta().getDisplayName();
        String amount = String.valueOf(cooldownLeft);

        String first = weaponName + ChatColor.RED + " is on cooldown for ";

        //cooldown >= 10s
        if (cooldownLeft >= 10000) {
            return first + amount.charAt(0) + amount.charAt(1) + "." + amount.charAt(2) + "s!";
        }

        //cooldown >= 1s
        if (cooldownLeft >= 1000) {
            return first + amount.charAt(0) + "." + amount.charAt(1) + "s!";
        }

        //cooldown >= 0.1s
        if (cooldownLeft >= 100) {
            return first + "0." + amount.charAt(0) + "s!";
        }

        else return "";
    }
}
