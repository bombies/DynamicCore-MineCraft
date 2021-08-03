package dev.me.bombies.dynamiccore.constants;

public enum PLACEHOLDERS {
    PLAYER("%player%"),
    TARGET("%target%"),
    DEATHS("%deaths%"),
    WILDCARD("%any%");

    private String str;

    PLACEHOLDERS(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static PLACEHOLDERS parseString(String str) {
        if (str.equals(PLACEHOLDERS.PLAYER.toString()))
            return PLACEHOLDERS.PLAYER;
        else if (str.equals(PLACEHOLDERS.TARGET.toString()))
            return PLACEHOLDERS.TARGET;
        else if (str.equals(PLACEHOLDERS.DEATHS.toString()))
            return PLACEHOLDERS.DEATHS;
        else if (str.equals(PLACEHOLDERS.WILDCARD.toString()))
            return PLACEHOLDERS.WILDCARD;
        throw new NullPointerException("Invalid enum \""+str+"\"");
    }
}
