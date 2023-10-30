package studio.resonos.headsteal;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import studio.resonos.headsteal.api.command.CommandHandler;
import studio.resonos.headsteal.core.listeners.PlayerListener;
import studio.resonos.headsteal.core.managers.RankManager;
import studio.resonos.headsteal.core.managers.RecipieManager;
import studio.resonos.headsteal.core.utils.BasicConfigurationFile;
import studio.resonos.headsteal.core.utils.CC;

public final class ResonosHeadsteal extends JavaPlugin {
    @Getter
    private BasicConfigurationFile playerconfig;
    @Override
    public void onEnable() {
        loadConfig();
        CommandHandler.registerCommands("studio.resonos.headsteal.core.commands", this);
        Bukkit.addRecipe(RecipieManager.createSoulFragment());
        Bukkit.addRecipe(RecipieManager.createBeacon());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&cHeadsteal Graves"));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&cVersion: 1.0"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&cDeveloped by Resonos Studios."));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));

        //update prefix task
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for (Player player: Bukkit.getOnlinePlayers()) {
                RankManager.deathAccordingPrefix(player);
            }
        }, 0L, 20L);
    }



    private void loadConfig() {
        this.playerconfig = new BasicConfigurationFile(this, "players");
    }


    @Override
    public void onDisable() {
       playerconfig.save();
    }
}
