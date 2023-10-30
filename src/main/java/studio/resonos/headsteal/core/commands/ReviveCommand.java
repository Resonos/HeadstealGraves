package studio.resonos.headsteal.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import studio.resonos.headsteal.api.command.Command;
import studio.resonos.headsteal.api.command.paramter.Param;
import studio.resonos.headsteal.core.managers.DataManager;
import studio.resonos.headsteal.core.utils.CC;

public class ReviveCommand {

    @Command(names = {"revive"}, permission = "headsteal.revive")
    public void reviveCommand(CommandSender sender, @Param(name = "target") String target) {
        if(target != null) {
            DataManager.revive(Bukkit.getOfflinePlayer(target));
            Bukkit.broadcastMessage(CC.translate("&e" + target + " &ahas been force-revived by &c" + sender.getName()));
        }
    }

}
