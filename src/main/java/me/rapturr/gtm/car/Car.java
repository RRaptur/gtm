package me.rapturr.gtm.car;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.rapturr.gtm.GTM;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class Car { //This car will not replace the regular minecart but will only work if it has certain data
    ProtocolManager pm = GTM.getProtocolManager();

    public void carEvent(GTM plugin) {

        pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketType type = event.getPacketType();
                PacketContainer container = event.getPacket();
                Player player = event.getPlayer();

                if (type != PacketType.Play.Client.STEER_VEHICLE) {
                    return;
                }

                if (player == null) {  //Check if vehicle is an actual "car" through the player object
                    return;
                }

                if (!player.isInsideVehicle()) {
                    return;
                }

                Entity entity = player.getVehicle();

                if (entity == null) {
                    return;
                }

                if (entity.getType() != EntityType.MINECART) {
                    return;
                }

                Minecart minecart = (Minecart) entity;

                //now we have the minecart we can check certain data we injected in the minecart to specify it as a car





                //Reading needed packet data, refer to wiki.vg about packet information

                String value = container.getModifier().read(1).toString();
                //String value1 = container.getModifier().read(0).toString();

                if (value == null) {
                    return;
                }

                Vector playerDirection = player.getEyeLocation().getDirection();

                double vectorX = playerDirection.getX();
                double vectorZ = playerDirection.getZ();

                Vector finalVector = new Vector(vectorX, 0, vectorZ);

                Location minecartLoc = minecart.getLocation();

                Block front = minecartLoc.add(finalVector).getBlock();
                Block under = minecartLoc.subtract(0, 0.4, 0).getBlock();

                boolean forward = value.equalsIgnoreCase("0.98");
                boolean backward = value.equalsIgnoreCase("-0.98");


                if (forward && !isPassable(under) && !isPassable(front)) {
                    minecart.setVelocity(finalVector.multiply(1D).add(new Vector(0, 0.4, 0)));


                }

                if (backward && !isPassable(under) && !isPassable(front)) {
                    minecart.setVelocity(finalVector.multiply(-1D).add(new Vector(0, 0.4, 0)));


                }
                if (forward && isPassable(front)) {
                    minecart.setVelocity(finalVector.multiply(1D).add(new Vector(0, -1, 0)));


                }

                if (backward && isPassable(front)) {
                    minecart.setVelocity(finalVector.multiply(-1D).add(new Vector(0, -1, 0)));


                }
                if (value.equalsIgnoreCase("0.0")) {
                    minecart.setVelocity(new Vector(0, 0, 0));

                }


            }
        });

        pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.LOW, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketType packetType = event.getPacketType();
                PacketContainer container = event.getPacket();
                Player player = event.getPlayer();

                if (packetType != PacketType.Play.Client.USE_ENTITY) {
                    return;
                }
                if (player == null) {
                    return;
                }

                String interactType = readPacket(container, 1);

                if (!interactType.equalsIgnoreCase("INTERACT")) {
                    return;
                }

                String handType = readPacket(container, 3);

                if (!handType.equalsIgnoreCase("MAIN_HAND")) {
                    return;
                }

                Integer entityID = container.getIntegers().read(0);

                if (entityID == null) {
                    return;
                }

                WorldServer world = ((CraftWorld) player.getWorld()).getHandle();

                net.minecraft.server.v1_12_R1.Entity nmsEntity = world.getEntity(entityID);

                if (nmsEntity == null) {
                    return;
                }

                Entity entity = nmsEntity.getBukkitEntity();

                if (entity.getType() != EntityType.MINECART) {
                    return;
                }

                Minecart minecart = (Minecart) entity;

                PlayerInventory inventory = player.getInventory();

                ItemStack itemStack = inventory.getItemInMainHand();

                if (itemStack.getType() != Material.COAL) {
                    return;
                }

                event.setCancelled(true);
                Vector vector = minecart.getDerailedVelocityMod();

                vector.add(new Vector(1, 0, 1));

                minecart.setDerailedVelocityMod(vector);

                itemStack.setAmount(itemStack.getAmount() -1);
                player.sendMessage("used fuel!");













            }
        });

    }

    private String readPacket(PacketContainer container, Integer index) {
        String value = container.getModifier().read(index).toString();

        if (value == null) {
            return null;
        } else return value;
    }

    private boolean isPassable(Block block) {
       if (block.isEmpty()) { //isEmpty only checks if the block is air //TODO add grass, etc.
           return true;
       } else return false;
    }

}
