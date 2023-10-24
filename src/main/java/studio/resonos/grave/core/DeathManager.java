package studio.resonos.grave.core;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import studio.resonos.grave.Grave;

public class DeathManager {

    public static int getDeaths(Player p) {

        NamespacedKey deathKey = new NamespacedKey(Grave.getPlugin(Grave.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0);
        }
        int deaths = data.get(deathKey, PersistentDataType.INTEGER);
        return deaths;
    }

    public static void addDeaths(Player p) {

        NamespacedKey deathKey = new NamespacedKey(Grave.getPlugin(Grave.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0 );
        }
        data.set(deathKey, PersistentDataType.INTEGER, (getDeaths(p) + 1));
    }

    public static void setDeaths(Player p, Integer amount) {

        NamespacedKey deathKey = new NamespacedKey(Grave.getPlugin(Grave.class), "deaths");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!(data.has(deathKey, PersistentDataType.INTEGER))) {
            data.set(deathKey, PersistentDataType.INTEGER, 0 );
        }

        data.set(deathKey, PersistentDataType.INTEGER, amount);
    }
}
