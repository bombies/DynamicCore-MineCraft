package dev.me.bombies.dynamiccore.constants;

public enum TABLES {
    // deaths.db
    PLAYER_DEATHS("player_deaths");

    private final String str;

    TABLES(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
