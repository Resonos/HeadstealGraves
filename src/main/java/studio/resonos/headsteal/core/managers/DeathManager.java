package studio.resonos.headsteal.core.managers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import studio.resonos.headsteal.ResonosHeadsteal;

public class DeathManager {

    public static int getLives(Player p) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 3);
        }
        return data.get(deathKey, PersistentDataType.INTEGER);
    }

    public static void removeLife(Player p) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 3);
        }
        data.set(deathKey, PersistentDataType.INTEGER, (getLives(p) - 1));
    }

    public static void setLives(Player p, Integer amount) {

        NamespacedKey deathKey = new NamespacedKey(ResonosHeadsteal.getPlugin(ResonosHeadsteal.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0);
        }

        data.set(deathKey, PersistentDataType.INTEGER, amount);
    }
}
