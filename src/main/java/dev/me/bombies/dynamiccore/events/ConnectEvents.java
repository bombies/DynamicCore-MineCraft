package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.GrindingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.MiningEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Tables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectEvents implements Listener {

    @EventHandler
    public synchronized void onPlayerDisconnect(PlayerQuitEvent e) {
        checks(e);
    }

    private synchronized void checks(PlayerEvent e) {
        Player player = e.getPlayer();
        if (MiningEvents.playerHasTempXPInfo(player)) {
            SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING, MiningEvents.getLevelForPlayer(player));
            SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_MINING, MiningEvents.getXPForPlayer(player));
            MiningEvents.removeXPInfoForPlayer(player);
            MiningEvents.removeLevelInfoForPlayer(player);
        }

        if (GrindingEvents.playerHasTempXPInfo(player)) {
            SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING, GrindingEvents.getLevelForPlayer(player));
            SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_GRINDING, GrindingEvents.getXPForPlayer(player));
            GrindingEvents.removeXPInfoForPlayer(player);
            GrindingEvents.removeLevelInfoForPlayer(player);
        }

        if (FarmingEvents.playerHasTempXPInfo(player)) {
            SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING, FarmingEvents.getLevelForPlayer(player));
            SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_FARMING, FarmingEvents.getXPForPlayer(player));
            FarmingEvents.removeXPInfoForPlayer(player);
            FarmingEvents.removeLevelInfoForPlayer(player);
        }
    }
}
