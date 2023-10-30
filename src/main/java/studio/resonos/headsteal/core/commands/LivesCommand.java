package studio.resonos.headsteal.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import studio.resonos.headsteal.api.command.Command;
import studio.resonos.headsteal.api.command.paramter.Param;
import studio.resonos.headsteal.core.managers.DataManager;
import studio.resonos.headsteal.core.managers.DeathManager;
import studio.resonos.headsteal.core.utils.CC;

public class LivesCommand {

    @Command(names = {"lives", "souls", "getlives", "getsouls"}, permission = "headsteal.getsouls")
    public void Command(CommandSender sender, @Param(name = "target") String target) {
        if(target != null) {
            sender.sendMessage(CC.translate("&a" + Bukkit.getOfflinePlayer(target).getName() + " &ahas " + DeathManager.getLives(Bukkit.getOfflinePlayer(target).getPlayer())+ " &aSouls left."));
        }
    }

}
