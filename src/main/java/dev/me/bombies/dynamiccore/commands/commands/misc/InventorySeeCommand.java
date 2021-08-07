package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventorySeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("This command cannot be executed from the console!");
            return true;
        }

        if (!GeneralUtils.hasPerms(player, Permissions.INVENTORY_SEE)) {
            player.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You must provide the name of a player to view the inventory of");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "There was no player with the name '"+args[0]+"' found.");
            return true;
        }

        Inventory playerInv = target.getInventory();
        player.openInventory(playerInv);

        return true;
    }
}
