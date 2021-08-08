package dev.me.bombies.dynamiccore.utils;

import com.google.common.base.Strings;
import dev.me.bombies.dynamiccore.constants.Placeholders;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.Permissions;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralUtils {
    public static boolean hasPerms(Player p, Permissions perm) {
        return p.hasPermission(perm.toString());
    }

    public static boolean hasPerms(CommandSender s, Permissions perm) {
        return s.hasPermission(perm.toString());
    }

    public static boolean stringIsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatString(@NonNull String str, Object... formatSpecifiers) {
        if (formatSpecifiers.length == 0)
            return str;

        String colorCodedString = ChatColor.translateAlternateColorCodes('&', str);
        String[] split = colorCodedString.split(" ");

        List<Placeholders> placeholdersEnumList = Arrays.stream(Placeholders.values()).toList();
        List<String> placeholders = new ArrayList<>();
        for (Placeholders p : placeholdersEnumList)
            placeholders.add(p.toString());

        int placeHolderIndex = 0;
        for (int i = 0; i < split.length && placeHolderIndex < formatSpecifiers.length; i++) {
            if (split[i].contains(PLUGIN.PLACEHOLDER_SYMBOL.toString())) {
                String placeHolderExtract;
                try {
                    placeHolderExtract = extractPlaceHolder(split[i].toLowerCase());
                } catch (NullPointerException e) {
                    continue;
                }
                if (placeholders.contains(placeHolderExtract)) {
                    Placeholders p = Placeholders.parseString(placeHolderExtract);
                    switch (p) {
                        case PLAYER, TARGET -> {
                            if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof Player player) {
                                split[i] = getValidString(split[i].replace(placeHolderExtract, player.getName()));
                            } else if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof OfflinePlayer player) {
                                split[i] = getValidString(split[i].replace(placeHolderExtract, player.getName()));
                            }
                        }
                        case PAGE, MAX_PAGE, DEATHS, COOLDOWN, PING  -> {
                            if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof Integer) {
                                split[i] = split[i].replace(
                                        placeHolderExtract,
                                        getValidString((String.valueOf(Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex))))
                                );
                            } else if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof String) {
                                if (GeneralUtils.stringIsInt((String) Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex))) {
                                    split[i] = split[i].replace(
                                            placeHolderExtract,
                                            getValidString(((String) Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex)))
                                    );
                                }
                            }
                        }
                        case WILDCARD -> split[i] = split[i].replace(
                                placeHolderExtract,
                                getValidString(Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex).toString()
                                )
                        );
                    }
                    placeHolderIndex++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String s : split)
            sb.append(s).append(" ");
        return sb.toString();
    }

    public static String extractPlaceHolder(String s) {
        String str = s.replaceAll("[^"+ PLUGIN.PLACEHOLDER_SYMBOL +"]", "");

        if (str.length() != 2 || !s.contains(PLUGIN.PLACEHOLDER_SYMBOL.toString()))
            throw new NullPointerException("This string doesn't have any placeholders!");

        return s.substring(s.indexOf(PLUGIN.PLACEHOLDER_SYMBOL.toString()), s.lastIndexOf(PLUGIN.PLACEHOLDER_SYMBOL.toString())+1);
    }

    public static String getValidString(String s) {
        return s.replaceAll("%", "%%");
    }

    public static String getColoredString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String toSnakeCase(String s) {
        String[] split = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String str : split)
            sb.append(String.valueOf(str.charAt(0)).toUpperCase()).append(str.substring(1).toLowerCase()).append(" ");
        return sb.toString();
    }

    public static int getEmptySlots(Inventory inventory) {
        ItemStack[] content = inventory.getContents();
        int count = 0;
        for (ItemStack item : content)
            if (item == null || item.getType().equals(Material.AIR))
                count++;
        return count;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static boolean isPickaxe(Material material) {
        switch (material) {
            case DIAMOND_PICKAXE, GOLDEN_PICKAXE, IRON_PICKAXE,
                    NETHERITE_PICKAXE, STONE_PICKAXE, WOODEN_PICKAXE -> {
                return true;
            }
            default -> {
                return false;
            }
        }

    }

    public static boolean isSolidBlock(Block block) {
        if (block == null)
            return false;

        if (isCrop(block))
            return false;

        switch (block.getBlockData().getMaterial()) {
            case GRASS, TALL_GRASS, TALL_SEAGRASS, VINE,
                    WEEPING_VINES_PLANT, TWISTING_VINES_PLANT, ROSE_BUSH,
                    WITHER_ROSE, PEONY, POPPY, OXEYE_DAISY,
                    ACACIA_SAPLING, BAMBOO_SAPLING, BIRCH_SAPLING, DARK_OAK_SAPLING,
                    JUNGLE_SAPLING, OAK_SAPLING, RAIL, ACTIVATOR_RAIL,
                    DETECTOR_RAIL, POWERED_RAIL, TORCH, REDSTONE_TORCH, REDSTONE_WALL_TORCH,
                    WALL_TORCH, SOUL_WALL_TORCH, SOUL_TORCH, FIRE, SOUL_FIRE,
                    BRAIN_CORAL, BUBBLE_CORAL, DEAD_BRAIN_CORAL, DEAD_BUBBLE_CORAL, DEAD_FIRE_CORAL,
                    DEAD_HORN_CORAL, DEAD_TUBE_CORAL, FIRE_CORAL, HORN_CORAL,
                    TUBE_CORAL, FERN, LARGE_FERN, DEAD_BUSH,
                    NETHER_SPROUTS, FLOWER_POT, BLACK_BANNER, BLACK_WALL_BANNER,
                    BLUE_BANNER, BLUE_WALL_BANNER, BROWN_BANNER, BROWN_WALL_BANNER,
                    CREEPER_BANNER_PATTERN, CYAN_BANNER, CYAN_WALL_BANNER, FLOWER_BANNER_PATTERN,
                    GRAY_BANNER, GRAY_WALL_BANNER, GREEN_BANNER, GREEN_WALL_BANNER,
                    GLOBE_BANNER_PATTERN, LIGHT_BLUE_BANNER, LIGHT_BLUE_WALL_BANNER, LIGHT_GRAY_BANNER,
                    LIGHT_GRAY_WALL_BANNER, LIME_BANNER, LIME_WALL_BANNER, MAGENTA_BANNER,
                    MAGENTA_WALL_BANNER, ORANGE_WALL_BANNER, ORANGE_BANNER, MOJANG_BANNER_PATTERN,
                    PIGLIN_BANNER_PATTERN, PINK_BANNER, PINK_WALL_BANNER, PURPLE_BANNER, PURPLE_WALL_BANNER,
                    RED_BANNER, RED_WALL_BANNER, WHITE_BANNER, WHITE_WALL_BANNER, YELLOW_BANNER, YELLOW_WALL_BANNER ,
                    SKULL_BANNER_PATTERN, BLACK_CARPET, CARVED_PUMPKIN, BLUE_CARPET, BROWN_CARPET, CYAN_CARPET,
                    GRAY_CARPET, GREEN_CARPET, LIGHT_GRAY_CARPET, LIGHT_BLUE_CARPET,
                    LIME_CARPET, MAGENTA_CARPET, MOSS_CARPET, ORANGE_CARPET, PINK_CARPET,
                    PURPLE_CARPET, RED_CARPET, WHITE_CARPET, YELLOW_CARPET, SNOW, COBWEB,
                    LEVER, ACACIA_BUTTON, BIRCH_BUTTON, CRIMSON_BUTTON, DARK_OAK_BUTTON,
                    JUNGLE_BUTTON, OAK_BUTTON, SPRUCE_BUTTON, STONE_BUTTON, WARPED_BUTTON,
                    POLISHED_BLACKSTONE_BUTTON, TRIPWIRE_HOOK, STRING, SKELETON_SKULL, CREEPER_HEAD,
                    CREEPER_WALL_HEAD, DRAGON_HEAD, DRAGON_WALL_HEAD, PISTON_HEAD, PLAYER_HEAD, PLAYER_WALL_HEAD,
                    ZOMBIE_HEAD, ZOMBIE_WALL_HEAD, SKELETON_WALL_SKULL, WITHER_SKELETON_SKULL,
                    WITHER_SKELETON_WALL_SKULL, COMPARATOR, REPEATER, LADDER,
                    SCAFFOLDING -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }

    public static boolean isCrop(Block block) {
        if (block == null)
            return false;

        switch (block.getBlockData().getMaterial()) {
            case WHEAT, BEETROOTS, CARROTS, POTATOES,
                    MELON, PUMPKIN, BAMBOO, COCOA_BEANS,
                    SUGAR_CANE, SWEET_BERRY_BUSH, CACTUS,
                    BROWN_MUSHROOM, RED_MUSHROOM, KELP_PLANT,
                    SEA_PICKLE, NETHER_WART_BLOCK, CHORUS_PLANT,
                    CRIMSON_FUNGUS, WARPED_FUNGUS, CHORUS_FLOWER
                    -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}

