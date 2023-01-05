package me.rapturr.gtm.car;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.rapturr.gtm.GTM;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

    }

    private boolean isPassable(Block block) {
       if (block.isEmpty()) { //isEmpty only checks if the block is air //TODO add grass, etc.
           return true;
       } else return false;
    }

}
