package dev.me.bombies.dynamiccore.constants;

public enum NBTTags {
    SNOWBALL_GUN("snowball_gun"),
    BAZOOKA("bazooka"),
    BAZOOKA_BULLET("bazooka_bullet"),
    BAZOOKA_SHOOTER("bazooka_shooter"),
    REPLANT_TOOL("replant_tool");

    private final String str;

    NBTTags(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
