package studio.resonos.grave.core.managers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import studio.resonos.grave.ResonosHeadsteal;

public class DeathManager {

    public static int getDeaths(Player p) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0);
        }
        int deaths = data.get(deathKey, PersistentDataType.INTEGER);
        return deaths;
    }

    public static void addDeaths(Player p) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0 );
        }
        data.set(deathKey, PersistentDataType.INTEGER, (getDeaths(p) + 1));
    }

    public static void setDeaths(Player p, Integer amount) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0 );
        }

        data.set(deathKey, PersistentDataType.INTEGER, amount);
    }
}
