package dev.me.bombies.dynamiccore.utils.plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginUtils extends JavaPlugin {
    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener l : listeners)
            registerEvents(plugin, l);
    }
}
