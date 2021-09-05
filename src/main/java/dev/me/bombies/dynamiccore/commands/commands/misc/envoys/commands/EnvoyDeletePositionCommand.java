package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
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

        if (args.length == 0) {
            player.sendMessage(GeneralUtils.getPrefixedString("You must provide the ID of an envoy position to remove!"));
            return;
        }

        if (!GeneralUtils.stringIsInt(args[1])) {
            player.sendMessage(GeneralUtils.getPrefixedString("ID provided must be an integer!"));
            return;
        }

        int id = Integer.parseInt(args[1]);

        EnvoyConfig config = new EnvoyConfig();
        try {
            Coordinates coords = config.getHashedPositions().get(id);
            config.removePosition(id);
            player.sendMessage(GeneralUtils.getPrefixedString(
                    "Envoy at position &e" + coords.toString() + " &7&o(ID: "+id+") "
                    + "&fhas been removed!"
            ));
        } catch (NullPointerException | IllegalArgumentException e) {
            player.sendMessage(GeneralUtils.getPrefixedString("Invalid ID!"));
        }
    }
}
