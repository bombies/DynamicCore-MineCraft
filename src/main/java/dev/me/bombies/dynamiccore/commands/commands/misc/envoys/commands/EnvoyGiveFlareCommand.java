package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Player;

public class EnvoyGiveFlareCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "giveflare";
    }

    @Override
    public String getDescription() {
        return "Give a player an envoy flare";
    }

    @Override
    public String getSyntax() {
        return "/envoy giveflare <player> [amount]";
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
