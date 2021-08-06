package dev.me.bombies.dynamiccore.utils.plugin;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.constants.CONFIG;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class PluginUtils {
    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener l : listeners)
            plugin.getServer().getPluginManager().registerEvents(l, plugin);
    }

    public static int getIntFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getInt(field.toString());
    }

    public static String getStringFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getString(field.toString());
    }

    public static double getDoubleFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getDouble(field.toString());
    }

    public static boolean getBoolFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getBoolean(field.toString());
    }

    public static long getLongFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getLong(field.toString());
    }

    public static List<String> getStringListFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getStringList(field.toString());
    }

    public static List<Integer> getIntListFromConfig(CONFIG field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getIntegerList(field.toString());
    }
}
