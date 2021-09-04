package dev.me.bombies.dynamiccore.utils.config.envoy;

public enum EnvoyConfigField {
    SILVER("silver"),
    RUBY("ruby"),
    GOLD("gold"),
    PLATINUM("platinum"),
    REWARDS("envoy_rewards"),
    INFO("envoy_info"),
    CHANCES("envoy_chances"),
    NAMES("envoy_names"),
    TIMES("envoy_times"),
    REMINDERS("envoy_reminders"),
    WORLD("envoy_world"),
    POSITIONS("envoy_positions");

    private final String str;

    EnvoyConfigField(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
