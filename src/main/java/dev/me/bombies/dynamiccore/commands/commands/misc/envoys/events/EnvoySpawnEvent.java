package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfigField;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnvoySpawnEvent implements Runnable {
    @Setter @Getter
    private static boolean envoysSpawned = false;

    @Getter
    private static List<Coordinates> spawnedLocations;

    private static HashMap<Coordinates, Hologram> spawnedEnvoys = new HashMap<>();

    @Override
    public void run() {
        spawnEnvoys(true);
    }

    public void spawnEnvoys(boolean broadcastMsg) {
        EnvoyConfig config = new EnvoyConfig();

        List<Coordinates> coordinates = config.getRandomPositions();
        World envoyWorld = Bukkit.getWorld(config.getWorldString());

        for (Coordinates coordinate : coordinates) {
            Block envoy = envoyWorld.getBlockAt(coordinate.x(), coordinate.y(), coordinate.z());
            envoy.setType(Material.CHEST);

            TileState state = (TileState) envoy.getState();
            PersistentDataContainer envoyContainer = state.getPersistentDataContainer();
            NamespacedKey envoyContainerKey = new NamespacedKey(DynamicCore.getPlugin(DynamicCore.class), "envoy-type");

            String type = config.getRandomType();
            envoyContainer.set(envoyContainerKey, PersistentDataType.STRING, type);
            state.update();

            Hologram envoyHologram = HologramsAPI.createHologram(DynamicCore.getPlugin(DynamicCore.class), envoy.getLocation().add(0.5, 2, 0.5));
            envoyHologram.appendTextLine(config.getEnvoyName(EnvoyConfigField.parseEnvoyType(type)));
            for (String s : EnvoyConfigField.getEnvoyDisplayLore(EnvoyConfigField.parseEnvoyType(type)))
                envoyHologram.appendTextLine(s);

            spawnedEnvoys.put(
                    new Coordinates(
                            envoy.getLocation().getBlockX(),
                            envoy.getLocation().getBlockY(),
                            envoy.getLocation().getBlockZ()
                    ),
                    envoyHologram);
        }

        spawnedLocations = coordinates;
        setEnvoysSpawned(true);

        if (broadcastMsg)
            GeneralUtils.broadcastColoredStrings(
                    "&8&m------------------------------------------",
                    "",
                    "&e| &6&l"+spawnedLocations.size()+" Envoy Crates &7have been spawned",
                    "&e|                    &7in the &eWarZone&7!",
                    "",
                    "&8&m------------------------------------------"
            );
    }

    public static void claimEnvoy(Coordinates coordinates) {
        spawnedLocations.remove(coordinates);
        spawnedEnvoys.get(coordinates).delete();
        spawnedEnvoys.remove(coordinates);
    }

    public static Hologram getHologram(Coordinates coordinates) {
        if (!spawnedLocations.contains(coordinates))
            throw new IllegalArgumentException("Invalid coordinate!");

        return spawnedEnvoys.get(coordinates);
    }

    public static void endEvent() {
        if (!spawnedLocations.isEmpty()) {
            EnvoyConfig config = new EnvoyConfig();

            List<Coordinates> spawnedLocationsCopy = new ArrayList<>(spawnedLocations);
            for (Coordinates coord : spawnedLocationsCopy) {
                Block envoy = config.getWorld().getBlockAt(coord.x(), coord.y(), coord.z());
                envoy.setType(Material.AIR);
                claimEnvoy(coord);
            }
        }

        EnvoySpawnEvent.setEnvoysSpawned(false);
        GeneralUtils.broadcastColoredStrings(
                "&8&m------------------------------------------",
                "",
                "&e|            &6&lThe envoy event has ended!",
                "&e|        &7There will be a next envoy soon!",
                "",
                "&8&m------------------------------------------"
        );
    }
}
