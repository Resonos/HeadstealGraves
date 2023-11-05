package studio.resonos.headsteal.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import studio.resonos.headsteal.api.command.Command;
import studio.resonos.headsteal.api.command.paramter.Param;
import studio.resonos.headsteal.core.managers.DeathManager;
import studio.resonos.headsteal.core.utils.CC;

public class CoreCommand {

    @Command(names = {"lifesteal", "headsteal", "core", "resonos", "oshifuu", "shifuu", "graves"})
    public void Command(CommandSender sender) {
        sender.sendMessage(CC.translate("&x&3&4&6&0&f&b&m-&x&3&a&6&0&f&b&m-&x&4&0&6&0&f&b&m-&x&4&6&6&0&f&b&m-&x&4&c&6&0&f&b&m-&x&5&2&6&0&f&a&m-&x&5&7&5&f&f&a&m-&x&5&d&5&f&f&a&m-&x&6&3&5&f&f&a&m-&x&6&9&5&f&f&a&m-&x&6&f&5&f&f&a&m-&x&7&5&5&f&f&a&m-&x&7&b&5&f&f&a&m-&x&8&1&5&f&f&9&m-&x&8&7&5&f&f&9&m-&x&8&d&5&f&f&9&m-&x&9&3&5&f&f&9&m-&x&9&9&5&f&f&9&m-&x&9&e&5&e&f&9&m-&x&a&4&5&e&f&9&m-&x&a&a&5&e&f&9&m-&x&b&0&5&e&f&9&m-&x&b&6&5&e&f&8&m-&x&b&c&5&e&f&8&m-&x&c&2&5&e&f&8&m-&x&c&8&5&e&f&8&m-&x&c&e&5&e&f&8&m-&x&d&4&5&e&f&8&m-&x&d&a&5&e&f&8&m-&x&d&f&5&d&f&8&m-&x&e&5&5&d&f&7&m-&x&e&b&5&d&f&7&m-&x&f&1&5&d&f&7&m-&x&f&7&5&d&f&7&m-&x&f&d&5&d&f&7&m-"));
        sender.sendMessage(CC.translate("&x&5&c&2&c&f&b&lR&x&6&7&2&b&f&4&le&x&7&1&2&a&e&d&ls&x&7&c&2&9&e&6&lo&x&8&7&2&8&d&e&ln&x&9&2&2&7&d&7&lo&x&9&c&2&6&d&0&ls &x&a&7&2&5&c&9&lH&x&b&2&2&5&c&2&le&x&b&d&2&4&b&b&la&x&c&7&2&3&b&4&ld&x&d&2&2&2&a&d&ls&x&d&d&2&1&a&5&lt&x&e&8&2&0&9&e&le&x&f&2&1&f&9&7&la&x&f&d&1&e&9&0&ll"));
        sender.sendMessage(CC.translate(" "));
        sender.sendMessage(CC.translate("&x&5&c&2&c&f&b&oA&x&6&5&2&b&f&5&on &x&6&e&2&a&e&f&oA&x&7&8&2&a&e&9&od&x&8&1&2&9&e&3&ov&x&8&a&2&8&d&c&oa&x&9&3&2&7&d&6&on&x&9&c&2&6&d&0&oc&x&a&6&2&6&c&a&oe&x&a&f&2&5&c&4&od &x&b&8&2&4&b&e&oH&x&c&1&2&3&b&8&oe&x&c&a&2&2&b&2&oa&x&d&4&2&2&a&c&od&x&d&d&2&1&a&5&os&x&e&6&2&0&9&f&ot&x&e&f&1&f&9&9&oe&x&f&8&1&e&9&3&oa&x&f&9&1&d&9&1&ol &x&f&2&1&c&9&4&op&x&e&b&1&b&9&6&ol&x&e&3&1&a&9&8&ou&x&d&c&1&8&9&b&og&x&d&4&1&7&9&d&oi&x&c&d&1&6&a&0&on &x&c&6&1&5&a&2&ow&x&b&e&1&3&a&4&oi&x&b&7&1&2&a&7&ot&x&b&0&1&1&a&9&oh &x&a&8&1&0&a&c&og&x&a&1&0&e&a&e&or&x&9&9&0&d&b&0&oa&x&9&2&0&c&b&3&ov&x&8&b&0&b&b&5&oe&x&8&3&0&9&b&8&os&x&7&c&0&8&b&a&o."));
        sender.sendMessage(CC.translate("&x&4&b&1&e&f&fD&x&5&2&1&e&f&fe&x&5&8&1&f&f&fv&x&5&f&1&f&f&fe&x&6&6&1&f&f&fl&x&6&d&2&0&f&fo&x&7&3&2&0&f&fp&x&7&a&2&0&f&ee&x&8&1&2&1&f&ed &x&8&7&2&1&f&eb&x&8&e&2&1&f&ey &x&9&5&2&2&f&eR&x&9&c&2&2&f&ee&x&a&2&2&2&f&es&x&a&9&2&3&f&eo&x&b&0&2&3&f&en&x&b&6&2&3&f&eo&x&b&d&2&4&f&es &x&c&4&2&4&f&eS&x&c&a&2&4&f&dt&x&d&1&2&5&f&du&x&d&8&2&5&f&dd&x&d&f&2&5&f&di&x&e&5&2&6&f&do&x&e&c&2&6&f&ds"));
        sender.sendMessage(CC.translate(" "));
        sender.sendMessage(CC.translate("&7&oLicensed to: &fLivingLegendOP"));
        sender.sendMessage(CC.translate("&x&3&4&6&0&f&b&m-&x&3&a&6&0&f&b&m-&x&4&0&6&0&f&b&m-&x&4&6&6&0&f&b&m-&x&4&c&6&0&f&b&m-&x&5&2&6&0&f&a&m-&x&5&7&5&f&f&a&m-&x&5&d&5&f&f&a&m-&x&6&3&5&f&f&a&m-&x&6&9&5&f&f&a&m-&x&6&f&5&f&f&a&m-&x&7&5&5&f&f&a&m-&x&7&b&5&f&f&a&m-&x&8&1&5&f&f&9&m-&x&8&7&5&f&f&9&m-&x&8&d&5&f&f&9&m-&x&9&3&5&f&f&9&m-&x&9&9&5&f&f&9&m-&x&9&e&5&e&f&9&m-&x&a&4&5&e&f&9&m-&x&a&a&5&e&f&9&m-&x&b&0&5&e&f&9&m-&x&b&6&5&e&f&8&m-&x&b&c&5&e&f&8&m-&x&c&2&5&e&f&8&m-&x&c&8&5&e&f&8&m-&x&c&e&5&e&f&8&m-&x&d&4&5&e&f&8&m-&x&d&a&5&e&f&8&m-&x&d&f&5&d&f&8&m-&x&e&5&5&d&f&7&m-&x&e&b&5&d&f&7&m-&x&f&1&5&d&f&7&m-&x&f&7&5&d&f&7&m-&x&f&d&5&d&f&7&m-"));
    }

}
