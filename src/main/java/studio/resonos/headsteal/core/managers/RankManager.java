package studio.resonos.headsteal.core.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.resonos.headsteal.ResonosHeadsteal;
import studio.resonos.headsteal.core.utils.CC;

import java.util.UUID;

public class RankManager {
    static RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    static LuckPerms luckPerms = provider.getProvider();

    /*public static void addFallenAngel(Player pl) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        assert provider != null;
        LuckPerms lp = provider.getProvider();

        if (!(lp.getGroupManager().getLoadedGroups().contains("fallen"))) {
            lp.getGroupManager().createAndLoadGroup("fallen");
        }

        lp.getUserManager().getUser(pl.getPlayer().getUniqueId()).data().add(InheritanceNode.builder("fallen").value(true).build());
        lp.getUserManager().saveUser(lp.getUserManager().getUser(pl.getPlayer().getUniqueId()));
        ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().save();
    }

    public static void removeFallenAngel(Player pl) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        assert provider != null;
        LuckPerms lp = provider.getProvider();

        if (!(lp.getGroupManager().getLoadedGroups().contains("fallen"))) {
            lp.getGroupManager().createAndLoadGroup("fallen");
        }

        lp.getUserManager().getUser(pl.getPlayer().getUniqueId()).data().remove(InheritanceNode.builder("fallen").value(true).build());
        lp.getUserManager().saveUser(lp.getUserManager().getUser(pl.getPlayer().getUniqueId()));
        ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().save();
    }*/

    public static void addFallenAngel(Player p) {
        UUID user = p.getUniqueId();
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().add(PrefixNode.builder("&c&lFallen ", 200).build());
            user1.data().add(PermissionNode.builder("resonos.fallen").build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
        ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().save();
    }


    public static void removeFallenAngel(Player p) {
        UUID user = p.getUniqueId();
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().remove(PrefixNode.builder("&c&lFallen ", 200).build());
            user1.data().remove(PermissionNode.builder("resonos.fallen").build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
        ResonosHeadsteal.getPlugin(ResonosHeadsteal.class).getPlayerconfig().save();
    }



    public static void setfirstlife(UUID user) {
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().remove(PrefixNode.builder("&7", 104).build());
            user1.data().remove(PrefixNode.builder("&c", 103).build());
            user1.data().remove(PrefixNode.builder("&e", 102).build());
           user1.data().add(PrefixNode.builder("&a", 101).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setsecondlife(UUID user) {
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().remove(PrefixNode.builder("&7", 104).build());
            user1.data().remove(PrefixNode.builder("&c", 103).build());
            user1.data().remove(PrefixNode.builder("&a", 101).build());
            user1.data().add(PrefixNode.builder("&e", 102).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setlastlife(UUID user) {
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().remove(PrefixNode.builder("&7", 104).build());
            user1.data().remove(PrefixNode.builder("&e", 102).build());
            user1.data().remove(PrefixNode.builder("&a", 101).build());
            user1.data().add(PrefixNode.builder("&c", 103).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setDead(UUID user) {
        luckPerms.getUserManager().modifyUser(user , user1 -> {
            user1.data().remove(PrefixNode.builder("&c", 103).build());
            user1.data().remove(PrefixNode.builder("&e", 102).build());
            user1.data().remove(PrefixNode.builder("&a", 101).build());
           user1.data().add(PrefixNode.builder("&7", 104).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }
    public static void clearPrefixes(UUID user) {
        luckPerms.getUserManager().modifyUser(user, user1 -> {
           user1.data().remove(PrefixNode.builder("&7", 104).build());
           user1.data().remove(PrefixNode.builder("&c", 103).build());
           user1.data().remove(PrefixNode.builder("&e", 102).build());
           user1.data().remove(PrefixNode.builder("&a", 101).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void deathAccordingPrefix(Player p) {
        UUID user = p.getUniqueId();
        if (DeathManager.getLives(p) == 3) {
            setfirstlife(user);
        }
        if (DeathManager.getLives(p) == 2) {
            setsecondlife(user);
        }
        if (DeathManager.getLives(p) == 1) {
            setlastlife(user);
        }
        if (DeathManager.getLives(p) == 0) {
            setDead(user);
        }
        if (DeathManager.getLives(p) < 0 || DeathManager.getLives(p) > 3) {
            DeathManager.setLives(p, 3);
            setfirstlife(user);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Something has gone wrong " + p.getName() + " &4has an invalid number of lives!"));
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Souls of player has been reset"));
        }
    }

    public static boolean isFallenAngel(Player player) {
        return  luckPerms.getUserManager().getUser(player.getName()).getCachedData().getPermissionData().checkPermission("resonos.fallen").asBoolean();
    }

}
