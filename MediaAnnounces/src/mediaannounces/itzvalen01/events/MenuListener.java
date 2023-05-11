package mediaannounces.itzvalen01.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import mediaannounces.itzvalen01.Main;
import mediaannounces.itzvalen01.utils.Cooldown;

public class MenuListener implements Listener{

    private Main plugin;

    public MenuListener(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> record_send = new ArrayList<Player>();
    public static ArrayList<Player> stream_send = new ArrayList<Player>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        FileConfiguration menu = plugin.getMenu();
        FileConfiguration messages = plugin.getMessages();

        if(e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', menu.getString("SelectMedia.name")))){
            if(e.getCurrentItem() == null || e.getSlotType() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }else {
                if(e.getCurrentItem().hasItemMeta()) {
                    e.setCancelled(true);
                    if(e.getSlot() == 11) {
                        e.setCancelled(true);
                        Cooldown c = new Cooldown(plugin);
                        if(!c.getCooldown(p).equals("-1")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.cooldown")
                                    .replaceAll("%cooldown_media%", c.getCooldown(p))));
                            p.closeInventory();

                        }else {
                            record_send.add(p);
                            p.closeInventory();
                            List<String> text = messages.getStringList("Media.recording_help");
                            for(int i=0;i<text.size();i++) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', text.get(i)));
                            }
                        }
                    }else if(e.getSlot() == 15) {
                        e.setCancelled(true);
                        Cooldown c = new Cooldown(plugin);
                        if(!c.getCooldown(p).equals("-1")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.cooldown")
                                    .replaceAll("%cooldown_media%", c.getCooldown(p))));
                            p.closeInventory();
                        }else {
                            stream_send.add(p);
                            p.closeInventory();
                            List<String> text = messages.getStringList("Media.stream_help");
                            for(int i=0;i<text.size();i++) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', text.get(i)));
                            }
                        }
                    }else if(e.getSlot() == 13){
                        e.setCancelled(true);
                        return;
                    }else {
                        return;
                    }
                }else {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

}
