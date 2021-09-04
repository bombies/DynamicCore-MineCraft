package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoyDeletePositionCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "delpos";
    }

    @Override
    public String getDescription() {
        return "Delete a specific envoy spawn location";
    }

    @Override
    public String getSyntax() {
        return "/envoy delpos <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        // TODO Envoy delete position logic
    }
}
