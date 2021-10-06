package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoySpawnEvent;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import net.md_5.bungee.api.ChatColor;
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

        try {
            new EnvoySpawnEvent().spawnEnvoys(true);
            Bukkit.broadcastMessage(GeneralUtils.getPrefixedString("&lEnvoys have been spawned!"));
        } catch (NullPointerException e) {
            player.sendMessage(ChatColor.RED + e.getMessage());
        }
    }
}
