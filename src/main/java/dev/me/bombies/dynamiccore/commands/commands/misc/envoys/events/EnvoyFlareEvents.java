package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class EnvoyFlareEvents implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onEnvoyFlareBlockPlace(BlockPlaceEvent e) {
        ItemStack itemInHand = e.getItemInHand();
        NBTItem itemInHandNBT = new NBTItem(itemInHand);

        if (!itemInHandNBT.hasKey(NBTTags.ENVOY_FLARE.toString()))
            return;

        if (!itemInHandNBT.getBoolean(NBTTags.ENVOY_FLARE.toString()))
            return;

        e.setCancelled(true);

        Player player = e.getPlayer();

        if (EnvoySpawnEvent.isEnvoysSpawned()) {
            player.sendMessage(GeneralUtils.getPrefixedString("There is already an ongoing envoy event!"));
            return;
        } else {
            new EnvoySpawnEvent().spawnEnvoys(false);
            GeneralUtils.broadcastColoredStrings(
                    "&8&m------------------------------------------",
                    "",
                    "&e| &6&l"+EnvoySpawnEvent.getSpawnedLocations().size()+" Envoy Crates &7have been spawned",
                    "&e|                    &7in the &eWarZone &7by &f"+player.getName()+"&7!",
                    "",
                    "&8&m------------------------------------------"
            );
        }

        player.getInventory().getItemInMainHand().setAmount(itemInHand.getAmount() -1);
    }
}
