package dev.me.bombies.dynamiccore.utils.Pagination;

import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PaginationUtils {
    /**
     * Paginate a regular list of strings
     * @param list List of strings to paginate
     * @param maxPerPage Max amount of items on each page
     * @return List of pages with each string in the list
     */
    public static List<Page> paginate(List<String> list, int maxPerPage) {
        List<Page> ret  = new ArrayList<>();
        int pagesReq    = 1;

        if (list.size() > maxPerPage)
            pagesReq = (int) Math.ceil(list.size() / (double) maxPerPage);

        int lastIndex = 0;
        for (int page = 0; page < pagesReq; page++) {
            Page pg = new Page();
            for (int i = 0; i < maxPerPage && lastIndex < list.size(); i++) {
                pg.addLine(list.get(lastIndex));
                lastIndex++;
            }
            ret.add(pg);
        }

        return ret;
    }

    /**
     * Paginate a map of UUIDs (Players)
     * @param map Map of UUIDs with integer keys
     * @param maxPerPage Max amount of items that can hold on a page
     * @param format The format to print the pages in.
     * @return List of pages with all the information from the map provided
     */
    public static List<Page> paginate(@NonNull HashMap<UUID, Integer> map, @NonNull int maxPerPage, @NonNull String format) {
        List<Page> ret      = new ArrayList<>();
        int pagesReq        = 0;
        Set<UUID> uuids   = map.keySet();

        if (map.size() > maxPerPage)
            pagesReq = (int) Math.ceil(map.size() / (double) maxPerPage);
        else if (map.size() > 0 && map.size() <= maxPerPage)
            pagesReq = 1;

        int lastIndex = map.size()-1;
        for (int page = 0; page < pagesReq; page++) {
            Page pg = new Page();
            for (int i = 0; i < maxPerPage && lastIndex >= 0; i++) {
                Player p = Bukkit.getPlayer((UUID) uuids.toArray()[lastIndex]);
                if (p == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((UUID) uuids.toArray()[lastIndex]);
                    pg.addLine(GeneralUtils.formatString(format, i+1, offlinePlayer, map.get(uuids.toArray()[lastIndex])));
                } else
                    pg.addLine(GeneralUtils.formatString(format, i+1, p, map.get(uuids.toArray()[lastIndex])));
                lastIndex--;
            }
            ret.add(pg);
        }

        return ret;
    }


}
