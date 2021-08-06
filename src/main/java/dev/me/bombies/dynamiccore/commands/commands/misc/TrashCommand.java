package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            System.out.println("This command cannot be executed from the console!");
            return true;
        }

        if (!GeneralUtils.hasPerms(p, Permissions.TRASH)) {
            p.sendMessage(CONFIG.getColouredString(CONFIG.NO_PERMISSION));
            return true;
        }

        p.openInventory(Bukkit.createInventory(p, 54, "Trash"));

        return true;
    }
}
