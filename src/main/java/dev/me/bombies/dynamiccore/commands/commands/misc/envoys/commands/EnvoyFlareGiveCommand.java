package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands;

import dev.me.bombies.dynamiccore.commands.commands.IDynamicCommand;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.guibuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EnvoyFlareGiveCommand implements IDynamicCommand {
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
        return "/envoy giveflare <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!GeneralUtils.hasPerms(player, Permissions.ADMIN)) {
            player.sendMessage(Config.getNoPermissionMessage());
            return;
        }

        if (args.length == 1) {
            player.sendMessage(GeneralUtils.getPrefixedString("You must provide a user to give the envoy flare to!"));
            return;
        }

        Player target = Bukkit.getPlayerExact(args[1]);

        if (target == null) {
            player.sendMessage(GeneralUtils.getPrefixedString("Player with name '"+args[1]+"' doesn't exist or isn't online!"));
            return;
        }

        ItemBuilder flare = new ItemBuilder(Material.BEACON, Config.getColouredString(Config.ENVOY_FLARE_NAME));
        flare.setGlowing(true);
        flare.setLore(Config.getLore(Config.ENVOY_FLARE_LORE));
        flare.addBooleanNBT(NBTTags.ENVOY_FLARE, true);
        target.getInventory().addItem(flare.build());
        target.sendMessage(GeneralUtils.getPrefixedString("You've been given an " + Config.getColouredString(Config.ENVOY_FLARE_NAME) + "&f!"));
        player.sendMessage(GeneralUtils.getPrefixedString("Successfully given &a"+args[1]+"&f an envoy flare!"));
    }
}
