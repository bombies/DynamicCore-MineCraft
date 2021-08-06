package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.events.DeathEvents;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("This command cannot be executed from the console!");
            return true;
        }

        if (!GeneralUtils.hasPerms(player, Permissions.BACK)) {
            player.sendMessage(CONFIG.getColouredString(CONFIG.NO_PERMISSION));
            return true;
        }

        try {
            player.teleport(DeathEvents.getLastDeathLocation(player.getUniqueId()));
            player.sendMessage(ChatColor.GREEN + "Teleported to your last death location!");
        } catch (NullPointerException e) {
            player.sendMessage(ChatColor.RED + "You do not have a last death location!");
        }

        return true;
    }
}
