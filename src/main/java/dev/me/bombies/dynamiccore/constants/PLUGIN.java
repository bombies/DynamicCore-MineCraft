package dev.me.bombies.dynamiccore.constants;

public enum PLUGIN {
    NAME("DynamicCore"),
    PREFIX("["+ NAME +"] ");

    private final String str;

    PLUGIN(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
