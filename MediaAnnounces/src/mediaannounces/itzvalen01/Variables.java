package mediaannounces.itzvalen01;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import mediaannounces.itzvalen01.commands.PartnerChat;
import mediaannounces.itzvalen01.commands.PrivateChat;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Variables extends PlaceholderExpansion {

    private Main plugin;

    public Variables(Main plugin) {
        this.plugin = plugin;
    }

    public boolean persist(){
        return true;
    }


    public boolean canRegister(){
        return true;
    }


    public String getAuthor(){
        return "ItzValen01";
    }


    public String getIdentifier(){
        return "mediaannounces";
    }



    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player p, String identifier){

        if(p == null){
            return "";
        }

        if(identifier.equals("chat")){
            FileConfiguration config = plugin.getConfig();
            if(!PrivateChat.pchat.contains(p) && !PartnerChat.chat_partner.contains(p)){
                return ChatColor.translateAlternateColorCodes('&', config.getString("Variables.Chat.Global"));
            }else if(PrivateChat.pchat.contains(p)){
                return ChatColor.translateAlternateColorCodes('&', config.getString("Variables.Chat.Media"));
            }else if(PartnerChat.chat_partner.contains(p)){
                return ChatColor.translateAlternateColorCodes('&', config.getString("Variables.Chat.Partner"));
            }
        }

        return null;
    }
}
