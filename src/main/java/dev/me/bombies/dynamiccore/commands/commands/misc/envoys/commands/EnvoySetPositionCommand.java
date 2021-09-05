package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import org.bukkit.Location;
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

        Location playerLocation = player.getLocation();
        EnvoyConfig config = new EnvoyConfig();
        int id = config.addPosition(playerLocation);
        player.sendMessage(GeneralUtils.getPrefixedString(
                "&fEnvoy position added at &a" +
                playerLocation.getBlockX() + ", " + playerLocation.getBlockY() + ", " + playerLocation.getBlockZ() +
                " &f with id: &e" + id + "&f!"
        ));
    }
}
