package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoySetPositionCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "setpos";
    }

    @Override
    public String getDescription() {
        return "Set a spawn location for the envoy";
    }

    @Override
    public String getSyntax() {
        return "/envoy setpos";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        // TODO Envoy set position logic
    }
}
