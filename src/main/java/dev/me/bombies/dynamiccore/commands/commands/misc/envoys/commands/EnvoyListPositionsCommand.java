package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import org.bukkit.entity.Player;

import java.util.HashMap;

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
        return "/envoy listpos";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        EnvoyConfig config = new EnvoyConfig();
        HashMap<Integer, Coordinates> positions = config.getHashedPositions();

        positions.forEach((key, value) ->
                player.sendMessage(GeneralUtils.getColoredString("&a" + key + " - " + value.toString()))
        );
    }
}
