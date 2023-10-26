package studio.resonos.grave.core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.resonos.grave.Grave;
import studio.resonos.grave.core.utils.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        Grave.getPlugin(Grave.class).getPlayerconfig().save();
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
        Grave.getPlugin(Grave.class).getPlayerconfig().save();
    }

    private static List<User> getUsersInGroup(String groupName) {
        LuckPerms api = LuckPermsProvider.get();
        Group group = api.getGroupManager().getGroup(groupName);
        if (group == null) throw new IllegalArgumentException("Group " + groupName + " not found");
        UserManager userManager = api.getUserManager();
        List<User> users = new ArrayList<>();
        for (UUID uuid : userManager.searchAll(NodeMatcher.key(InheritanceNode.builder(group).build())).join().keySet()) {
            User user = userManager.isLoaded(uuid) ? userManager.getUser(uuid) : userManager.loadUser(uuid).join();
            if (user == null) throw new IllegalStateException("Could not load data of " + uuid);
            users.add(user);
        }
        return users;
    }

    public static boolean isFallenAngel(Player player) {
        return player.hasPermission("group.fallen");
    }
}
