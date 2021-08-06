package dev.me.bombies.dynamiccore.commands.commands;

import dev.me.bombies.dynamiccore.constants.CONFIG;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private HashMap<UUID, Long> cooldowns = new HashMap<>();
    public static int BAZOOKA_COOLDOWN = CONFIG.getInt(CONFIG.BAZOOKA_COOLDOWN);

    public void setCooldown(UUID uuid, long time) {
        if (time == 0)
            cooldowns.remove(uuid);
        else cooldowns.put(uuid, time);
    }

    public long getCooldown(UUID uuid) {
        if (!cooldowns.containsKey(uuid))
            setCooldown(uuid, BAZOOKA_COOLDOWN+1);
        return cooldowns.get(uuid);
    }

    CooldownManager() {}

    public static final CooldownManager ins = new CooldownManager();

}
