package studio.resonos.grave.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import studio.resonos.grave.core.*;
import studio.resonos.grave.core.utils.CC;
import studio.resonos.grave.Grave;
import studio.resonos.grave.core.utils.ItemUtil;
import studio.resonos.grave.core.utils.SkullCreator;

import java.io.File;
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

                //Bukkit.getConsoleSender().sendMessage("starting victim search");
                FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
                for (String string: configuration.getConfigurationSection("players").getKeys(false)) {
                   // Bukkit.getConsoleSender().sendMessage(string + " is the key!");
                    String path = "players." + string + ".killer";
                    //Bukkit.getConsoleSender().sendMessage(path);

                   // Bukkit.getConsoleSender().sendMessage("1");
                    if (configuration.getString(path, ".killer").equals(e.getEntity().getPlayer().getName())) {
                        //Bukkit.getConsoleSender().sendMessage("2");
                        DataManager.removeKiller(Bukkit.getOfflinePlayer(string), e.getEntity().getPlayer());
                       // Bukkit.broadcastMessage(string + " has been revived");
                        DataManager.revive(Bukkit.getOfflinePlayer(string));
                    }

                }
               /* for (String string: DataManager.getVictims(e.getEntity().getPlayer())) {
                    Bukkit.getConsoleSender().sendMessage("started victim search");
                    OfflinePlayer victim = Bukkit.getOfflinePlayer(string);
                    Bukkit.getConsoleSender().sendMessage("victim found");
                    DataManager.removeKiller(e.getEntity().getPlayer(), victim);
                    Bukkit.getConsoleSender().sendMessage("removed victim");
                    Bukkit.broadcastMessage("Victim revived: " + victim.getName());
                }*/
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
            DataManager.setDead(e.getEntity().getPlayer());
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
                    DataManager.addKiller(Bukkit.getOfflinePlayer(owner), event.getPlayer());
                    RankManager.addFallenAngel(event.getPlayer());
                }
            //}
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(DataManager.isDead(e.getPlayer())) {
            // tp back but allow rotating head
            if(e.getTo().getBlockX() > e.getFrom().getBlockX() || e.getTo().getBlockX() < e.getFrom().getBlockX()
                    || e.getTo().getBlockZ() > e.getFrom().getBlockZ() || e.getTo().getBlockZ() < e.getFrom().getBlockZ()
                    || e.getTo().getBlockY() > e.getFrom().getBlockY() || e.getTo().getBlockY() < e.getFrom().getBlockY()) {
                e.getPlayer().teleport(e.getFrom());
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock() == RecipieManager.createBeacon().getResult()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGraveClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ArmorStand) {
            //Bukkit.broadcastMessage("1");
            Entity stand = e.getRightClicked();
            FileConfiguration configuration = Grave.getPlugin(Grave.class).getPlayerconfig().getConfiguration();
           // if (stand.getCustomName().contains("grave")) {
                //Bukkit.broadcastMessage("2");
                //im writing this on phone so i cant test & idfk which one will work
                if ( /*(e.getPlayer().getInventory().getItemInMainHand().isSimilar(RecipieManager.createBeacon().getResult())) ||*/
                        (RecipieManager.createBeacon().getResult().isSimilar(e.getPlayer().getInventory().getItemInMainHand()))){
                    //Bukkit.broadcastMessage("3");
                    for (String string: configuration.getConfigurationSection("players").getKeys(false)) {
                       Bukkit.broadcastMessage(string);
                       if (e.getRightClicked().getCustomName().equals(string)){
                           e.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                           DataManager.revive(Bukkit.getOfflinePlayer(string));
                           Bukkit.broadcastMessage(CC.translate("&a " + string + " has been revived by " + e.getPlayer().getName() + " using a Revive Beacon!"));
                       }
                    }
                }
           // }
        }
    }

}
