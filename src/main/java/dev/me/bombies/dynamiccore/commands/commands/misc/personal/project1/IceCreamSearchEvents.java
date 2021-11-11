package dev.me.bombies.dynamiccore.commands.commands.misc.personal.project1;

import de.tr7zw.nbtapi.NBTEntity;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class IceCreamSearchEvents implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onIceCreamManInteraction(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager v))
            return;

        if (!iceCreamManCheck(v))
            return;

        GUIBuilder menuBuilder = new GUIBuilder(
                e.getPlayer(),
                GeneralUtils.getColoredString("&b&lIce-Cream &f&lShop"),
                27,
                true);

        menuBuilder.setItem(Material.SUGAR, GeneralUtils.getColoredString("&b&lGet Ice-Cream"), 13, true);
        e.getPlayer().openInventory(menuBuilder.build());
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onIceCreamManDamage(EntityDamageEvent e) {
        if (!iceCreamManCheck(e))
            return;

        e.setCancelled(true);
    }

    public boolean iceCreamManCheck(EntityEvent e) {
        if (!(e.getEntity() instanceof Villager v))
            return false;

        var villagerNBT = new NBTEntity(v);
        var villagerDataContainer = villagerNBT.getPersistentDataContainer();

        if (!villagerDataContainer.hasKey("mob-type"))
            return false;

        if (!villagerDataContainer.getString("mob-type").equals("icecreamman"))
            return false;

        return true;
    }

    public boolean iceCreamManCheck(Entity e) {
        if (!(e instanceof Villager v))
            return false;

        var villagerNBT = new NBTEntity(v);
        var villagerDataContainer = villagerNBT.getPersistentDataContainer();

        if (!villagerDataContainer.hasKey("mob-type"))
            return false;

        if (!villagerDataContainer.getString("mob-type").equals("icecreamman"))
            return false;

        return true;
    }
}
