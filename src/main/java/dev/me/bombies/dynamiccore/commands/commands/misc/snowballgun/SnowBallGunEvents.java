package dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SnowBallGunEvents implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack item = e.getItem();
            NBTItem itemNBT = new NBTItem(item);

            if (!itemNBT.hasKey(NBTTags.SNOWBALL_GUN.toString()))
                return;

            if (!itemNBT.getBoolean(NBTTags.SNOWBALL_GUN.toString()))
                return;

            Player p = e.getPlayer();
            p.launchProjectile(Snowball.class);

            if (e.getClickedBlock() != null)
                if (e.getClickedBlock().getBlockData().getMaterial().equals(Material.GRASS_BLOCK))
                    e.setCancelled(true);
        }
    }
}
