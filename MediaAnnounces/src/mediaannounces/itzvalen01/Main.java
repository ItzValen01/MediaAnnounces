package mediaannounces.itzvalen01;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import mediaannounces.itzvalen01.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import mediaannounces.itzvalen01.events.Chat;
import mediaannounces.itzvalen01.events.MenuListener;

public class Main extends JavaPlugin{
    public String rutaConfig;
    private FileConfiguration messages = null;
    private File messagesFile = null;
    private FileConfiguration menu = null;
    private File menuFile = null;
    private FileConfiguration playerdata = null;
    private File playerdataFile = null;

    PluginDescriptionFile desc = getDescription();

    public void onEnable() {
        // Evitar Errores
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5----------------------------------------"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "		&5MediaAnnounces"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dStatus: &a&lActivated"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dVersion: &5"+desc.getVersion()));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dAuthor: &bItzValen01"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5----------------------------------------"));
            registerCommands();
            registerEvents();
            registerConfig();
            registerMessages();
            registerMenu();
            registerPlayerData();

            // Register Variables Creadas
            new Variables(this).register();
        }else{
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&m------------------------------&4&lERROR&c&m------------------------------"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "		&5MediaAnnounces"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe plugin could not be started because the following plugin is missing:"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lPlaceholderAPI"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&m------------------------------&4&lERROR&c&m------------------------------"));
        }
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5----------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "		&5MediaAnnounces"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dStatus: &c&lDesactivated"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dVersion: &5"+desc.getVersion()));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&dAuthor: &bItzValen01"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5----------------------------------------"));
    }

    public void registerCommands() {
        this.getCommand("media").setExecutor(new Media(this));
        this.getCommand("mediareload").setExecutor(new Reload(this));
        this.getCommand("cooldownreset").setExecutor(new CooldownReset(this));
        this.getCommand("mediachat").setExecutor(new PrivateChat(this));
        this.getCommand("partnerchat").setExecutor(new PartnerChat(this));
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Chat(this), this);
        pm.registerEvents(new MenuListener(this), this);
    }

    // config.yml
    public void registerConfig() {
        File config = new File(this.getDataFolder(),"config.yml");
        rutaConfig = config.getPath();
        if(!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    // Messages.yml
    public FileConfiguration getMessages() {
        if(messages == null) {
            reloadMessages();
        }
        return messages;
    }

    public void reloadMessages() {
        if(messages == null) {
            messagesFile = new File(getDataFolder(),"messages.yml");

        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveMessages() {
        try {
            messages.save(messagesFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerMessages() {
        messagesFile = new File(this.getDataFolder(),"messages.yml");
        if(!messagesFile.exists()) {
            this.getMessages().options().copyDefaults(true);
            saveMessages();
        }
    }

    // menu.yml
    public FileConfiguration getMenu() {
        if(menu == null) {
            reloadMenu();
        }
        return menu;
    }

    public void reloadMenu() {
        if(menu == null) {
            menuFile = new File(getDataFolder(),"menus.yml");

        }
        menu = YamlConfiguration.loadConfiguration(menuFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("menus.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                menu.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveMenu() {
        try {
            menu.save(menuFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerMenu() {
        menuFile = new File(this.getDataFolder(),"menus.yml");
        if(!menuFile.exists()) {
            this.getMenu().options().copyDefaults(true);
            saveMenu();
        }
    }

    // playerdata.yml
    public FileConfiguration getPlayerData() {
        if(playerdata == null) {
            reloadPlayerData();
        }
        return playerdata;
    }

    public void reloadPlayerData() {
        if(playerdata == null) {
            playerdataFile = new File(getDataFolder(),"playerdata.yml");

        }
        playerdata = YamlConfiguration.loadConfiguration(playerdataFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("playerdata.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                playerdata.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData() {
        try {
            playerdata.save(playerdataFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerPlayerData() {
        playerdataFile = new File(this.getDataFolder(),"playerdata.yml");
        if(!playerdataFile.exists()) {
            this.getPlayerData().options().copyDefaults(true);
            savePlayerData();
        }
    }

}
