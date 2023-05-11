package mediaannounces.itzvalen01.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mediaannounces.itzvalen01.Main;

public class CooldownReset implements CommandExecutor{

    private Main plugin;

    public CooldownReset(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration pd = plugin.getPlayerData();
        if(sender.hasPermission("mediaannounces.cooldownreset")) {
            if(args.length == 1) {
                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                if(pd.contains("Players."+t.getUniqueId()+".media_cooldown")) {
                    pd.set("Players."+t.getUniqueId()+".media_cooldown", null);
                    plugin.savePlayerData();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CooldownReset.reseted")
                            .replaceAll("%player%", t.getName())));
                    return true;
                }else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CooldownReset.no_cooldown")
                            .replaceAll("%player%", t.getName())));
                    return true;
                }
            }

            if(args.length <=0 || args.length >= 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.cooldownreset")));
                return true;
            }
        }else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
            return true;
        }
        return false;
    }

}
