package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfigField;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EnvoyClickEvent implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onEnvoyRightClick(PlayerInteractEvent e) {
        if (!EnvoySpawnEvent.isEnvoysSpawned())
            return;

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && e.getClickedBlock().getBlockData().getMaterial().equals(Material.CHEST)) {

            Block envoyBlock = e.getClickedBlock();

            if (!blockIsEnvoy(envoyBlock))
                return;

            String type = getEnvoyType(envoyBlock);

            EnvoyConfig config = new EnvoyConfig();

            try {
                String reward = config.parseReward(e.getPlayer(), config.getRandomReward(type));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
            } catch (NullPointerException exception) {
                DynamicCore.logger.warning("[WARN] The " + type + " envoy doesn't have any rewards!");
            }

            e.setCancelled(true);
            envoyBlock.setType(Material.AIR);
            EnvoySpawnEvent.claimEnvoy(new Coordinates(
                    envoyBlock.getLocation().getBlockX(),
                    envoyBlock.getLocation().getBlockY(),
                    envoyBlock.getLocation().getBlockZ()
            ));

            Bukkit.broadcastMessage(GeneralUtils.getPrefixedString("&e" + e.getPlayer().getName() + "&f has claimed" +
                    " a " + config.getEnvoyName(EnvoyConfigField.parseEnvoyType(type)) + "&f!"));

            if (EnvoySpawnEvent.getSpawnedLocations().isEmpty())
                EnvoySpawnEvent.endEvent();
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onEnvoyBlockBreak(BlockBreakEvent e) {
        if (!EnvoySpawnEvent.isEnvoysSpawned())
            return;

        if (!blockIsEnvoy(e.getBlock()))
            return;

        e.setCancelled(true);
    }

    private boolean blockIsEnvoy(Block block) {
        if (!block.getBlockData().getMaterial().equals(Material.CHEST))
            return false;

        TileState state = (TileState) block.getState();
        PersistentDataContainer envoyContainer = state.getPersistentDataContainer();
        NamespacedKey envoyContainerKey = new NamespacedKey(DynamicCore.getPlugin(DynamicCore.class), "envoy-type");

        return envoyContainer.has(envoyContainerKey, PersistentDataType.STRING);
    }

    private String getEnvoyType(Block block) {
        TileState state = (TileState) block.getState();
        PersistentDataContainer envoyContainer = state.getPersistentDataContainer();
        NamespacedKey envoyContainerKey = new NamespacedKey(DynamicCore.getPlugin(DynamicCore.class), "envoy-type");

        return envoyContainer.get(envoyContainerKey, PersistentDataType.STRING);
    }
}
