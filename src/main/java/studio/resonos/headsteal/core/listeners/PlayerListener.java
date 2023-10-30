package studio.resonos.headsteal.core.listeners;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import studio.resonos.headsteal.ResonosHeadsteal;
import studio.resonos.headsteal.core.managers.*;
import studio.resonos.headsteal.core.utils.CC;
import studio.resonos.headsteal.core.utils.CooldownUtil;
import studio.resonos.headsteal.core.utils.ItemUtil;
import studio.resonos.headsteal.core.utils.SkullCreator;

import java.util.Objects;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        if (!(DataManager.hasProfile(e.getPlayer()))) {
            Bukkit.getConsoleSender().sendMessage("No Profile found for " + e.getPlayer().getName() + " ...creating now.");
            DataManager.createProfile(e.getPlayer());
        }
        RankManager.deathAccordingPrefix(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Bukkit.getWorld(e.getEntity().getWorld().getName()).playSound(e.getEntity().getLocation(), Sound.ENTITY_WITHER_HURT, 1.0f,1.0f);
        Bukkit.broadcastMessage(CC.translate("&c" + e.getEntity().getName() + " &chas died and lost a Soul!. They have: " + DeathManager.getLives(e.getEntity()) + " &cSouls left!"));
        DeathManager.removeLife(Objects.requireNonNull(e.getEntity().getPlayer()));
        e.getEntity().getPlayer().sendMessage(CC.translate("&cYou have lost your Soul!. You have " + DeathManager.getLives(e.getEntity().getPlayer())+ " &csouls left"));
        if (e.getEntity().getPlayer().getKiller() instanceof Player) {
            e.getEntity().getKiller().sendMessage(CC.translate("&aYou have killed &c" + e.getEntity().getPlayer().getName() + " &a and Gained a Soul!"));
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
        if (DeathManager.getLives(e.getEntity().getPlayer()) <= 0) {
            DataManager.setDead(e.getEntity().getPlayer());
            e.getEntity().getPlayer().setMaxHealth(20);
            ItemStack skull = SkullCreator.itemFromUuid(e.getEntity().getUniqueId());
            ItemUtil.drop(e.getEntity().getLocation(), skull);
            e.getEntity().getPlayer().sendMessage(CC.translate("&cYou have died 3 times. You can only respawn once a player has revived you!"));
            GraveManager.createGrave(e.getEntity().getPlayer(),
                    e.getEntity().getPlayer().getLocation().getBlockX(),
                    e.getEntity().getPlayer().getLocation().getBlockY(),
                    e.getEntity().getPlayer().getLocation().getBlockZ());
        }
        RankManager.deathAccordingPrefix(e.getEntity().getPlayer());
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
                event.getPlayer().sendMessage(CC.translate("&cYou have consumed &e" +  owner + "'s head. &cYou are now a fallen angel!"));
                event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);
                DataManager.addKiller(Bukkit.getOfflinePlayer(owner), event.getPlayer());
                RankManager.addFallenAngel(event.getPlayer());
            }
            if (event.getItem().isSimilar(RecipieManager.createSoulFragment().getResult())) {
                if (CooldownUtil.hasCooldown("soulfrag", event.getPlayer())) {
                    event.getPlayer().sendMessage(CC.translate("&cYou cannot use this item in cooldown mode!"));
                    event.setCancelled(true);
                    return;
                }
                // allow stacking
                if (event.getPlayer().getInventory().getItemInHand().getAmount() == 1) {
                    event.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
                } else {
                    event.getPlayer().getInventory().getItemInHand().setAmount(event.getPlayer().getInventory().getItemInHand().getAmount()-1);
                }
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3000, 0));
                CooldownUtil.setCooldown("soulfrag", event.getPlayer(), 600000);
                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(DataManager.isDead(e.getPlayer())) {
            // tp back but allow rotating head
            if(e.getTo().getBlockX() > e.getPlayer().getLastDeathLocation().getBlockX() || e.getTo().getBlockX() < e.getPlayer().getLastDeathLocation().getBlockX()
                    || e.getTo().getBlockZ() > e.getPlayer().getLastDeathLocation().getBlockZ() || e.getTo().getBlockZ() < e.getPlayer().getLastDeathLocation().getBlockZ()
                    || e.getTo().getBlockY() > e.getPlayer().getLastDeathLocation().getBlockY() || e.getTo().getBlockY() < e.getPlayer().getLastDeathLocation().getBlockY()) {
                e.getPlayer().teleport(e.getPlayer().getLastDeathLocation());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.SKELETON_SKULL && e.getItemInHand().isSimilar(RecipieManager.createSoulFragment().getResult())) {
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
                        e.getRightClicked().remove(); // remove clickable
                        // allow stacking
                        if (e.getPlayer().getInventory().getItemInHand().getAmount() == 1) {
                            e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
                        } else {
                            e.getPlayer().getInventory().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount()-1);
                        }
                        DataManager.revive(Bukkit.getOfflinePlayer(string));
                        Bukkit.broadcastMessage(CC.translate("&a " + string + " has been revived by " + e.getPlayer().getName() + " using a Revive Beacon!"));
                    }
                }
            }
        }
    }
}
