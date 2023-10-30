package studio.resonos.headsteal.api.command.paramter.impl;

import studio.resonos.headsteal.api.command.paramter.Processor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class IntegerProcessor extends Processor<Integer> {
    public Integer process(CommandSender sender, String supplied) {
        try {
            return Integer.parseInt(supplied);
        } catch(Exception ex) {
            sender.sendMessage(ChatColor.RED + "The value you entered '" + supplied + "' is an invalid integer.");
            return 0;
        }
    }
}
