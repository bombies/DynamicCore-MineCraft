package dev.me.bombies.dynamiccore.commands.commands.misc.personal.project1;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class IceCreamSearchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            System.out.println("This command cannot be executed from the console!");
            return true;
        }

        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
            return true;
        }

        final World world = Bukkit.getWorld("world");
        Entity iceCreamMan = world.spawnEntity(new Location(world, -362.5D, 137D, 193.7D, 180F, 2.6F), EntityType.VILLAGER);
        iceCreamMan.setCustomName(GeneralUtils.getColoredString("&b&lIce-Cream &f&lMan"));
        iceCreamMan.setCustomNameVisible(true);

        NBTEntity iceCreamManNBT = new NBTEntity(iceCreamMan);
        NBTCompound persistentDataContainer = iceCreamManNBT.getPersistentDataContainer();
        persistentDataContainer.setString("mob-type", "icecreamman");

        player.sendMessage(GeneralUtils.getPrefixedString("Spawned the &b&lIce-Cream &f&lMan&r!"));
        return true;
    }
}
