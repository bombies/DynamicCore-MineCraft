package dev.me.bombies.dynamiccore.commands.commands.misc.replanttool;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ReplantToolEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public synchronized void onCropBreak(BlockBreakEvent e) {
        if (!GeneralUtils.isCrop(e.getBlock()))
            return;

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

        if (item.getType().equals(Material.AIR))
            return;

        NBTItem itemNbt = new NBTItem(item);
        if (!itemNbt.hasKey(NBTTags.REPLANT_TOOL.toString()))
            return;

        if (!itemNbt.getBoolean(NBTTags.REPLANT_TOOL.toString()))
            return;

        if (!(e.getBlock().getBlockData() instanceof Ageable age))
            return;

        if (e.getBlock().getBlockData().getMaterial().equals(Material.SUGAR_CANE)) {
            Block sugarCane = e.getBlock();
            int rootY = GeneralUtils.getRootSugarCaneLocation(sugarCane).getBlockY();
            for (int i = rootY+1; i < 256; i++) {
                if (DynamicCore.getPlugin(DynamicCore.class)
                        .getServer()
                        .getWorld(sugarCane.getWorld().getName())
                        .getBlockAt(sugarCane.getX(), i, sugarCane.getZ())
                        .getBlockData()
                        .getMaterial()
                        .equals(Material.SUGAR_CANE)) {

                    DynamicCore.getPlugin(DynamicCore.class)
                            .getServer()
                            .getWorld(sugarCane.getWorld().getName())
                            .getBlockAt(sugarCane.getX(), i, sugarCane.getZ())
                            .setType(Material.AIR);

                    DynamicCore.getPlugin(DynamicCore.class)
                            .getServer()
                            .getWorld(sugarCane.getWorld().getName())
                            .dropItemNaturally(new Location(
                                            e.getBlock().getWorld(),
                                            sugarCane.getX(),
                                            i,
                                            sugarCane.getZ()
                                    ),
                                    new ItemStack(Material.SUGAR_CANE)
                            );
                    new FarmingEvents().logic(e.getPlayer());
                } else break;
            }

            if (sugarCane.getLocation().getBlockY() == rootY)
                e.setCancelled(true);
            else
                DynamicCore.getPlugin(DynamicCore.class)
                    .getServer()
                    .getWorld(sugarCane.getWorld().getName())
                    .getBlockAt(sugarCane.getX(), rootY, sugarCane.getZ())
                    .setType(Material.SUGAR_CANE);
            return;
        }

        e.setCancelled(true);

        if (age.getAge() != age.getMaximumAge()) return;

        age.setAge(0);
        e.getBlock().setBlockData(age);

        new FarmingEvents().logic(e.getPlayer());

        for (ItemStack drop : e.getBlock().getDrops())
            e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
    }
}
