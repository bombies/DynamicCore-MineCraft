package dev.me.bombies.dynamiccore.constants;

public enum Permissions {
    MAIN_NODE("dc."),

    ADMIN(MAIN_NODE + "admin"),

    ENDERCHEST(MAIN_NODE + "enderchest"),

    GODMODE(MAIN_NODE + "godmode"),
    GODMODE_OTHERS(GODMODE + ".others"),

    HOME(MAIN_NODE + "home"),
    HOME_OTHERS(HOME + ".others");

    private final String str;

    Permissions(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
