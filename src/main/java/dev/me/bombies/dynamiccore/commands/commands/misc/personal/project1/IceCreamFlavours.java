package dev.me.bombies.dynamiccore.commands.commands.misc.personal.project1;

public enum IceCreamFlavours {
    CHOCOLATE("chocolate"),
    CHOCOLATE_CHIP("chocolate chip"),
    VANILLA("vanilla"),
    STRAWBERRY("strawberry"),
    FUDGE("fudge"),
    GRAPENUT("grapenut");

    private String str;

    IceCreamFlavours(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static IceCreamFlavours parse(String flavour) {
        for (IceCreamFlavours flavours : IceCreamFlavours.values())
            if (flavours.toString().equalsIgnoreCase(flavour))
                return flavours;
        return null;
    }
}
