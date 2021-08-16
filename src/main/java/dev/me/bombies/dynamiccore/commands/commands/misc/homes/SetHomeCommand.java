package dev.me.bombies.dynamiccore.commands.commands.misc.homes;

import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.database.HomeUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            System.out.println("This command cannot be executed from the console");
            return true;
        }

        if (GeneralUtils.hasPerms(p, Permissions.HOME)) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "You must provide a name of the home!");
                return true;
            }

            HomeUtils utils = new HomeUtils();

            try {
                utils.addHome(p.getUniqueId().toString(), args[0].replaceAll("[^a-zA-Z0-9\\s]", ""), p.getLocation());
                p.sendMessage(ChatColor.GREEN + "Home set!");
            } catch (Exception e) {
                e.printStackTrace();
                p.sendMessage(ChatColor.RED + "Something went wrong!");
            }

            utils.closeConnection();
        }

        return true;
    }
}
