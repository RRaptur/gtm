package me.rapturr.gtm;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.tr7zw.nbtinjector.NBTInjector;
import me.rapturr.gtm.bleeding.BleedingManager;
import me.rapturr.gtm.car.Car;
import me.rapturr.gtm.car.CarListeners;
import me.rapturr.gtm.commands.GTMCommand;
import me.rapturr.gtm.entity.GTMEntityManager;
import me.rapturr.gtm.item.GTMItemManager;
import me.rapturr.gtm.listeners.*;
import me.rapturr.gtm.projectiles.ProjectileManager;
import me.rapturr.gtm.utilities.cooldown.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class GTM extends JavaPlugin {

    private static GTM plugin;
    private static ProtocolManager protocolManager;

    public static GTM getInstance() {
        return plugin;
    }

    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @Override
    public void onLoad() {
        NBTInjector.inject();
    }

    @Override
    public void onEnable() {
        sendConsoleMessage(ChatColor.GREEN + "Loading GTM...");

        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();


        new BleedingManager();
        new GTMItemManager();
        new GTMEntityManager();
        new CooldownManager();
        new ProjectileManager();
        new Car();
        GTMItemListeners.activeListener();
        registerListeners();
        registerCommands();

        sendConsoleMessage(ChatColor.GREEN + "Loaded GTM!");


    }

    @Override
    public void onDisable() {
        sendConsoleMessage(ChatColor.RED+ "Disabling GTM...");

        plugin = null;
        protocolManager = null;


        sendConsoleMessage(ChatColor.RED+ "Disabled GTM!");

    }
    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    private void registerCommands() {
        getCommand("gtm").setExecutor(new GTMCommand());
    }


    private void registerListeners() {
        registerListener(new CarListeners());
        registerListener(new GTMItemListeners(this));
        registerListener(new InventoryMenuListeners());
        registerListener(new ArmorListener(Collections.singletonList("")));
        registerListener(new ProjectileListeners());
        registerListener(new EntityListeners());
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

}
