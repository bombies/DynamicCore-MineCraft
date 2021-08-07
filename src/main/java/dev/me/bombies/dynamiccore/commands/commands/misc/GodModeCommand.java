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

public class GodModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))  {
            System.out.println("This command cannot be executed from console!");
            return true;
        }

        if (args.length == 0) {
            selfGodMode(p);
        } else {
            if (GeneralUtils.hasPerms(p, Permissions.GODMODE_OTHERS)) {
                Player target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "No player with the name '" + args[0] + "' was found.");
                    return true;
                }

                if (target.isInvulnerable()) {
                    target.setInvulnerable(false);
                    p.sendMessage(ChatColor.GREEN +  "You've turned off godmode for " + target.getName());
                    target.sendMessage(ChatColor.RED + "God mode toggled off.");
                } else {
                    target.setInvulnerable(true);
                    p.sendMessage(ChatColor.GREEN +  "You've turned on godmode for " + target.getName());
                    target.sendMessage(ChatColor.GREEN + "God mode toggled on.");
                }
            } else {
                selfGodMode(p);
            }
        }
        return true;
    }

    private void selfGodMode(Player p) {
        if (GeneralUtils.hasPerms(p, Permissions.GODMODE)) {
            if (p.isInvulnerable()) {
                p.setInvulnerable(false);
                p.sendMessage(ChatColor.RED + "God mode toggled off.");
            } else {
                p.setInvulnerable(true);
                p.sendMessage(ChatColor.GREEN + "God mode toggled on.");
            }
        } else p.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
    }
}
