package dev.me.bombies.dynamiccore.utils;

import dev.me.bombies.dynamiccore.constants.PLACEHOLDERS;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.constants.Permissions;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class GeneralUtils {
    public static boolean hasPerms(Player p, Permissions perm) {
        return p.hasPermission(perm.toString());
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

        List<PLACEHOLDERS> placeholdersEnumList = Arrays.stream(PLACEHOLDERS.values()).toList();
        List<String> placeholders = new ArrayList<>();
        for (PLACEHOLDERS p : placeholdersEnumList)
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
                    PLACEHOLDERS p = PLACEHOLDERS.parseString(placeHolderExtract);
                    switch (p) {
                        case PLAYER, TARGET -> {
                            if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof Player player) {
                                split[i] = getValidString(split[i].replace(placeHolderExtract, player.getName()));
                            } else if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof OfflinePlayer player) {
                                split[i] = getValidString(split[i].replace(placeHolderExtract, player.getName()));
                            }
                        }
                        case DEATHS -> {
                            if (Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex) instanceof Integer) {
                                split[i] = split[i].replace(
                                        placeHolderExtract,
                                        getValidString(String.valueOf(Arrays.stream(formatSpecifiers).toList().get(placeHolderIndex)))
                                );
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
}

