package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;
import dev.me.bombies.dynamiccore.utils.plugin.Coordinates;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

        if (positions.isEmpty()) {
            player.sendMessage(ChatColor.RED + "There are no locations set for envoys!");
            return;
        }

        positions.forEach((key, value) -> {
            TextComponent positionMsg = new TextComponent(key + " - " + value.toString());
            positionMsg.setColor(ChatColor.GREEN);
            positionMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Click here to teleport to this location!")
                            .color(ChatColor.GREEN)
                            .create()));
            positionMsg.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tp " + value.getX() + " " + value.getY() + " " + value.getZ()
                    ));
            player.spigot().sendMessage(positionMsg);
        });
    }
}
