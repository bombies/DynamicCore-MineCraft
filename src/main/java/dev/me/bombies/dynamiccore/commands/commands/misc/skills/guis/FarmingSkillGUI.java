package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FarmingSkillGUI {

    public FarmingSkillGUI(Player player) {
        Inventory roadMap = GUIBuilder.buildRoadMap(
                GUIs.SKILLS_FARMING,
                player,
                GUIs.SKILLS_FARMING.toString(),
                0,
                SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING),
                Tables.SKILLS_FARMING
        ).build();

        player.openInventory(roadMap);
    }
}
