package mediaannounces.itzvalen01.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mediaannounces.itzvalen01.Main;
import mediaannounces.itzvalen01.menus.MediaMenus;

public class Media implements CommandExecutor{

    private Main plugin;

    public Media(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis command can only be executed by players!"));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            FileConfiguration menu = plugin.getMenu();
            if(p.hasPermission("mediaannounces.media")) {
                if(args.length == 0) {
                    MediaMenus.openMenu(p, menu);
                    return true;
                }

                if(args.length >= 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.media")));
                    return true;
                }
            }else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
                return true;
            }
        }
        return false;
    }

}
