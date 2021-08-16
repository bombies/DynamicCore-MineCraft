package dev.me.bombies.dynamiccore.utils.guibuilder;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.GrindingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.MiningEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUIBuilder {
    private Inventory GUI;
    private boolean addPlaceholders;

    public GUIBuilder(@NonNull Player player, String name, @NonNull int size, boolean addPlaceholders) {
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

    public void setItem(ItemStack item, int slot) {
        GUI.setItem(slot, item);
    }

    public Inventory build() {
        if (addPlaceholders)
            GUIs.setPlaceholders(GUI);
        return GUI;
    }

    private ItemStack buildItem(@NonNull Material material, @NonNull String itemName, boolean isEnchanted, boolean hideAttributes) {
        ItemBuilder itemBuilder = new ItemBuilder(material, itemName);
        if (isEnchanted) {
            itemBuilder.addEnchant(Enchantment.DURABILITY, 1).hideEnchants();
        }

        if (hideAttributes)
            itemBuilder.hideAttributes();

        return itemBuilder.build();
    }

    private ItemStack buildItem(@NonNull Material material, @NonNull String itemName, List<String> lore, boolean isEnchanted, boolean hideAttributes) {
        ItemBuilder itemBuilder = new ItemBuilder(material, itemName);
        itemBuilder.setLore(lore);
        if (isEnchanted) {
            itemBuilder.addEnchant(Enchantment.DURABILITY, 1).hideEnchants();
        }

        if (hideAttributes)
            itemBuilder.hideAttributes();

        return itemBuilder.build();
    }

    public static GUIBuilder buildRoadMap(GUIs skill, Player player, String name, int seriesIndex, int currentLevel, Tables table) {
        GUIBuilder builder = new GUIBuilder(player, name, 54, true);

        System.out.println(seriesIndex);

        int[] roadMapSlots = new int[] {
                9, 10, 19, 28, 37, 38, 39, 30, 21, 12, 13, 14, 23, 32, 41, 42, 43, 34, 25, 16, 17
        };

        int startingLevel = (seriesIndex * 21) + 1;
        List<ItemStack> roadMapPanes = new ArrayList<>();

        for (int i = startingLevel; i < startingLevel + roadMapSlots.length; i++) {
            if (i == currentLevel+1)
                roadMapPanes.add(
                        builder.buildItem(
                                GUIs.ROADMAP_IN_PROGRESS.toMaterial(),
                                GeneralUtils.getColoredString("&a&lLEVEL " + i),
                                getRoadMapLore(player, GUIs.ROADMAP_IN_PROGRESS, i, skill, table),
                                true, true
                        )
                );
            else if (i <= currentLevel)
                roadMapPanes.add(
                        builder.buildItem(
                                GUIs.ROADMAP_COMPLETE.toMaterial(),
                                GeneralUtils.getColoredString("&a&lLEVEL " + i),
                                getRoadMapLore(player, GUIs.ROADMAP_COMPLETE, i, skill, table),
                                true, true
                        )
                );
            else if (i >= currentLevel+2)
                roadMapPanes.add(
                        builder.buildItem(
                                GUIs.ROADMAP_LOCKED.toMaterial(),
                                GeneralUtils.getColoredString("&a&lLEVEL " + i),
                                getRoadMapLore(player, GUIs.ROADMAP_LOCKED, i, skill, table),
                                true, true
                        )
                );
        }

        for (int i = 0; i < roadMapSlots.length; i++)
            builder.setItem(roadMapPanes.get(i), roadMapSlots[i]);

        return builder;
    }

    private static List<String> getRoadMapLore(Player player, GUIs roadmapPaneType, int level, GUIs skill, Tables table) {
        switch (skill) {
            case SKILLS_FARMING, SKILLS_GRINDING, SKILLS_MINING -> {}
            default -> throw new IllegalArgumentException("Unsupported skill enum!");
        }

        List<String> ret = new ArrayList<>();
        ret.add(GeneralUtils.getColoredString("&l"));
        ret.add(GeneralUtils.getColoredString("&2&lPROGRESS"));
        ret.add(GeneralUtils.getColoredString("&l"));

        int currentXP   = -1;
        int maxXP       = SkillsUtils.ins.getNextXPForLevel(skill, level);
        int maxBars     = 25;

        int doNothing;
        switch (table) {
            case SKILLS_MINING -> currentXP     = MiningEvents.getXPForPlayer(player);
            case SKILLS_FARMING -> currentXP    = FarmingEvents.getXPForPlayer(player);
            case SKILLS_GRINDING -> currentXP   = GrindingEvents.getXPForPlayer(player);
            default -> doNothing = 1;
        }

        switch (roadmapPaneType) {
            case ROADMAP_COMPLETE -> {
                ret.add(GeneralUtils.getColoredString("&2&l┃ &a&lXP: &f" + maxXP + "/" + maxXP));
                ret.add(GeneralUtils.getColoredString("&8  ["+ GeneralUtils.getProgressBar(maxXP, maxXP, maxBars, '|', ChatColor.GREEN, ChatColor.GRAY)+"&8]"));
                ret.add(GeneralUtils.getColoredString("&l"));
                ret.add(GeneralUtils.getColoredString("&2&lCOMPLETED"));
            }
            case ROADMAP_IN_PROGRESS -> {
                ret.add(GeneralUtils.getColoredString("&2&l┃ &a&lXP: &f" + currentXP + "/" + maxXP));
                ret.add(GeneralUtils.getColoredString("&8  ["+ GeneralUtils.getProgressBar(currentXP, maxXP, maxBars, '|', ChatColor.GREEN, ChatColor.GRAY)+"&8]"));
                ret.add(GeneralUtils.getColoredString("&l"));
                ret.add(GeneralUtils.getColoredString("&6&lIN PROGRESS"));
            }
            case ROADMAP_LOCKED -> {
                ret.add(GeneralUtils.getColoredString("&2&l┃ &a&lXP: &f" + 0 + "/" + maxXP));
                ret.add(GeneralUtils.getColoredString("&8  ["+ GeneralUtils.getProgressBar(0, maxXP, maxBars, '|', ChatColor.GREEN, ChatColor.GRAY)+"&8]"));
                ret.add(GeneralUtils.getColoredString("&l"));
                ret.add(GeneralUtils.getColoredString("&c&lLOCKED"));
            }
            default -> throw new IllegalArgumentException("Unsupported enum!");
        }

        float rate;
        int incLevel;
        switch (skill) {
            case SKILLS_MINING -> {
                rate = Config.getFloat(Config.SKILLS_MINING_BLOCK_INCREASE_RATE);
                incLevel = Config.getInt(Config.SKILLS_MINING_BLOCK_INCREASE_LEVEL);
                if (level % incLevel == 0 && level != 0) {
                    int percentageRate = (int)((rate * (level / incLevel))*100);
                    ret.add(GeneralUtils.getColoredString("&l"));
                    ret.add(GeneralUtils.getColoredString("&2&lREWARDS"));
                    ret.add(GeneralUtils.getColoredString("&2&l┃ &f"+percentageRate+"% more ore drops"));
                }
            }
            case SKILLS_FARMING -> {
                rate = Config.getFloat(Config.SKILLS_FARMING_CROP_INCREASE_RATE);
                incLevel = Config.getInt(Config.SKILLS_FARMING_CROP_INCREASE_LEVEL);
                if (level % incLevel == 0 && level != 0) {
                    int percentageRate = (int)((rate * (level / incLevel))*100);
                    ret.add(GeneralUtils.getColoredString("&l"));
                    ret.add(GeneralUtils.getColoredString("&2&lREWARDS"));
                    ret.add(GeneralUtils.getColoredString("&2&l┃ &f"+percentageRate+"% more crop drops"));
                };
            }
            case SKILLS_GRINDING -> {
                rate = Config.getFloat(Config.SKILLS_GRINDING_XP_INCREASE_RATE);
                incLevel = Config.getInt(Config.SKILLS_GRINDING_XP_INCREASE_LEVEL);
                if (level % incLevel == 0 && level != 0) {
                    int percentageRate = (int)((rate * (level / incLevel))*100);
                    ret.add(GeneralUtils.getColoredString("&l"));
                    ret.add(GeneralUtils.getColoredString("&2&lREWARDS"));
                    ret.add(GeneralUtils.getColoredString("&2&l┃ &f"+percentageRate+"% more xp drops"));
                }
            }
        }
        return ret;
    }
}
