package me.rapturr.gtm.commands;

import me.rapturr.gtm.item.GTMItemManager;
import me.rapturr.gtm.item.GTMItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GTMCommand implements CommandExecutor {

    String commandLabel = "gtm";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender == null) {
            return true;
        }
        if (!label.equalsIgnoreCase(commandLabel)) {
            return true;
        }
        if (!sender.hasPermission("gtm.default")) {
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command!");
            return true;
        }

        Player player = (Player) sender;
        GTMItemManager.giveGTMItem(GTMItems.ZITRIQ, player);

        return true;
    }
}
