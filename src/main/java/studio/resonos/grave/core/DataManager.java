package studio.resonos.grave.core;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import studio.resonos.grave.ResonosHeadsteal;

public class DataManager {

    // default value

    public static boolean hasProfile(Player p) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        return configuration.get(path) != null;
    }

    public static void createProfile(Player p ) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        configuration.createSection("players." + p.getName());
    }

    public static void setDead(Player p) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();

        p.setGameMode(GameMode.SPECTATOR);
        configuration.set(path + ".dead", Boolean.TRUE);
    }

    public static Boolean isDead(Player p) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();

        return configuration.getBoolean(path + ".dead");
    }


    public static void revive(OfflinePlayer p) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();

        Player player = (Player) p;
        ((Player) p).setGameMode(GameMode.SURVIVAL);
        configuration.set(path + ".dead", Boolean.FALSE);
    }

    public static void addKiller(OfflinePlayer p, OfflinePlayer k ) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        // add name of killer
        configuration.set(path + ".killer", k.getName());
    }


    public static void removeKiller(OfflinePlayer p, OfflinePlayer k) {
        FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
        String path = "players." + p.getName();
        // remove name of killer
        configuration.set(path + ".killer", null);
    }

}
