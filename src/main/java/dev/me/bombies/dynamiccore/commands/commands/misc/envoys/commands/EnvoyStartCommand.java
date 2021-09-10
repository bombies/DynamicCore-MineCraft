package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoySpawnEvent;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnvoyStartCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Force start the envoy";
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

        new EnvoySpawnEvent().spawnEnvoys(true);
        Bukkit.broadcastMessage(GeneralUtils.getPrefixedString("&lEnvoys have been spawned!"));
    }
}
