package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.utils.database.DeathCounterUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathCounterEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        DeathCounterUtils utils = new DeathCounterUtils();
        utils.incrementDeathCount(e.getEntity().getUniqueId().toString());
        e.setDeathMessage("lol "+e.getEntity().getName()+" died. ("+utils.getDeathCount(e.getEntity().getUniqueId().toString())+" deaths)");
        utils.closeConnection();
    }
}
