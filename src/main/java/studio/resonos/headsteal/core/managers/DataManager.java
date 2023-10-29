package studio.resonos.headsteal.core.managers;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import studio.resonos.headsteal.ResonosHeadsteal;

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

        DeathManager.setLives(p, 0);
        RankManager.clearPrefixes(p.getUniqueId());
        RankManager.setDead(p.getUniqueId());
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

        RankManager.clearPrefixes(p.getUniqueId());
        DeathManager.setLives(p.getPlayer(), 3);
        RankManager.setfirstlife(p.getUniqueId());

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
