package dev.me.bombies.dynamiccore.utils.guibuilder;

import dev.me.bombies.dynamiccore.constants.GUIs;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIBuilder {
    private Inventory GUI;
    private boolean addPlaceholders;

    public GUIBuilder(Player player, String name, int size, boolean addPlaceholders) {
        if (player == null)
            throw new NullPointerException("Player can't be null!");

        if (size % 9 != 0 || size <= 0)
            throw new IllegalArgumentException("Invalid size!");

        this.addPlaceholders = addPlaceholders;
        this.GUI = Bukkit.createInventory(player, size, name);
    }

    public void setItem(Material material, String itemName, int slot) {
        ItemStack item = buildItem(material, itemName, false, false);
        GUI.setItem(slot, item);
    }

    public void setItem(Material material, String itemName, int slot, boolean isEnchanted) {
        ItemStack item = buildItem(material, itemName, isEnchanted, false);
        GUI.setItem(slot, item);
    }

    public void setItem(Material material, String itemName, int slot, boolean isEnchanted, boolean hideAttributes) {
        ItemStack item = buildItem(material, itemName, isEnchanted, hideAttributes);
        GUI.setItem(slot, item);
    }

    public void setItem(Material material, String itemName, List<String> lore, int slot) {
        ItemStack item = buildItem(material, itemName, lore, false, false);
        GUI.setItem(slot, item);
    }

    public void setItem(Material material, String itemName, List<String> lore, int slot, boolean isEnchanted) {
        ItemStack item = buildItem(material, itemName, lore, isEnchanted, false);
        GUI.setItem(slot, item);
    }

    public void setItem(Material material, String itemName, List<String> lore, int slot, boolean isEnchanted, boolean hideAttributes) {
        ItemStack item = buildItem(material, itemName, lore, isEnchanted, hideAttributes);
        GUI.setItem(slot, item);
    }

    public Inventory build() {
        if (addPlaceholders)
            GUIs.setPlaceholders(GUI);
        return GUI;
    }

    private ItemStack buildItem(@NonNull Material material, @NonNull String itemName, boolean isEnchanted, boolean hideAttributes) {
        GUIItem itemBuilder = new GUIItem(material, itemName);
        if (isEnchanted) {
            itemBuilder.addEnchant(Enchantment.DURABILITY, 1).hideEnchants();
        }

        if (hideAttributes)
            itemBuilder.hideAttributes();

        return itemBuilder.build();
    }

    private ItemStack buildItem(@NonNull Material material, @NonNull String itemName, List<String> lore, boolean isEnchanted, boolean hideAttributes) {
        GUIItem itemBuilder = new GUIItem(material, itemName);
        itemBuilder.setLore(lore);
        if (isEnchanted) {
            itemBuilder.addEnchant(Enchantment.DURABILITY, 1).hideEnchants();
        }

        if (hideAttributes)
            itemBuilder.hideAttributes();

        return itemBuilder.build();
    }

}
