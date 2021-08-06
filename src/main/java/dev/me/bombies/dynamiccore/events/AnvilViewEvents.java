package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilViewEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onAnvilInsert(PrepareAnvilEvent e) {
        if (!GeneralUtils.hasPerms(e.getView().getPlayer(), Permissions.COLORED_ANVIL))
            return;

        AnvilInventory anvilInventory = e.getInventory();
        String colorCodedText = ChatColor.translateAlternateColorCodes('&', anvilInventory.getRenameText());

        ItemStack result = e.getResult();

        if (result == null) return;

        ItemMeta resultMeta = result.getItemMeta();

        resultMeta.setDisplayName(colorCodedText);
        result.setItemMeta(resultMeta);

        e.setResult(result);
    }
}
