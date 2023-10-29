package studio.resonos.grave.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import studio.resonos.grave.ResonosHeadsteal;
import studio.resonos.grave.core.*;
import studio.resonos.grave.core.utils.CC;
import studio.resonos.grave.core.utils.ItemUtil;
import studio.resonos.grave.core.utils.SkullCreator;

import java.util.Objects;

public class PlayerListener implements Listener {

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
                FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
                for (String string: configuration.getConfigurationSection("players").getKeys(false)) {
                    String path = "players." + string + ".killer";
                    if (configuration.getString(path, ".killer").equals(e.getEntity().getPlayer().getName())) {
                        DataManager.removeKiller(Bukkit.getOfflinePlayer(string), e.getEntity().getPlayer());
                        DataManager.revive(Bukkit.getOfflinePlayer(string));
                    }

                }
            }
        }
        e.getEntity().getPlayer().setMaxHealth(e.getEntity().getPlayer().getMaxHealth() - 2);
        if (DeathManager.getDeaths(e.getEntity().getPlayer()) >= 3) {
            e.getEntity().getPlayer().setMaxHealth(20);
            DeathManager.setDeaths(e.getEntity().getPlayer(), 0);
            ItemStack skull = SkullCreator.itemFromUuid(e.getEntity().getUniqueId());
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
            if (event.getItem().getType() == Material.PLAYER_HEAD ) {
                event.getPlayer().getInventory().setItem(event.getHand(), new ItemStack(Material.AIR));
                event.getPlayer().setMaxHealth(event.getPlayer().getMaxHealth() + 4);
                event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
                ItemMeta meta = event.getItem().getItemMeta();
                String owner = ((SkullMeta) meta).getOwner();
                event.getPlayer().sendMessage(CC.translate("&cYou have consumed &e" +  owner + "'s head. &cYou are now a fallen angel!"));;
                DataManager.addKiller(Bukkit.getOfflinePlayer(owner), event.getPlayer());
                RankManager.addFallenAngel(event.getPlayer());
            }
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
            FileConfiguration configuration = ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().getConfiguration();
            if ((RecipieManager.createBeacon().getResult().isSimilar(e.getPlayer().getInventory().getItemInMainHand()))){
                for (String string: configuration.getConfigurationSection("players").getKeys(false)) {
                    Bukkit.broadcastMessage(string);
                    if (e.getRightClicked().getCustomName().equals(string)){
                        e.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                        DataManager.revive(Bukkit.getOfflinePlayer(string));
                        Bukkit.broadcastMessage(CC.translate("&a " + string + " has been revived by " + e.getPlayer().getName() + " using a Revive Beacon!"));
                    }
                }
            }
        }
    }
}
