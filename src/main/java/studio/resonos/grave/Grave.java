package studio.resonos.grave;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;
import studio.resonos.grave.listeners.PlayerListener;

public final class Grave extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Graves Plugin");
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Version: 1.0");
        Bukkit.getConsoleSender().sendMessage(Color.RED + "Developed by Resonos Studios.");
        Bukkit.getConsoleSender().sendMessage(Color.RED + " ");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
