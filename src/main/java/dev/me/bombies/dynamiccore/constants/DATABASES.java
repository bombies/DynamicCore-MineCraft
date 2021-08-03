package dev.me.bombies.dynamiccore.constants;

public enum DATABASES {
    DIRECTORY("/"+PLUGIN.NAME+"/databases"),
    DEATHS("deaths.db");

    private final String str;

    DATABASES(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
