package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EnvoyCommandManager implements CommandExecutor {
    @Getter
    private List<IDynamicCommand> commands = new ArrayList<>();

    public EnvoyCommandManager() {
        commands.add(new EnvoyAdminHelpCommand());
        commands.add(new EnvoyStartCommand());
        commands.add(new EnvoyStopCommand());
        commands.add(new EnvoySetPositionCommand());
        commands.add(new EnvoyDeletePositionCommand());
        commands.add(new EnvoyListPositionsCommand());
        commands.add(new EnvoyFlareGiveCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            DynamicCore.logger.info("This command cannot be executed from the console!");
            return true;
        }

        if (strings.length == 0) {
            // TODO Envoy Time left
            p.sendMessage(GeneralUtils.getPrefixedString("&fThere is &e{time} &funtil the next envoy."));
            return true;
        }

        boolean cmdFound = false;
        for (IDynamicCommand cmd : commands)
            if (cmd.getName().equalsIgnoreCase(strings[0])) {
                cmd.perform(p, strings); cmdFound = true; break;
            }

        if (!cmdFound)
            p.sendMessage(GeneralUtils.getPrefixedString("&fInvalid subcommand!"));

        return true;
    }
}
