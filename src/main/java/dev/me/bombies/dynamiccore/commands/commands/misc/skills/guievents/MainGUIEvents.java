package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainGUIEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(GUIs.SKILLS_MAIN.toString())) {
            return;
        }

        if (e.getCurrentItem() == null)
            return;

        Material typeClicked = e.getCurrentItem().getType();
        HumanEntity player = e.getWhoClicked();
        if (typeClicked.equals(Config.getMaterial(Config.SKILLS_MINING_MATERIAL))) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED  + "This feature is a work in progress!");
        } else if (typeClicked.equals(Config.getMaterial(Config.SKILLS_GRINDING_MATERIAL))) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED  + "This feature is a work in progress!");
        } else if (typeClicked.equals(Config.getMaterial(Config.SKILLS_FARMING_MATERIAL))) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED  + "This feature is a work in progress!");
        }

        e.setCancelled(true);
    }
}
