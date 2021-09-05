package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoySpawnEvent;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoyStopCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Force stop the envoy";
    }

    @Override
    public String getSyntax() {
        return "/envoy stop";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        if (!EnvoySpawnEvent.isEnvoysSpawned())
            player.sendMessage(GeneralUtils.getPrefixedString("There is no ongoing envoy event!"));
        else {
            EnvoySpawnEvent.endEvent();
            player.sendMessage(GeneralUtils.getPrefixedString("You have ended the envoy event!"));
        }
    }
}
