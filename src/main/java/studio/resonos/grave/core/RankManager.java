package studio.resonos.grave.core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.resonos.grave.core.utils.CC;

public class RankManager {

    public static void addFallenAngel(Player pl) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        assert provider != null;
        LuckPerms lp = provider.getProvider();

        if (!(lp.getGroupManager().getLoadedGroups().contains("fallen"))) {
            Bukkit.getConsoleSender().sendMessage((CC.translate("&aNO LUCKPERMS GROUP 'FALLEN' FOUND, CREATING NOW")));
            lp.getGroupManager().createAndLoadGroup("fallen");
        }

        lp.getUserManager().getUser(pl.getPlayer().getUniqueId()).data().add(InheritanceNode.builder("fallen").value(true).build());
        lp.getUserManager().saveUser(lp.getUserManager().getUser(pl.getPlayer().getUniqueId()));
    }

    public static void removeFallenAngel(Player pl) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        assert provider != null;
        LuckPerms lp = provider.getProvider();

        if (!(lp.getGroupManager().getLoadedGroups().contains("fallen"))) {
            Bukkit.getConsoleSender().sendMessage((CC.translate("&aNO LUCKPERMS GROUP 'FALLEN' FOUND, CREATING NOW")));
            lp.getGroupManager().createAndLoadGroup("fallen");
        }

        lp.getUserManager().getUser(pl.getPlayer().getUniqueId()).data().remove(InheritanceNode.builder("fallen").value(true).build());
        lp.getUserManager().saveUser(lp.getUserManager().getUser(pl.getPlayer().getUniqueId()));
    }
}
