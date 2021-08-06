package dev.me.bombies.dynamiccore.constants;

import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public enum CONFIG {
    // Database Info
    DEATHS_DB_NAME("deaths_database_name"),
    HOMES_DB_NAME("homes_database_name"),

    // General Plugin Info
    PREFIX("prefix"),
    PAGE_MAX_ITEMS("max_items_per_page"),
    CHAT_FORMAT("chat_format"),
    PING_FORMAT("ping_format"),
    PING_FORMAT_OTHERS("ping_format_others"),
    NO_PERMISSION("no_permission"),

    // Deaths Info
    DEATHS_TOP_HEADER("deaths_top_header"),
    DEATHS_TOP_FORMAT("deaths_top_format"),
    DEATHS_TOP_FOOTER("deaths_top_footer"),

    // Snowball Gun Info
    SNOWBALL_GUN_NAME("snowball_gun_name"),
    SNOWBALL_GUN_LORE("snowball_gun_lore"),

    // Bazooka Info
    BAZOOKA_NAME("bazooka_name"),
    BAZOOKA_LORE("bazooka_lore"),
    BAZOOKA_DMG_RADIUS("bazooka_damage_radius"),
    BAZOOKA_COOLDOWN("bazooka_cooldown"),
    BAZOOKA_COOLDOWN_MSG("bazooka_cooldown_message");

    private final String str;

    CONFIG(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static String getString(CONFIG field) {
        return PluginUtils.getStringFromConfig(field);
    }

    public static String getColouredString(CONFIG field) {
        return ChatColor.translateAlternateColorCodes('&', getString(field));
    }

    public static String getPrefix() {
        return getColouredString(CONFIG.PREFIX);
    }

    public static int getInt(CONFIG field) {
        return PluginUtils.getIntFromConfig(field);
    }

    public static boolean getBoolean(CONFIG field) {
        return PluginUtils.getBoolFromConfig(field);
    }

    public static long getLong(CONFIG field) {
        return PluginUtils.getLongFromConfig(field);
    }

    public static double getDouble(CONFIG field) {
        return PluginUtils.getDoubleFromConfig(field);
    }

    public static float getFloat(CONFIG field) {
        return (float) getDouble(field);
    }

    public static List<String> getLore(CONFIG field) {
        List<String> ret = new ArrayList<>();
        for (String s : getStringList(field))
            ret.add(ChatColor.translateAlternateColorCodes(
                    '&', s
            ));
        return ret;
    }

    public static List<String> getStringList(CONFIG field) {
        return PluginUtils.getStringListFromConfig(field);
    }

    public static List<Integer> getIntList(CONFIG field) {
        return PluginUtils.getIntListFromConfig(field);
    }

}
