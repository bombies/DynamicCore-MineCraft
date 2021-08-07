package dev.me.bombies.dynamiccore.commands.commands.misc;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.Pagination.Page;
import dev.me.bombies.dynamiccore.utils.Pagination.PaginationUtils;
import dev.me.bombies.dynamiccore.utils.database.DeathCounterUtils;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeathTopCommand implements CommandExecutor {
    final int maxPerPage = PluginUtils.getIntFromConfig(Config.PAGE_MAX_ITEMS);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("This command cannot be executed from the console!");
            return true;
        }

        final Player p = (Player) sender;
        final DeathCounterUtils utils = new DeathCounterUtils();

        List<Page> information = PaginationUtils.paginate(utils.getSortedPlayerMap(), maxPerPage, PluginUtils.getStringFromConfig(Config.DEATHS_TOP_FORMAT));

        if (args.length == 0) {
            if (information.isEmpty())
                p.sendMessage(ChatColor.RED + "There is no information yet!");
            else {
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        GeneralUtils.formatString(PluginUtils.getStringFromConfig(Config.DEATHS_TOP_HEADER), 1, information.size())
                ));
                for (String s : information.get(0).getInformation())
                    p.sendMessage(s);
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        PluginUtils.getStringFromConfig(Config.DEATHS_TOP_FOOTER)
                ));
            }
        } else {
            if (!GeneralUtils.stringIsInt(args[0])) {
                p.sendMessage(ChatColor.RED + "Invalid argument!");
                utils.closeConnection();
                return true;
            }

            int page = Integer.parseInt(args[0]);

            if (page < 1) {
                p.sendMessage(ChatColor.RED + "You must provide a positive integer!");
                utils.closeConnection();
                return true;
            }

            if (page > information.size()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        GeneralUtils.formatString(PluginUtils.getStringFromConfig(Config.DEATHS_TOP_HEADER), information.size(), information.size())
                ));
                for (String s : information.get(information.size()-1).getInformation())
                    p.sendMessage(s);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        GeneralUtils.formatString(PluginUtils.getStringFromConfig(Config.DEATHS_TOP_HEADER), page, information.size())
                ));
                for (String s : information.get(page-1).getInformation())
                    p.sendMessage(s);
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&',
                    PluginUtils.getStringFromConfig(Config.DEATHS_TOP_FOOTER)
            ));
        }

        utils.closeConnection();

        return true;
    }
}
