package dev.me.bombies.dynamiccore.commands.commands.misc.skills.commands;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.Permissions;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSkillLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!GeneralUtils.hasPerms(sender, Permissions.SKILLS_SET_LEVEL)) {
            sender.sendMessage(Config.getNoPermissionMessage());
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must provide a user to set the skill level of");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player with name \""+args[0]+"\" doesn't exist!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "You must provide either mining, grinding or farming as a skill");
            return true;
        }

        switch (args[1].toLowerCase()) {
            case "farming", "grinding", "mining" -> {}
            default -> {
                sender.sendMessage(ChatColor.RED + "You must provide either mining, grinding or farming as a skill");
                return true;
            }
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "You must provide a level to set the player to");
            return true;
        }

        if (!GeneralUtils.stringIsInt(args[2])) {
            sender.sendMessage(ChatColor.RED + "Level provided must be a valid integer!");
            return false;
        }

        String skill    = args[1];
        int level       = Integer.parseInt(args[2]);

        if (level < 0) {
            sender.sendMessage(ChatColor.RED + "Level provided must be greater or equal to 0!");
            return false;
        }

        switch(skill) {
            case "farming" -> {
                SkillsUtils.ins.setPlayerLevel(target.getUniqueId(), Tables.SKILLS_FARMING, level);
                SkillsUtils.ins.setXP(target.getUniqueId(), Tables.SKILLS_FARMING, 0);
            }
            case "mining" -> {
                SkillsUtils.ins.setPlayerLevel(target.getUniqueId(), Tables.SKILLS_MINING, level);
                SkillsUtils.ins.setXP(target.getUniqueId(), Tables.SKILLS_MINING, 0);
            }
            case "grinding" -> {
                SkillsUtils.ins.setPlayerLevel(target.getUniqueId(), Tables.SKILLS_GRINDING, level);
                SkillsUtils.ins.setXP(target.getUniqueId(), Tables.SKILLS_GRINDING, 0);
            }
        }

        sender.sendMessage(Config.getPrefix()
                + GeneralUtils.getColoredString("&fYou have set &a&l"+ target.getName()+"'s &f"+ skill +
                " level to &alevel "+ level));

        target.sendMessage(Config.getPrefix() +
                GeneralUtils.getColoredString("&fYour &a&l"+ skill + " &fskill has been set to" +
                        " &alevel "+ level +"&f!"));
        return true;
    }
}
