package dev.me.bombies.dynamiccore.constants;

import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;

public enum DATABASES {
    DIRECTORY("/"+PLUGIN.NAME+"/databases"),
    DEATHS(PluginUtils.getStringFromConfig(CONFIG.DEATHS_DB_NAME) +  ".db"),
    HOMES(PluginUtils.getStringFromConfig(CONFIG.HOMES_DB_NAME) + ".db");

    private final String str;

    DATABASES(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
