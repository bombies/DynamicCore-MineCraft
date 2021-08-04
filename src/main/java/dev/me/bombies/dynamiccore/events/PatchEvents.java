package dev.me.bombies.dynamiccore.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PatchEvents implements Listener {

    @EventHandler
    public void onCropTrample(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getBlockData().getMaterial().equals(Material.FARMLAND)) {
            e.setCancelled(true);
        }
    }
}
