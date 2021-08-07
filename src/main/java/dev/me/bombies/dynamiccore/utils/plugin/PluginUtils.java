package dev.me.bombies.dynamiccore.utils.plugin;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.constants.Config;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class PluginUtils {
    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener l : listeners)
            plugin.getServer().getPluginManager().registerEvents(l, plugin);
    }

    public static int getIntFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getInt(field.toString());
    }

    public static String getStringFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getString(field.toString());
    }

    public static double getDoubleFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getDouble(field.toString());
    }

    public static boolean getBoolFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getBoolean(field.toString());
    }

    public static long getLongFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getLong(field.toString());
    }

    public static List<String> getStringListFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getStringList(field.toString());
    }

    public static List<Integer> getIntListFromConfig(Config field) {
        final Plugin plugin = DynamicCore.getPlugin(DynamicCore.class);
        return plugin.getConfig().getIntegerList(field.toString());
    }
}
