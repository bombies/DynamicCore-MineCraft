package dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DynamicCoreCommandManager implements CommandExecutor {

    @Getter
    private List<IDynamicCommand> commands = new ArrayList<>();

    public DynamicCoreCommandManager() {
        commands.add(new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!GeneralUtils.hasPerms(p, Permissions.ADMIN)) {
                p.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
                return true;
            }

            if (args.length == 0) {
                p.sendMessage("Help menu");
                p.sendMessage("/dyncore reload");
                return true;
            }

            for (IDynamicCommand cmd : getCommands())
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    cmd.perform(p, args); break;
                }
        }

        return true;
    }
}
