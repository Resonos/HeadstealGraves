package studio.resonos.grave.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import studio.resonos.grave.core.DeathManager;
import studio.resonos.grave.core.RankManager;
import studio.resonos.grave.core.utils.CC;
import studio.resonos.grave.Grave;
import studio.resonos.grave.core.GraveManager;
import studio.resonos.grave.core.utils.ItemUtil;
import studio.resonos.grave.core.utils.SkullUtil;

import java.util.Objects;

public class PlayerListener implements Listener {

    NamespacedKey headkey = new NamespacedKey(Grave.getPlugin(Grave.class), "phead");
    NamespacedKey killerkey = new NamespacedKey(Grave.getPlugin(Grave.class), "killer");

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getPlayer().getKiller() instanceof Player) {
            if (e.getEntity().getPlayer().getPersistentDataContainer().has(killerkey, PersistentDataType.BOOLEAN)){
                for (OfflinePlayer player: Bukkit.getOfflinePlayers()) {
                    if (e.getEntity().getPlayer().getPersistentDataContainer().getKeys().contains(player.getName())) {
                        Bukkit.broadcastMessage(CC.translate("&aFOUND VICTIM:" + player.getName()));
                        for (NamespacedKey key: e.getEntity().getPlayer().getPersistentDataContainer().getKeys()) {
                            e.getEntity().getPlayer().getPersistentDataContainer().remove(key);
                        }
                        // revive player lopgic
                    }
                }
            }
        }
        // remove 1 heart
        e.getEntity().getPlayer().setMaxHealth(e.getEntity().getPlayer().getMaxHealth() - 2);
        if (DeathManager.getDeaths(e.getEntity().getPlayer()) >= 3) {
            e.getEntity().getPlayer().setMaxHealth(20);
            DeathManager.setDeaths(e.getEntity().getPlayer(), 0);
            ItemStack skull = SkullUtil.getPlayerHead(e.getEntity());
            skull.getItemMeta().setDisplayName(CC.translate("&eHead of " + e.getEntity().getPlayer().getName()));
            ItemUtil.drop(e.getEntity().getLocation(), skull);
            e.getEntity().getPlayer().sendMessage(CC.translate("&cYou have died 3 times. You can only respawn once a player has revived you!" + DeathManager.getDeaths(e.getEntity().getPlayer()) ));
            GraveManager.createGrave(e.getEntity().getPlayer(),
                    e.getEntity().getPlayer().getLocation().getBlockX(),
                    e.getEntity().getPlayer().getLocation().getBlockY(),
                    e.getEntity().getPlayer().getLocation().getBlockZ());
        }
        DeathManager.addDeaths(Objects.requireNonNull(e.getEntity().getPlayer()));
        e.getEntity().getPlayer().sendMessage(CC.translate("&cYou have died " + DeathManager.getDeaths(e.getEntity().getPlayer()) ));
    }

    @EventHandler
    public void onInventoryClick(PlayerInteractEvent event) {

        if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem().getItemMeta().getDisplayName().contains("Head of")) {
                if (event.getItem().getType() == Material.PLAYER_HEAD ) {
                    event.getItem().setType(null);
                    event.getPlayer().setMaxHealth(event.getPlayer().getMaxHealth() + 4);
                    ItemMeta meta = event.getItem().getItemMeta();
                    OfflinePlayer owner = ((SkullMeta) meta).getOwningPlayer();
                    PersistentDataContainer data = event.getPlayer().getPersistentDataContainer();
                    data.set(killerkey, PersistentDataType.BOOLEAN, Boolean.TRUE);
                    data.set(new NamespacedKey(Grave.getPlugin(Grave.class), owner.getName()), PersistentDataType.STRING, "killer." + owner.getName());
                    RankManager.addFallenAngel((Player) event.getPlayer());
                }
            }
        }
    }

}
