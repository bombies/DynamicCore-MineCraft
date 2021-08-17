package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents;

import dev.me.bombies.dynamiccore.constants.GUIs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SkillGUIEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(GUIs.SKILLS_MINING.toString())
        && !e.getView().getTitle().equals(GUIs.SKILLS_GRINDING.toString())
        && !e.getView().getTitle().equals(GUIs.SKILLS_FARMING.toString()))
            return;

        e.setCancelled(true);
    }
}
