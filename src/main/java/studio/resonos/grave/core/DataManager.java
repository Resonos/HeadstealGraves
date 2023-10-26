package studio.resonos.grave.core;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import studio.resonos.grave.Grave;

import java.util.List;

public class DataManager {

    private static List<String> def; // default value

    public static boolean hasProfile(Player p) {
        FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        return configuration.get(path) != null;
    }

    public static void createProfile(Player p ) {
        FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
        configuration.createSection("players." + p.getName());
        String path = "players." + p.getName();
        configuration.set(path + ".victims", def);
    }


    public static void addVictim(Player p, OfflinePlayer v ) {
        FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        // add name of victim
        configuration.getStringList(path + ".victims").add(configuration.getStringList(path + ".victims").size(), v.getName());//.add(v.getName());
    }

    public static List<String> getVictims(Player player) {
        FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
        String path = "players." + player.getName();
        return configuration.getStringList(path + ".victims");
    }

    public static void removeVictim(Player p, OfflinePlayer v) {
        FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        // remove name of victim
        configuration.getStringList(path + ".victims").remove(v.getName());
    }

}
