package dev.me.bombies.dynamiccore.utils;

import dev.me.bombies.dynamiccore.constants.Placeholders;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.Permissions;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
}

