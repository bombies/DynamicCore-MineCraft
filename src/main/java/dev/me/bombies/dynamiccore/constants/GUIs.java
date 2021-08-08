package dev.me.bombies.dynamiccore.constants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUIs {
    DEFAULT_PLACEHOLDER(Material.BLACK_STAINED_GLASS_PANE),
    ROADMAP_COMPLETE(Material.GREEN_STAINED_GLASS_PANE),
    ROADMAP_IN_PROGRESS(Material.ORANGE_STAINED_GLASS_PANE),
    ROADMAP_LOCKED(Material.RED_STAINED_GLASS_PANE),

    ROADMAP_COMPLETE_NAME("&a&lCOMPLETE"),
    ROADMAP_IN_PROGRESS_NAME("&6&lIN PROGRESS"),
    ROADMAP_LOCKED_NAME("&4&lLOCKED"),
    SKILLS_MAIN(Config.getColouredString(Config.SKILLS_GUI_MAIN_NAME)),
    SKILLS_MINING(Config.getColouredString(Config.SKILLS_GUI_MINING_NAME)),
    SKILLS_GRINDING(Config.getColouredString(Config.SKILLS_GUI_GRINDING_NAME)),
    SKILLS_FARMING(Config.getColouredString(Config.SKILLS_GUI_FARMING_NAME));

    private String str;
    private Material mat;

    GUIs(String str) {
        this.str = str;
    }

    GUIs(Material mat) {
        this.mat = mat;
    }

    @Override
    public String toString() {
        return str;
    }

    public Material toMaterial() {
        return mat;
    }

    public static ItemStack getPlaceholder() {
        ItemStack item = new ItemStack(DEFAULT_PLACEHOLDER.toMaterial());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(ChatColor.BOLD));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static void setPlaceholders(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null || inventory.getItem(i).equals(Material.AIR))
                inventory.setItem(i, GUIs.getPlaceholder());
    }
}
