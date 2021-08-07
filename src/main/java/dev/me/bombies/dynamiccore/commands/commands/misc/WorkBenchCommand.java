package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkBenchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("This command cannot be executed from the console");
            return true;
        }

        if (!GeneralUtils.hasPerms(player, Permissions.WORKBENCH)) {
            player.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
            return false;
        }

        player.openWorkbench(null, true);

        return true;
    }
}
