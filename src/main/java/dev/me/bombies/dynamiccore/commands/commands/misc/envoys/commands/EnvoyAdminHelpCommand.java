package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoyAdminHelpCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Admin help menu";
    }

    @Override
    public String getSyntax() {
        return "/envoy help";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        player.sendMessage(GeneralUtils.getColoredString("&8&m-------------&r &6Envoy Admin Help &8&m--------------"));
        for (IDynamicCommand cmd : new EnvoyCommandManager().getCommands())
            player.sendMessage(GeneralUtils.getColoredString("&e| &6" + cmd.getSyntax() + " &7(" + cmd.getDescription() + ")"));
        player.sendMessage(GeneralUtils.getColoredString("&8&m------------------------------------------"));
    }
}
