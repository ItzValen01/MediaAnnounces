package mediaannounces.itzvalen01.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mediaannounces.itzvalen01.Main;

public class Reload implements CommandExecutor{

    private Main plugin;

    public Reload(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration messages = plugin.getMessages();
        if(sender.hasPermission("mediaannounces.reloadconfig")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.reload_config")));
                plugin.reloadConfig();
                plugin.reloadMessages();
                plugin.reloadMenu();
                return true;
            }

            if(args.length >= 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.reload")));
                return true;
            }
        }else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messagesno_permissions")));
            return true;
        }
        return false;
    }

}
