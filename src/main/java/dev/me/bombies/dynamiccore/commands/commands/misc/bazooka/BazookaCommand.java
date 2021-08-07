package dev.me.bombies.dynamiccore.commands.commands.misc.bazooka;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BazookaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!GeneralUtils.hasPerms(sender, Permissions.ADMIN)) {
            sender.sendMessage(Config.getColouredString(Config.NO_PERMISSION));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must provide a player to give the bazooka gun to!");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage("There was no user with the name '"+args[0]+"' found!");
            return true;
        }

        ItemStack bazooka = new ItemStack(Material.NETHERITE_HOE);

        NBTItem bazookaNBT = new NBTItem(bazooka);
        bazookaNBT.setBoolean(NBTTags.BAZOOKA.toString(), true);
        bazooka = bazookaNBT.getItem();

        ItemMeta bazookaMeta = bazooka.getItemMeta();
        final String gunName = Config.getColouredString(Config.BAZOOKA_NAME);


        bazookaMeta.setDisplayName(gunName);
        bazookaMeta.setLore(Config.getLore(Config.BAZOOKA_LORE));
        bazookaMeta.setUnbreakable(true);

        bazooka.setItemMeta(bazookaMeta);
        bazooka.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        target.getInventory().addItem(bazooka);
        target.sendMessage(ChatColor.GREEN + "You've received a " + gunName);
        sender.sendMessage(ChatColor.GREEN + "You've given "+target.getName()+" a bazooka!");

        return true;
    }
}
