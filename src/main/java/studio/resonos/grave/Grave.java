package studio.resonos.grave;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;
import studio.resonos.grave.core.utils.BasicConfigurationFile;
import studio.resonos.grave.listeners.PlayerListener;

import java.util.ArrayList;

public final class Grave extends JavaPlugin {
    @Getter
    private BasicConfigurationFile playerconfig;
    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Graves Plugin");
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Version: 1.0");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Developed by Resonos Studios.");
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");
    }



    private void loadConfig() {
        this.playerconfig = new BasicConfigurationFile(this, "players");
    }


    @Override
    public void onDisable() {
       playerconfig.save();
    }
}
