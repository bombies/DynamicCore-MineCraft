package dev.me.bombies.dynamiccore.utils.guibuilder;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import org.bukkit.Material;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    private static ItemStack item;

    /**
     * Constructor to make a new GUI item with just the material of the item
     * @param material Material of the item
     */
    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    /**
     * Constructor to make a new GUI item with the material and name
     * @param material Material of the item
     * @param name Name of the item
     */
    public ItemBuilder(Material material, String name) {
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    /**
     * Setting the name of the item
     * @param name Name of the item
     */
    public ItemBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the lore of the item
     * @param lore Lore of the item
     */
    public ItemBuilder setLore(List<String> lore) {
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
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchants(Enchantment enchantment) {
        item.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Set how many of these items are to be displayed in the GUI
     * @param amount
     * @throws IllegalArgumentException Thrown when a number less than or equal to 0 or greater than 64 is passed.
     */
    public ItemBuilder setAmount(int amount) {
        if (amount <= 0 || amount > 64)
            throw new IllegalArgumentException("Invalid amount!");
        item.setAmount(amount);
        return this;
    }

    /**
     * Hides the enchants from the lore of the item
     */
    public ItemBuilder hideEnchants() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Hides the attributes from the lore of the item
     */
    public ItemBuilder hideAttributes() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addBooleanNBT(NBTTags tag, boolean value) {
        NBTItem itemNbt = new NBTItem(item);
        itemNbt.setBoolean(tag.toString(), value);
        item = itemNbt.getItem();
        return this;
    }

    public ItemBuilder addStringNBT(NBTTags tag, String value) {
        NBTItem itemNbt = new NBTItem(item);
        itemNbt.setString(tag.toString(), value);
        item = itemNbt.getItem();
        return this;
    }

    public ItemBuilder addIntNBT(NBTTags tag, int value) {
        NBTItem itemNbt = new NBTItem(item);
        itemNbt.setInteger(tag.toString(), value);
        item = itemNbt.getItem();
        return this;
    }

    public ItemBuilder removeNBTTag(NBTTags tag) {
        NBTItem itemNbt = new NBTItem(item);
        if (!itemNbt.hasKey(tag.toString()))
            throw new NullPointerException("There was no tag called '"+tag+"' found for this item!");

        itemNbt.removeKey(tag.toString());
        item = itemNbt.getItem();
        return this;
    }

    public ItemBuilder setGlowing(boolean value) {
        if (value) {
            addEnchant(Enchantment.DURABILITY, 1);
            hideAttributes();
            hideEnchants();
        } else {
            if (!isGlowing())
                throw new IllegalArgumentException("This item isn't glowing!");

            for (Enchantment enchant : item.getEnchantments().keySet())
                removeEnchants(enchant);
        }
        return this;
    }

    public boolean isGlowing() {
        return !item.getEnchantments().isEmpty();
    }

    /**
     * Builds the GUI Item to be used
     * @return ItemStack of the GUI item
     */
    public ItemStack build() {
        return item;
    }
}
