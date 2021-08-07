package dev.me.bombies.dynamiccore.constants;

import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;

public enum Databases {
    DIRECTORY("/"+PLUGIN.NAME+"/databases"),
    DEATHS(PluginUtils.getStringFromConfig(Config.DEATHS_DB_NAME) +  ".db"),
    HOMES(PluginUtils.getStringFromConfig(Config.HOMES_DB_NAME) + ".db");

    private final String str;

    Databases(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
