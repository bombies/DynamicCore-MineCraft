package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.utils.database.DeathCounterUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class DeathEvents implements Listener {
    private static HashMap<UUID, Location> lastDeathLocation = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        lastDeathLocation.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
        DeathCounterUtils utils = new DeathCounterUtils();
        try {
            utils.incrementDeathCount(e.getEntity().getUniqueId().toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        utils.closeConnection();
    }

    public static Location getLastDeathLocation(UUID uuid) {
        if (!lastDeathLocation.containsKey(uuid))
            throw new NullPointerException("User with uuid '"+uuid+"' doesn't have a last death location!");
        return lastDeathLocation.get(uuid);
    }
}
