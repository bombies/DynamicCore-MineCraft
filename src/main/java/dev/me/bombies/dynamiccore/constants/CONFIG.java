package dev.me.bombies.dynamiccore.constants;

public enum CONFIG {
    // Database Info
    DEATHS_DB_NAME("deaths_database_name"),
    HOMES_DB_NAME("homes_database_name"),

    // General Plugin Info
    PAGE_MAX_ITEMS("max_items_per_page"),
    CHAT_FORMAT("chat_format"),

    // Deaths Info
    DEATHS_TOP_FORMAT("deaths_top_format");

    private final String str;

    CONFIG(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
