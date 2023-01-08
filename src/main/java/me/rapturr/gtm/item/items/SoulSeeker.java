package me.rapturr.gtm.item.items;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.rapturr.gtm.GTM;
import me.rapturr.gtm.item.GTMItem;
import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class SoulSeeker extends GTMItem {
    public SoulSeeker(String displayName, String itemID, boolean isStackable, boolean isShiny, Material material, boolean hasActiveEffect, boolean hasOffHandSupport) {
        super(displayName, itemID, isStackable, isShiny, material, hasActiveEffect, hasOffHandSupport);
    }

    @Override
    public void onItemStackCreate(NBTItem nbtItem, ItemStack itemStack) {

    }

    @Override
    public void onLeftClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onLeftClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event)  {
        event.setCancelled(true);
        blockBreakAnimation(6, new BlockPosition(block.getX(), block.getY(), block.getZ()), player);
    }

    @Override
    public void onRightClickAir(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void onRightClickBlock(Player player, ItemStack itemStack, Block block, PlayerInteractEvent event) {

    }

    @Override
    public void onEntityInteract(Player player, Entity entity, PacketEvent event) {
        event.setCancelled(true);
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setVelocity(new Vector(randomOffset(), 1, randomOffset()));

        World world = player.getWorld();

        Block under = livingEntity.getLocation().subtract(0, 1, 0).getBlock();
        blockBreakAnimation(6, new BlockPosition(under.getX(), under.getY(), under.getZ()), player);
        player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 4, 0.1f);
        player.spawnParticle(Particle.SMOKE_LARGE, entity.getLocation(), 50, 0, 0.5, 0.5, 0.5);

        new BukkitRunnable() {
            @Override
            public void run() {
                livingEntity.setHealth(0);

                world.playSound(livingEntity.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 3, 1);
                world.playSound(livingEntity.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
                world.spawnParticle(Particle.BLOCK_DUST, livingEntity.getLocation().add(0, 1.5, 0), 100, 0,0.2, 0.2, 0.2, new MaterialData(Material.REDSTONE_BLOCK));
                world.spawnParticle(Particle.BLOCK_DUST, player.getLocation().add(0, 1.5, 0), 100, 0, 0.1, 0.1, 0.1, new MaterialData(Material.SOUL_SAND));
                player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
            }
        }.runTaskLater(GTM.getInstance(), 10);
    }

    private double randomOffset() {
        Random random = new Random();
        double min = -1d;
        double max = 1d;
        return random.nextInt((int) ( max - min)) + min;

    }

    private void blockBreakAnimation(int i, net.minecraft.server.v1_12_R1.BlockPosition blockPosition, Player player) {
        try {
            PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getModifier().write(1, blockPosition);
            packetContainer.getModifier().write(2, i);
            GTM.getProtocolManager().sendServerPacket(player, packetContainer);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
