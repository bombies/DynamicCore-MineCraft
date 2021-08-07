package dev.me.bombies.dynamiccore.constants;

public enum Placeholders {
    PLAYER("%player%"),
    TARGET("%target%"),
    DEATHS("%deaths%"),
    WILDCARD("%any%"),
    PAGE("%page%"),
    MAX_PAGE("%max_page%"),
    COOLDOWN("%cooldown%"),
    PING("%ping%");

    private String str;

    Placeholders(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static Placeholders parseString(String str) {
        if (str.equals(Placeholders.PLAYER.toString()))
            return Placeholders.PLAYER;
        else if (str.equals(Placeholders.TARGET.toString()))
            return Placeholders.TARGET;
        else if (str.equals(Placeholders.DEATHS.toString()))
            return Placeholders.DEATHS;
        else if (str.equals(Placeholders.WILDCARD.toString()))
            return Placeholders.WILDCARD;
        else if (str.equals(Placeholders.PAGE.toString()))
            return Placeholders.PAGE;
        else if (str.equals(Placeholders.MAX_PAGE.toString()))
            return Placeholders.MAX_PAGE;
        else if (str.equals(Placeholders.COOLDOWN.toString()))
            return Placeholders.COOLDOWN;
        else if (str.equals(Placeholders.PING.toString()))
            return Placeholders.PING;
        throw new NullPointerException("Invalid enum \""+str+"\"");
    }
}
