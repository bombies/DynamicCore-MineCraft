package dev.me.bombies.dynamiccore.utils.guibuilder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIItem {
    private static ItemStack item;

    /**
     * Constructor to make a new GUI item with just the material of the item
     * @param material Material of the item
     */
    public GUIItem(Material material) {
        item = new ItemStack(material);
    }

    /**
     * Constructor to make a new GUI item with the material and name
     * @param material Material of the item
     * @param name Name of the item
     */
    public GUIItem(Material material, String name) {
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    /**
     * Setting the name of the item
     * @param name Name of the item
     */
    public GUIItem setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the lore of the item
     * @param lore Lore of the item
     */
    public GUIItem setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Add an enchantment to the item
     * @param enchantment Enchantment to be added
     * @param level Level of the enchantment
     */
    public GUIItem addEnchant(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Set how many of these items are to be displayed in the GUI
     * @param amount
     * @throws IllegalArgumentException Thrown when a number less than or equal to 0 or greater than 64 is passed.
     */
    public GUIItem setAmount(int amount) {
        if (amount <= 0 || amount > 64)
            throw new IllegalArgumentException("Invalid amount!");
        item.setAmount(amount);
        return this;
    }

    /**
     * Hides the enchants from the lore of the item
     */
    public GUIItem hideEnchants() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Hides the attributes from the lore of the item
     */
    public GUIItem hideAttributes() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Builds the GUI Item to be used
     * @return ItemStack of the GUI item
     */
    public ItemStack build() {
        return item;
    }
}
