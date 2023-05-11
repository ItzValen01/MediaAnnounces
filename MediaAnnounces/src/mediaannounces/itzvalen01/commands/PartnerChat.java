package mediaannounces.itzvalen01.commands;

import mediaannounces.itzvalen01.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PartnerChat implements CommandExecutor {

    private Main plugin;

    public PartnerChat(Main plugin){
        this.plugin = plugin;
    }

    public static ArrayList<Player> chat_partner = new ArrayList<Player>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis command can only be executed by players!"));
            return true;
        }else{
            Player p = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            FileConfiguration messages = plugin.getMessages();
            if(config.getString("Config.PartnerChat.enable").equals("true")){
                if(p.hasPermission("mediaannounces.partner")){
                    if(!chat_partner.contains(p)){
                        chat_partner.add(p);
                        PrivateChat.pchat.remove(p);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("PartnerChat.activated")));
                        return true;
                    }else if(chat_partner.contains(p)){
                        chat_partner.remove(p);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("PartnerChat.desactivated")));
                        return true;
                    }
                }else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
                    return true;
                }
            }
        }
        return false;
    }
}
