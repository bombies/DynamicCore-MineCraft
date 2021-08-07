package dev.me.bombies.dynamiccore.constants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUIs {
    DEFAULT_PLACEHOLDER(Material.BLACK_STAINED_GLASS_PANE),

    SKILLS_MAIN(Config.getColouredString(Config.SKILLS_GUI_NAME));

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
