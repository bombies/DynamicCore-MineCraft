package dev.me.bombies.dynamiccore.constants;

import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum Config {
    // Database Info
    DEATHS_DB_NAME("deaths_database_name"),
    HOMES_DB_NAME("homes_database_name"),
    SKILLS_DB_NAME("skills_database_name"),

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
    BAZOOKA_COOLDOWN_MSG("bazooka_cooldown_message"),

    // Skills Info
    SKILLS_GUI_MAIN_NAME("skills_gui_name"),
    SKILLS_GUI_SIZE("skills_gui_size"),
    SKILLS_MINING_NAME("mining_item_name"),
    SKILLS_MINING_LORE("mining_item_lore"),
    SKILLS_MINING_MATERIAL("mining_item_material"),
    SKILLS_GRINDING_NAME("grinding_item_name"),
    SKILLS_GRINDING_LORE("grinding_item_lore"),
    SKILLS_GRINDING_MATERIAL("grinding_item_material"),
    SKILLS_FARMING_NAME("farming_item_name"),
    SKILLS_FARMING_LORE("farming_item_lore"),
    SKILLS_FARMING_MATERIAL("farming_item_material"),
    SKILLS_ITEM_SLOTS("item_slots"),
    SKILLS_GUI_MINING_NAME("mining_gui_name"),
    SKILLS_MINING_SCALE_BASE("mining_scale_base_amount"),
    SKILLS_MINING_SCALE_INCREASE("mining_scale_increase_amount"),
    SKILLS_MINING_BLOCK_INCREASE_LEVEL("mining_level_per_block_increase"),
    SKILLS_MINING_BLOCK_INCREASE_RATE("mining_block_increase_rate"),
    SKILLS_GUI_GRINDING_NAME("grinding_gui_name"),
    SKILLS_GRINDING_SCALE_BASE("grinding_scale_base_amount"),
    SKILLS_GRINDING_SCALE_INCREASE("grinding_scale_increase_amount"),
    SKILLS_GRINDING_XP_INCREASE_LEVEL("grinding_level_per_xp_increase"),
    SKILLS_GRINDING_XP_INCREASE_RATE("grinding_xp_increase_rate"),
    SKILLS_GUI_FARMING_NAME("farming_gui_name"),
    SKILLS_FARMING_SCALE_BASE("farming_scale_base_amount"),
    SKILLS_FARMING_SCALE_INCREASE("farming_scale_increase_amount"),
    SKILLS_FARMING_CROP_INCREASE_LEVEL("farming_level_per_crop_drop_increase"),
    SKILLS_FARMING_CROP_INCREASE_RATE("farming_crop_drop_increase_rate"),

    // Replant tool
    REPLANT_TOOL_NAME("replant_tool_name"),
    REPLANT_TOOL_LORE("replant_tool_lore"),

    // Envoy Info
    ENVOY_SILVER_LORE("silver_lore"),
    ENVOY_GOLD_LORE("gold_lore"),
    ENVOY_RUBY_LORE("ruby_lore"),
    ENVOY_PLATINUM_LORE("platinum_lore"),
    ENVOY_FLARE_NAME("envoy_flare_name"),
    ENVOY_FLARE_LORE("envoy_flare_lore");

    private final String str;

    Config(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static String getString(Config field) {
        return PluginUtils.getStringFromConfig(field);
    }

    public static String getColouredString(Config field) {
        return ChatColor.translateAlternateColorCodes('&', getString(field));
    }

    public static String getPrefix() {
        return getColouredString(Config.PREFIX);
    }

    public static String getNoPermissionMessage() {
        return getColouredString(Config.NO_PERMISSION);
    }

    public static Material getMaterial(Config field) {
        return Material.matchMaterial(getString(field));
    }

    public static int getInt(Config field) {
        return PluginUtils.getIntFromConfig(field);
    }

    public static boolean getBoolean(Config field) {
        return PluginUtils.getBoolFromConfig(field);
    }

    public static long getLong(Config field) {
        return PluginUtils.getLongFromConfig(field);
    }

    public static double getDouble(Config field) {
        return PluginUtils.getDoubleFromConfig(field);
    }

    public static float getFloat(Config field) {
        return (float) getDouble(field);
    }

    public static List<String> getLore(Config field) {
        List<String> ret = new ArrayList<>();
        for (String s : getStringList(field))
            ret.add(ChatColor.translateAlternateColorCodes(
                    '&', s
            ));
        return ret;
    }

    public static List<String> getStringList(Config field) {
        return PluginUtils.getStringListFromConfig(field);
    }

    public static List<Integer> getIntList(Config field) {
        return PluginUtils.getIntListFromConfig(field);
    }

}
