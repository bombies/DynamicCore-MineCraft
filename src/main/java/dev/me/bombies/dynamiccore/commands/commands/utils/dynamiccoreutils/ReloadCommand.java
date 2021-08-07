package dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand implements IDynamicCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the config file for the plugin";
    }

    @Override
    public String getSyntax() {
        return "/dyncore reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.isOp() || !player.hasPermission("dc.reload")) {
            player.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
            return;
        }

        DynamicCore.getPlugin(DynamicCore.class).reloadConfig();
        player.sendMessage(ChatColor.GREEN + "Successfully reloaded the config file!");
    }
}
