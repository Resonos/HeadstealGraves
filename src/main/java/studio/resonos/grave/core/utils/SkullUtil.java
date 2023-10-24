package studio.resonos.grave.core.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import studio.resonos.grave.Grave;

public class SkullUtil {

    public static ItemStack getPlayerHead (OfflinePlayer player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
        ItemMeta meta = head.getItemMeta();
        ((SkullMeta)meta).setOwningPlayer(player);
        //head.getItemMeta().setDisplayName(CC.translate("&eHead of " + player.getName()));
        head.setItemMeta(meta);
        NamespacedKey headkey = new NamespacedKey(Grave.getPlugin(Grave.class), "phead");
        meta.getPersistentDataContainer().set(headkey, PersistentDataType.BOOLEAN, Boolean.TRUE);
        return head;
    }
}
