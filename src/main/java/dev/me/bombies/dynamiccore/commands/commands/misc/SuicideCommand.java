package dev.me.bombies.dynamiccore.commands.commands.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("This command cannot be executed from the console!");
            return false;
        }

        Player s = (Player) sender;
        s.setHealth(0.0D);
        Bukkit.broadcastMessage(ChatColor.RED + s.getName() + ChatColor.GRAY + " has taken their own life.");

        return true;
    }
}
