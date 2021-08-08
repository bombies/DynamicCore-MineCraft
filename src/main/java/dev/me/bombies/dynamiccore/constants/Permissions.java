package dev.me.bombies.dynamiccore.constants;

import org.bukkit.inventory.Inventory;

public enum Permissions {
    MAIN_NODE("dc."),

    ADMIN(MAIN_NODE + "admin"),

    ENDERCHEST(MAIN_NODE + "enderchest"),

    GODMODE(MAIN_NODE + "godmode"),
    GODMODE_OTHERS(GODMODE + ".others"),

    HOME(MAIN_NODE + "home"),
    HOME_OTHERS(HOME + ".others"),

    TRASH(MAIN_NODE + "trash"),

    BAZOOKA_COOLDOWN_BYPASS(MAIN_NODE + "bazooka.bypasscooldown"),

    BACK(MAIN_NODE + "back"),

    INVENTORY_SEE(MAIN_NODE + "invsee"),
    INVENTORY_SEE_INTERACT(INVENTORY_SEE + ".interact"),

    WORKBENCH(MAIN_NODE + "workbench"),

    COLORED_ANVIL(MAIN_NODE + "anvilcolor"),

    SKILLS_MAIN_NODE(MAIN_NODE + "skills"),
    SKILLS_SET_LEVEL(SKILLS_MAIN_NODE + ".setlevel");

    private final String str;

    Permissions(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }


}
