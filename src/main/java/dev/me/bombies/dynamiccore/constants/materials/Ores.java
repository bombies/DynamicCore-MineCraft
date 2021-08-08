package dev.me.bombies.dynamiccore.constants.materials;

import org.bukkit.Material;

public enum Ores {
    COAL_ORE(Material.COAL_ORE),
    COPPER_ORE(Material.COPPER_ORE),
    DIAMOND_ORE(Material.DIAMOND_ORE),
    EMERALD_ORE(Material.EMERALD_ORE),
    GOLD_ORE(Material.GOLD_ORE),
    IRON_ORE(Material.IRON_ORE),
    LAPIS_ORE(Material.LAPIS_ORE),
    REDSTONE_ORE(Material.REDSTONE_ORE);

    private final Material material;

    Ores(Material material) {
        this.material = material;
    }

    public Material toMaterial() {
        return material;
    }
}
