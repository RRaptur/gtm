package me.rapturr.gtm;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.rapturr.gtm.car.Car;
import me.rapturr.gtm.car.CarListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
    public void onEnable() {
        sendConsoleMessage(ChatColor.GREEN + "Loading GTM...");

        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        new Car().carEvent(GTM.plugin);
        registerEvents();

        sendConsoleMessage(ChatColor.GREEN + "Loaded GTM!");


    }

    @Override
    public void onDisable() {
        sendConsoleMessage(ChatColor.RED+ "Disabling GTM...");

        plugin = null;
        protocolManager = null;


        sendConsoleMessage(ChatColor.RED+ "Disabled GTM!");

    }
    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    private void registerEvents() {
        registerEvent(new CarListeners());
    }

    private void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
