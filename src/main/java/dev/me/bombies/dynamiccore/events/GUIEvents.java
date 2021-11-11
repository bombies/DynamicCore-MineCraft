package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.constants.GUIs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public interface GUIEvents extends Listener {

    default void onItemMove(InventoryClickEvent e, String guiTitle) {
        if (!e.getView().getTitle().equals(guiTitle))
            return;

        if (e.getCurrentItem() == null)
            return;

        e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    void onItemClick(InventoryClickEvent e);

    default boolean checkGUI(InventoryInteractEvent e, String check) {
        return (e.getView().getTitle().equals(check));
    }
}
