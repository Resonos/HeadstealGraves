package studio.resonos.headsteal.core.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.resonos.headsteal.ResonosHeadsteal;

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
        clearPrefixes(user);
        luckPerms.getUserManager().modifyUser(user, user1 -> {
           user1.data().add(PrefixNode.builder("&a", 101).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setsecondlife(UUID user) {
        clearPrefixes(user);
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().add(PrefixNode.builder("&e", 102).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setlastlife(UUID user) {
        clearPrefixes(user);
        luckPerms.getUserManager().modifyUser(user, user1 -> {
            user1.data().add(PrefixNode.builder("&c", 103).build());
        });
        luckPerms.getUserManager().saveUser(luckPerms.getUserManager().getUser(user));
    }

    public static void setDead(UUID user) {
        clearPrefixes(user);
        luckPerms.getUserManager().modifyUser(user , user1 -> {
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


    public static boolean isFallenAngel(Player player) {
        return  luckPerms.getUserManager().getUser(player.getName()).getCachedData().getPermissionData().checkPermission("resonos.fallen").asBoolean();
    }

}
