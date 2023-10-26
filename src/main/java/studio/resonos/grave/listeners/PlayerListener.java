package studio.resonos.grave.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import studio.resonos.grave.core.DataManager;
import studio.resonos.grave.core.DeathManager;
import studio.resonos.grave.core.RankManager;
import studio.resonos.grave.core.utils.CC;
import studio.resonos.grave.Grave;
import studio.resonos.grave.core.GraveManager;
import studio.resonos.grave.core.utils.ItemUtil;
import studio.resonos.grave.core.utils.SkullCreator;

import java.util.Objects;

public class PlayerListener implements Listener {

    NamespacedKey headkey = new NamespacedKey(Grave.getPlugin(Grave.class), "phead");
    NamespacedKey killerkey = new NamespacedKey(Grave.getPlugin(Grave.class), "killer");

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        if (!(DataManager.hasProfile(e.getPlayer()))) {
            Bukkit.getConsoleSender().sendMessage("No Profile found for " + e.getPlayer().getName() + " ...creating now.");
            DataManager.createProfile(e.getPlayer());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getPlayer().getKiller() instanceof Player) {
            if (RankManager.isFallenAngel(e.getEntity().getPlayer())) {
                RankManager.removeFallenAngel(e.getEntity().getPlayer());
                for (String string: DataManager.getVictims(e.getEntity().getPlayer())) {
                    OfflinePlayer victim = Bukkit.getOfflinePlayer(string);
                    DataManager.removeVictim(e.getEntity().getPlayer(), victim);
                    Bukkit.broadcastMessage("Victim revived: " + victim.getName());
                }
            }
        }
        // remove 1 heart
        e.getEntity().getPlayer().setMaxHealth(e.getEntity().getPlayer().getMaxHealth() - 2);
        if (DeathManager.getDeaths(e.getEntity().getPlayer()) >= 3) {
            e.getEntity().getPlayer().setMaxHealth(20);
            DeathManager.setDeaths(e.getEntity().getPlayer(), 0);
            //ItemStack skull = SkullUtil.getPlayerHead(e.getEntity());
            ItemStack skull = SkullCreator.itemFromUuid(e.getEntity().getUniqueId());
            //skull.getItemMeta().setDisplayName(CC.translate("&eHead of " + e.getEntity().getPlayer().getName()));
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
            //if (event.getItem().getItemMeta().getDisplayName().contains("Head")) {
                if (event.getItem().getType() == Material.PLAYER_HEAD ) {
                    event.getPlayer().getInventory().setItem(event.getHand(), new ItemStack(Material.AIR));
                    event.getPlayer().setMaxHealth(event.getPlayer().getMaxHealth() + 4);
                    event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
                    ItemMeta meta = event.getItem().getItemMeta();
                   // meta.getPersistentDataContainer().set(new NamespacedKey(Grave.getPlugin(Grave.class), "head"), );
                    String owner = ((SkullMeta) meta).getOwner();
                    event.getPlayer().sendMessage(CC.translate("&cYou have consumed &e" +  owner + "'s head. &cYou are now a fallen angel!"));
                    //PersistentDataContainer data = event.getPlayer().getPersistentDataContainer();
                    //data.set(killerkey, PersistentDataType.BOOLEAN, Boolean.TRUE);
                    //data.set(new NamespacedKey(Grave.getPlugin(Grave.class), owner.getName()), PersistentDataType.STRING, "killer." + owner.getName());
                    DataManager.addVictim(event.getPlayer(), Bukkit.getOfflinePlayer(owner));
                    RankManager.addFallenAngel(event.getPlayer());
                }
            //}
        }
    }

}
