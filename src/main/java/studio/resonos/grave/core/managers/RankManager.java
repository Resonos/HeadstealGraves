package studio.resonos.grave.core.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.resonos.grave.ResonosHeadsteal;

public class RankManager {

    public static void addFallenAngel(Player pl) {
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
    }

    public static boolean isFallenAngel(Player player) {
        return player.hasPermission("group.fallen");
    }
}
