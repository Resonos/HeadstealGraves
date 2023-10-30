package studio.resonos.headsteal.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import studio.resonos.headsteal.api.command.Command;
import studio.resonos.headsteal.api.command.paramter.Param;
import studio.resonos.headsteal.core.managers.DeathManager;
import studio.resonos.headsteal.core.utils.CC;

public class SetLivesCommand {

    @Command(names = {"setlives", "setsouls"}, permission = "headsteal.setsouls")
    public void Command(CommandSender sender, @Param(name = "target") String target, @Param(name = "lives")Integer lives) {
        if(target != null) {
            if (lives < 3) {
                sender.sendMessage(CC.translate("&cMax lives can only be 3"));
                return;
            }
            DeathManager.setLives(Bukkit.getOfflinePlayer(target).getPlayer(), lives);
            sender.sendMessage(CC.translate("&a" + Bukkit.getOfflinePlayer(target).getName() + " &aSouls have been set to: &a" + DeathManager.getLives(Bukkit.getOfflinePlayer(target).getPlayer())));
            //sender.sendMessage(CC.translate("&a" + Bukkit.getOfflinePlayer(target).getName() + " &aHas" + DeathManager.getLives(Bukkit.getOfflinePlayer(target).getPlayer())+ " &aSouls left."));
        }
    }

}
