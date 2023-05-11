package mediaannounces.itzvalen01.menus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MediaMenus {

    public static void openMenu(Player p, FileConfiguration menu) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', menu.getString("SelectMedia.name")));

        ItemStack recording = new ItemStack(Material.valueOf(menu.getString("SelectMedia.Items.recording.material")),1,
                (short) menu.getInt("SelectMedia.Items.recording.value"));
        ItemMeta rMeta = recording.getItemMeta();
        rMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("SelectMedia.Items.recording.name")));
        recording.setItemMeta(rMeta);

        ItemStack stream = new ItemStack(Material.valueOf(menu.getString("SelectMedia.Items.stream.material")),1,
                (short) menu.getInt("SelectMedia.Items.stream.value"));
        ItemMeta sMeta = stream.getItemMeta();
        sMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("SelectMedia.Items.stream.name")));
        stream.setItemMeta(sMeta);

        ItemStack chat = new ItemStack(Material.valueOf(menu.getString("SelectMedia.Items.chat.material")),1,
                (short) menu.getInt("SelectMedia.Items.chat.value"));
        String cname = PlaceholderAPI.setPlaceholders(p, menu.getString("SelectMedia.Items.chat.name"));
        ItemMeta cMeta = chat.getItemMeta();
        cMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', cname));
        chat.setItemMeta(cMeta);

        inv.setItem(11, recording);
        inv.setItem(13, chat);
        inv.setItem(15, stream);

        p.openInventory(inv);
    }

}
