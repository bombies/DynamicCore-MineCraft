package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoyListPositionsCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "listpos";
    }

    @Override
    public String getDescription() {
        return "List all the spawn locations of envoys";
    }

    @Override
    public String getSyntax() {
        return "/envoy start";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        // TODO Envoy start event logic
    }
}
