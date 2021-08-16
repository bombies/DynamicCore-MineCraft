package dev.me.bombies.dynamiccore.commands.commands.misc.replanttool;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.guibuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ReplantToolRecipe {
    private static ItemBuilder replantToolBuilder;
    private static ItemStack replantTool;
    private static ShapedRecipe replantToolRecipe;

    public static void loadRecipe() {
        replantToolBuilder = new ItemBuilder(Material.DIAMOND_HOE);
        replantToolBuilder.setName(Config.getColouredString(Config.REPLANT_TOOL_NAME));
        replantToolBuilder.setLore(Config.getLore(Config.REPLANT_TOOL_LORE));
        replantToolBuilder.addEnchant(Enchantment.DURABILITY, 10);
        replantToolBuilder.addBooleanNBT(NBTTags.REPLANT_TOOL, true);
        replantTool = replantToolBuilder.hideAttributes().build();
        replantToolRecipe = new ShapedRecipe(NamespacedKey.minecraft(NBTTags.REPLANT_TOOL.toString()), replantTool);

        replantToolRecipe.shape("*%%", " / ", " / ");
        replantToolRecipe.setIngredient('*', Material.GOLDEN_CARROT);
        replantToolRecipe.setIngredient('%', Material.DIAMOND);
        replantToolRecipe.setIngredient('/', Material.STICK);
        DynamicCore.getPlugin(DynamicCore.class).getServer().addRecipe(replantToolRecipe);
    }

    public static void unloadRecipe() {
        DynamicCore.getPlugin(DynamicCore.class).getServer().removeRecipe(replantToolRecipe.getKey());
    }
}
