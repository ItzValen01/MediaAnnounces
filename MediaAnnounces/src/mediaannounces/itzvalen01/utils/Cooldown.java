package mediaannounces.itzvalen01.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import mediaannounces.itzvalen01.Main;

public class Cooldown {

    private Main plugin;

    public Cooldown(Main plugin) {
        this.plugin = plugin;
    }


    public String getCooldown(Player p) {
        FileConfiguration pd = plugin.getPlayerData();
        FileConfiguration config = plugin.getConfig();
        String pathtime = "Players."+p.getUniqueId()+".media_cooldown";
        if(pd.contains(pathtime)){
            String timecooldownString = pd.getString(pathtime);
            long timecooldown = Long.valueOf(timecooldownString);
            long millis = System.currentTimeMillis();
            long cooldown = config.getInt("Config.cooldown_time"); //En Segundos
            long cooldownmil = cooldown*1000;

            long espera = millis - timecooldown;
            long esperaDiv = espera/1000;
            long esperatotalseg = cooldown - esperaDiv;
            long esperatotalmin = esperatotalseg/60;
            long esperatotalhour = esperatotalmin/60;
            if(((timecooldown + cooldownmil) > millis) && (timecooldown != 0)){
                if(esperatotalseg > 59){
                    esperatotalseg = esperatotalseg - 60*esperatotalmin;
                }
                String time = "";
                if(esperatotalseg != 0){
                    time = esperatotalseg+"s";
                }

                if(esperatotalmin > 59){
                    esperatotalmin = esperatotalmin - 60*esperatotalhour;
                }
                if(esperatotalmin > 0){
                    time = esperatotalmin+"min"+" "+time;
                }

                if(esperatotalhour > 0){
                    time = esperatotalhour+ "h"+" " + time;
                }

                return time;
            }else{
                return "-1";
            }
        }else{
            return "-1";
        }
    }

}
