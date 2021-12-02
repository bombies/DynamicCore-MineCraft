package dev.me.bombies.dynamiccore.commands.commands.misc.envoys;

import lombok.Getter;

import java.util.List;
import java.util.Random;

// Ah, memories...
public class Envoy {
    @Getter
    private String name;

    @Getter
    private String displayName;

    @Getter
    private final EnvoyType envoyType;

    private List<String> rewardCommands;

    public Envoy(EnvoyType type) {
        switch (type) {
            case GOLD -> {
                name = "Gold";
                displayName = "&6&lGold Envoy Crate";
            }
            case SILVER -> {
                name = "Silver";
                displayName = "&7&lSilver Envoy Crate";
            }
            case RUBY -> {
                name = "Ruby";
                displayName = "&c&lRuby Envoy Crate";
            }
            case PLATINUM -> {
                name = "Platinum";
                displayName = "&b&lPlatinum Envoy Crate";
            }
        }
        envoyType = type;
    }

    public Envoy() {
        envoyType = EnvoyType.values()[new Random().nextInt(EnvoyType.values().length)];
        switch(envoyType) {
            case GOLD -> {
                name = "Gold";
                displayName = "&6&lGold Envoy Crate";
            }
            case SILVER -> {
                name = "Silver";
                displayName = "&7&lSilver Envoy Crate";
            }
            case RUBY -> {
                name = "Ruby";
                displayName = "&c&lRuby Envoy Crate";
            }
            case PLATINUM -> {
                name = "Platinum";
                displayName = "&b&lPlatinum Envoy Crate";
            }
        }
    }
}
