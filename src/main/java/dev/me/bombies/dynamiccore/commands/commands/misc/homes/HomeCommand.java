package dev.me.bombies.dynamiccore.commands.commands.misc.homes;

import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.database.HomeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            System.out.println("This command cannot be executed from the console");
            return true;
        }

        if (GeneralUtils.hasPerms(p, Permissions.HOME)) {
            HomeUtils utils = new HomeUtils();

            if (args.length == 0) {
                try {
                    List<String> homes = utils.getHomes(p.getUniqueId().toString());
                    if (homes.size() == 1) {
                        Location homeLocation = utils.getLocationOfHome(p.getUniqueId().toString(), homes.get(0));
                        p.teleport(homeLocation);
                        p.sendMessage(ChatColor.GREEN + "You teleported to your "+homes.get(0)+" home!");
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&6Homes: &f" + homes));
                    }
                } catch (NullPointerException e) {
                    p.sendMessage(ChatColor.RED + "You have no homes!");
                } catch (Exception e) {
                    e.printStackTrace();
                    p.sendMessage(ChatColor.RED + "Something went wrong!");
                }
            } else {
                try {
                    utils.teleportPlayerToHome(p, args[0]);
                } catch (NullPointerException e) {
                  p.sendMessage(ChatColor.RED + "You do not have a home called '"+args[0]+"'!");
                } catch (Exception e) {
                    e.printStackTrace();
                    p.sendMessage(ChatColor.RED + "Something went wrong!");
                }
            }

            utils.closeConnection();
        }

        return true;
    }
}
