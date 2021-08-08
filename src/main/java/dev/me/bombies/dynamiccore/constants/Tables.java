package dev.me.bombies.dynamiccore.constants;

public enum Tables {
    // deaths.db
    PLAYER_DEATHS("player_deaths"),

    // homes.db
    PLAYER_HOMES("player_homes"),

    // skills.db
    SKILLS_MINING("mining"),
    SKILLS_GRINDING("grinding"),
    SKILLS_FARMING("farming");

    private final String str;

    Tables(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
