package studio.resonos.headsteal;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;
import studio.resonos.headsteal.core.managers.RecipieManager;
import studio.resonos.headsteal.core.utils.BasicConfigurationFile;
import studio.resonos.headsteal.core.utils.CC;
import studio.resonos.headsteal.core.listeners.PlayerListener;

public final class ResonosHeadsteal extends JavaPlugin {
    @Getter
    private BasicConfigurationFile playerconfig;
    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.addRecipe(RecipieManager.createSoulFragment());
        Bukkit.addRecipe(RecipieManager.createBeacon());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + " "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + "Graves Plugin"));
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + " "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + "Version: 1.0"));
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + "Developed by Resonos Studios."));
        Bukkit.getConsoleSender().sendMessage(CC.translate(Color.RED + " "));
    }



    private void loadConfig() {
        this.playerconfig = new BasicConfigurationFile(this, "players");
    }


    @Override
    public void onDisable() {
       playerconfig.save();
    }
}
