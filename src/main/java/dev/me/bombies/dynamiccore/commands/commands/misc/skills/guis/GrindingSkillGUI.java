package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GrindingSkillGUI {

    public GrindingSkillGUI(Player player) {
        int playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING);
        Inventory roadMap = GUIBuilder.buildRoadMap(
                GUIs.SKILLS_GRINDING,
                player,
                GUIs.SKILLS_GRINDING.toString(),
                SkillsGUICommand.getSeriesIndex(playerLevel),
                playerLevel,
                Tables.SKILLS_GRINDING
        ).build();

        player.openInventory(roadMap);
    }
}
