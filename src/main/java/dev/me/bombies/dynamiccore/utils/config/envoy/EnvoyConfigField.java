package dev.me.bombies.dynamiccore.utils.config.envoy;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.utils.config.IJSONField;

import java.util.List;

public enum EnvoyConfigField implements IJSONField {
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
    MAX_ENVOYS("max_envoys"),
    POSITIONS("envoy_positions"),
    ID("envoy_id"),
    X_POS("x"),
    Y_POS("y"),
    Z_POS("z");

    private final String str;

    EnvoyConfigField(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    public static EnvoyConfigField[] getEnvoyTypes() {
        EnvoyConfigField[] ret = new EnvoyConfigField[4];

        ret[0] = SILVER;
        ret[1] = RUBY;
        ret[2] = GOLD;
        ret[3] = PLATINUM;

        return ret;
    }

    public static EnvoyConfigField parseEnvoyType(String envoyType) {
        if (envoyType.equalsIgnoreCase(SILVER.toString()))
            return SILVER;
        else if (envoyType.equalsIgnoreCase(RUBY.toString()))
            return RUBY;
        else if (envoyType.equalsIgnoreCase(GOLD.toString()))
            return GOLD;
        else if (envoyType.equalsIgnoreCase(PLATINUM.toString()))
            return PLATINUM;
        else return null;
    }

    public static List<String> getEnvoyDisplayLore(EnvoyConfigField envoy) {
        switch (envoy) {
            case SILVER -> {
                return Config.getLore(Config.ENVOY_SILVER_LORE);
            }
            case RUBY -> {
                return Config.getLore(Config.ENVOY_RUBY_LORE);
            }
            case GOLD -> {
                return Config.getLore(Config.ENVOY_GOLD_LORE);
            }
            case PLATINUM -> {
                return Config.getLore(Config.ENVOY_PLATINUM_LORE);
            }
            default -> throw new IllegalArgumentException("Invalid field!");
        }
    }
}
