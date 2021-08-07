package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            System.out.println("This command cannot be executed from console!");
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(Config.getPrefix() + GeneralUtils.formatString(Config.getString(Config.PING_FORMAT), p.getPing()));
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);

            if (target == null) {
                p.sendMessage(ChatColor.RED + "There was no player found with the name '"+args[0]+"'");
                return true;
            }

            p.sendMessage(Config.getPrefix() + GeneralUtils.formatString(Config.getString(Config.PING_FORMAT), target, p.getPing()));
        }

        return true;
    }
}
