package dev.me.bombies.dynamiccore.commands.commands.misc.replanttool;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class ReplantToolEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public synchronized void onCropBreak(BlockBreakEvent e) {
        if (!GeneralUtils.isCrop(e.getBlock()))
            return;

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        NBTItem itemNbt = new NBTItem(item);
        if (!itemNbt.hasKey(NBTTags.REPLANT_TOOL.toString()))
            return;

        if (!itemNbt.getBoolean(NBTTags.REPLANT_TOOL.toString()))
            return;

        if (!(e.getBlock().getBlockData() instanceof Ageable age))
            return;

        if (age.getAge() != age.getMaximumAge())
            return;

        e.setCancelled(true);
        age.setAge(0);
        e.getBlock().setBlockData(age);

        for (ItemStack drop : e.getBlock().getDrops())
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
    }
}
