package dev.me.bombies.dynamiccore.commands.commands.misc.skills.events;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class GrindingEvents implements Listener {
    private static Timer timer = new Timer();
    private static HashMap<Player, TimerTask> playerTasks = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerXP = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerLevel = new HashMap<>();
    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.YELLOW,
            BarStyle.SOLID
    );

    @EventHandler(priority = EventPriority.NORMAL)
    public synchronized void onMobKill(EntityDeathEvent e) {
        if (!GeneralUtils.isMob(e.getEntityType()))
            return;

        Player player = null;

        if (e.getEntity().getKiller() == null) {
            if (e.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))
                player = (Player) e.getEntity().getLastDamageCause().getEntity();
        } else player = e.getEntity().getKiller();

        if (player == null) return;

        if (!tempPlayerLevel.containsKey(player))
            tempPlayerLevel.put(player, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING));

        if (!tempPlayerXP.containsKey(player))
            tempPlayerXP.put(player, SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_GRINDING));

        int playerLevel         = tempPlayerLevel.get(player);
        int currentXP           = tempPlayerXP.get(player);
        int nextXP              = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_GRINDING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING) + 1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_GRINDING_XP_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_GRINDING_XP_INCREASE_RATE);

        e.setDroppedExp(e.getDroppedExp() + (e.getDroppedExp() * Math.round((xpIncreaseRate * (playerLevel/levelPerXPIncrease)))));

        tempPlayerXP.put(player, tempPlayerXP.get(player)+1);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            tempPlayerLevel.put(player, tempPlayerLevel.get(player)+1);
            tempPlayerXP.put(player, 0);

            playerLevel     = tempPlayerLevel.get(player);
            player.sendTitle(
                    GeneralUtils.getColoredString("&6&lGrinding Skill &8| &e↑" + playerLevel),
                    GeneralUtils.getColoredString("&7You have levelled up your grinding skill!"),
                    10, 60, 20
            );

            if (playerLevel % levelPerXPIncrease == 0)
                player.sendMessage(Config.getPrefix()
                        + GeneralUtils.getColoredString("&fAll &6&lXP &fdrops have been increased by &a&l"+Math.round(xpIncreaseRate*100)+"%&f!"));

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3F, 0.5F);
        }
    }

    private synchronized void updateBossBar(Player player, int currentXP, int nextXP) {
        if (playerTasks.containsKey(player))
            playerTasks.get(player).cancel();

        int level = tempPlayerLevel.get(player)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_GRINDING + " &6&lSkill &7&o(level "+level+" progress) &8| &e("+Math.round(percentageFull*100)+"%)"));

        bar.setProgress(percentageFull);

        bar.addPlayer(player);
        startNewTask(bar, player);
    }

    private synchronized static void startNewTask(BossBar bar, Player player) {
        MiningEvents.taskRunnable(bar, player);
    }

    public static synchronized int getXPForPlayer(Player player) {
        if (!tempPlayerXP.containsKey(player))
            tempPlayerXP.put(player, SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_MINING));
        return tempPlayerXP.get(player);
    }

    public static synchronized boolean playerHasTempXPInfo(Player player) {
        return tempPlayerXP.containsKey(player);
    }

    public static synchronized void removeXPInfoForPlayer(Player player) {
        tempPlayerXP.remove(player);
    }

    public static synchronized int getLevelForPlayer(Player player) {
        if (!tempPlayerLevel.containsKey(player))
            tempPlayerLevel.put(player, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING));
        return tempPlayerLevel.get(player);
    }

    public static boolean playerHasTempLevelInfo(Player player) {
        return tempPlayerLevel.containsKey(player);
    }

    public static void removeLevelInfoForPlayer(Player player) {
        tempPlayerLevel.remove(player);
    }
}
