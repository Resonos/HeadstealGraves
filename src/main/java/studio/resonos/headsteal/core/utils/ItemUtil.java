package studio.resonos.headsteal.core.utils;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public final class ItemUtil {

    public static void drop (Location location, ItemStack drop) {
        location.getWorld().dropItem(location,drop);
    }
}
