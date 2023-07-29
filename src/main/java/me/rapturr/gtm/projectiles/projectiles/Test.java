package me.rapturr.gtm.projectiles.projectiles;

import me.rapturr.gtm.projectiles.Projectile;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class Test extends Projectile {
    public Test(String projectileID) {
        super(projectileID);
    }

    @Override
    public void onProjectileSpawn(Item item, Player player) {
        item.setItemStack(new ItemStack(Material.EXP_BOTTLE));
    }

    @Override
    public void onProjectileHit(Snowball snowball, Player player, Entity entity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onProjectileRemove(Snowball snowball, ProjectileSource shooter) {

    }
}
