package me.rapturr.gtm.car;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.util.Vector;

public class CarListeners implements Listener {

    @EventHandler
    public void onExitVehicle(VehicleExitEvent event) {
        LivingEntity entity = event.getExited();
        Vehicle vehicle = event.getVehicle();

        if (entity == null) {
            return;
        }
        if (vehicle == null) {
            return;
        }
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }
        if (vehicle.getType() != EntityType.MINECART) {
            return;
        }
        Minecart minecart = (Minecart) vehicle;
        Player player = (Player) entity;

        minecart.setDerailedVelocityMod(new Vector(0, -1, 0));
        minecart.setSlowWhenEmpty(true);
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        Entity entity = event.getEntered();
        Vehicle vehicle = event.getVehicle();

        if (entity == null) {
            return;
        }
        if (vehicle == null) {
            return;
        }
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }
        if (vehicle.getType() != EntityType.MINECART) {
            return;
        }

        Minecart minecart = (Minecart) vehicle;
        minecart.setDerailedVelocityMod(new Vector(5, 0, 5));


    }
}
