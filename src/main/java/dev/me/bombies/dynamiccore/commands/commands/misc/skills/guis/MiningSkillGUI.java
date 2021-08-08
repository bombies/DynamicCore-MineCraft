package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MiningSkillGUI {

    public MiningSkillGUI(Player player) {
        int playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING);
        Inventory roadMap = GUIBuilder.buildRoadMap(
                GUIs.SKILLS_MINING,
                player,
                GUIs.SKILLS_MINING.toString(),
                SkillsGUICommand.getSeriesIndex(playerLevel),
                playerLevel,
                Tables.SKILLS_MINING
        ).build();

        player.openInventory(roadMap);
    }
}
