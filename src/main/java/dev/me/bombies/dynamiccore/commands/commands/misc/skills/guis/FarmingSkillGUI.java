package dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.guibuilder.GUIBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FarmingSkillGUI {

    public FarmingSkillGUI(Player player) {
        int playerLevel = FarmingEvents.getLevelForPlayer(player);
        Inventory roadMap = GUIBuilder.buildRoadMap(
                GUIs.SKILLS_FARMING,
                player,
                GUIs.SKILLS_FARMING.toString(),
                SkillsGUICommand.getSeriesIndex(playerLevel),
                playerLevel,
                Tables.SKILLS_FARMING
        ).build();

        player.openInventory(roadMap);
    }
}
