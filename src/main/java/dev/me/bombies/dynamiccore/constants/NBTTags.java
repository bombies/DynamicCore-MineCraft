package dev.me.bombies.dynamiccore.constants;

public enum NBTTags {
    SNOWBALL_GUN("snowball_gun"),
    BAZOOKA("bazooka"),
    BAZOOKA_BULLET("bazooka_bullet");

    private final String str;

    NBTTags(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
