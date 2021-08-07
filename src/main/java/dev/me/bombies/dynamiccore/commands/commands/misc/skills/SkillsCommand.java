package dev.me.bombies.dynamiccore.commands.commands.misc.skills;

import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SkillsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("This command cannot be executed from console!");
            return true;
        }

        int invSize = Config.getInt(Config.SKILLS_GUI_SIZE);

        if (invSize % 9 != 0 || invSize <= 0)
            throw new IllegalArgumentException(invSize + " is an invalid GUI size");

        GUIBuilder builder = new GUIBuilder(player, GUIs.SKILLS_MAIN.toString(), invSize, true);

        Material miningButtonMaterial   = Config.getMaterial(Config.SKILLS_MINING_MATERIAL);
        Material grindingButtonMaterial = Config.getMaterial(Config.SKILLS_GRINDING_MATERIAL);
        Material farmingButtonMaterial  = Config.getMaterial(Config.SKILLS_FARMING_MATERIAL);

        if (miningButtonMaterial == null)
            throw new NullPointerException("Material '"+Config.getString(Config.SKILLS_MINING_MATERIAL)+"' isn't a valid material for the mining button!");
        if (farmingButtonMaterial == null)
            throw new NullPointerException("Material '"+Config.getString(Config.SKILLS_FARMING_MATERIAL)+"' isn't a valid material for the mining button!");
        if (grindingButtonMaterial == null)
            throw new NullPointerException("Material '"+Config.getString(Config.SKILLS_GRINDING_MATERIAL)+"' isn't a valid material for the mining button!");

        List<Integer> slots = Config.getIntList(Config.SKILLS_ITEM_SLOTS);

        if (slots.isEmpty())
            throw new NullPointerException("There were no item slots provided!");
        else if (slots.size() != 3)
            throw new IllegalArgumentException("Too much or not enough item slots were provided!");

        builder.setItem(
                miningButtonMaterial,
                Config.getColouredString(Config.SKILLS_MINING_NAME),
                Config.getLore(Config.SKILLS_MINING_LORE),
                slots.get(0),
                true, true
        );

        builder.setItem(
                grindingButtonMaterial,
                Config.getColouredString(Config.SKILLS_GRINDING_NAME),
                Config.getLore(Config.SKILLS_GRINDING_LORE),
                slots.get(1),
                true, true
        );

        builder.setItem(
                farmingButtonMaterial,
                Config.getColouredString(Config.SKILLS_FARMING_NAME),
                Config.getLore(Config.SKILLS_FARMING_LORE),
                slots.get(2),
                true, true
        );

        player.openInventory(builder.build());

        return true;
    }
}
