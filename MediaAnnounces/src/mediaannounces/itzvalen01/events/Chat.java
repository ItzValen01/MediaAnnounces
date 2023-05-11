package mediaannounces.itzvalen01.events;

import java.util.List;

import mediaannounces.itzvalen01.commands.PartnerChat;
import mediaannounces.itzvalen01.commands.PrivateChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import mediaannounces.itzvalen01.Main;
import mediaannounces.itzvalen01.utils.Cooldown;

public class Chat implements Listener{

    private Main plugin;

    public Chat(Main plugin) {
        this.plugin = plugin;
    }

    String yt_url = "https://www.youtube.com/";
    String tw_url_complete = "https://www.twitch.tv/";
    String tw_url = "twitch.tv/";


    @EventHandler
    public void Recording(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String url = e.getMessage();
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration pd = plugin.getPlayerData();
        String pathtime = "Players."+p.getUniqueId()+".media_cooldown";
        if(MenuListener.record_send.contains(p)) {
            e.setCancelled(true);
            if(url.contains(yt_url)) {
                Cooldown c = new Cooldown(plugin);
                if(c.getCooldown(p).equals("-1")) {
                    long milis = System.currentTimeMillis();
                    pd.set(pathtime, milis);
                    plugin.savePlayerData();

                    List<String> text = messages.getStringList("Media.recording_notify");
                    for(int i=0;i<text.size();i++) {
                        String notify = PlaceholderAPI.setPlaceholders(p, text.get(i));
                        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', notify
                                    .replaceAll("%player%", p.getName())
                                    .replaceAll("%recording_link%", url)));
                        }
                    }
                    MenuListener.record_send.remove(p);
                }
            }else if(url.contains("cancel") || url.equals("cancel") || url.contains("Cancel") || url.equals("Cancel")) {
                MenuListener.record_send.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Media.send_canceled")));
            }else {
                MenuListener.record_send.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Media.invalid_plataform")));
            }
        }
    }

    @EventHandler
    public void Stream(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String url = e.getMessage();
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration pd = plugin.getPlayerData();
        String pathtime = "Players."+p.getUniqueId()+".media_cooldown";
        if(MenuListener.stream_send.contains(p)) {
            e.setCancelled(true);
            if(url.contains(yt_url) || url.contains(tw_url_complete) || url.contains(tw_url)) {
                Cooldown c = new Cooldown(plugin);
                if(c.getCooldown(p).equals("-1")) {
                    long milis = System.currentTimeMillis();
                    pd.set(pathtime, milis);
                    plugin.savePlayerData();

                    List<String> text = messages.getStringList("Media.stream_notify");
                    for(int i=0;i<text.size();i++) {
                        String notify = PlaceholderAPI.setPlaceholders(p, text.get(i));
                        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', notify
                                    .replaceAll("%player%", p.getName())
                                    .replaceAll("%stream_link%", url)));
                        }
                    }
                    MenuListener.stream_send.remove(p);
                }
            }else if(url.contains("cancel") || url.equals("cancel") || url.contains("Cancel") || url.equals("Cancel")) {
                MenuListener.stream_send.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Media.send_canceled")));
            }else {
                MenuListener.stream_send.remove(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Media.invalid_plataform")));
            }
        }
    }

    @EventHandler
    public void chatMedia(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        FileConfiguration config = plugin.getConfig();
        String msg = e.getMessage();
        if(config.getString("Config.MediaChat.enable").equals("true")){
            if(PrivateChat.pchat.contains(p)){
                e.setCancelled(true);
                for(Player all : Bukkit.getServer().getOnlinePlayers()){
                    if(all.hasPermission("mediaannounces.chat")){
                        String format = PlaceholderAPI.setPlaceholders(p, config.getString("Config.MediaChat.format"));
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', format
                                .replaceAll("%player%", p.getName())
                                .replaceAll("%message%", msg)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void PartnerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        FileConfiguration config = plugin.getConfig();
        String msg = e.getMessage();
        if(config.getString("Config.PartnerChat.enable").equals("true")){
            if(PartnerChat.chat_partner.contains(p)){
                e.setCancelled(true);
                for(Player all : Bukkit.getServer().getOnlinePlayers()){
                    if(all.hasPermission("mediaannounces.partner")){
                        String format = PlaceholderAPI.setPlaceholders(p, config.getString("Config.PartnerChat.format"));
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', format
                                .replaceAll("%player%", p.getName())
                                .replaceAll("%message%", msg)));
                    }
                }
            }
        }
    }

}
