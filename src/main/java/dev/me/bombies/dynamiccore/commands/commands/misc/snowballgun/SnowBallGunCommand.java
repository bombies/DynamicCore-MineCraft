package dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun;

import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
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

import java.util.ArrayList;
import java.util.List;

public class SnowBallGunCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!GeneralUtils.hasPerms(sender, Permissions.ADMIN)) {
            sender.sendMessage(CONFIG.getColouredString(CONFIG.NO_PERMISSION));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must provide a player to give the gun to");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "There was no player called '"+args[0]+"' found.");
            return true;
        }

        ItemStack snowballGun = new ItemStack(Material.GOLDEN_HOE);

        NBTItem snowBallGunNBT = new NBTItem(snowballGun);
        snowBallGunNBT.setBoolean(NBTTags.SNOWBALL_GUN.toString(), true);
        snowballGun = snowBallGunNBT.getItem();

        ItemMeta snowBallGunMeta = snowballGun.getItemMeta();
        final String gunName = CONFIG.getColouredString(CONFIG.SNOWBALL_GUN_NAME);


        snowBallGunMeta.setDisplayName(gunName);
        snowBallGunMeta.setLore(CONFIG.getLore(CONFIG.SNOWBALL_GUN_LORE));
        snowBallGunMeta.setUnbreakable(true);

        snowballGun.setItemMeta(snowBallGunMeta);
        snowballGun.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        target.getInventory().addItem(snowballGun);
        target.sendMessage(ChatColor.GREEN + "You've received a " + gunName);
        sender.sendMessage(ChatColor.GREEN + "You've given "+target.getName()+" a snowball gun!");
        return true;
    }
}
