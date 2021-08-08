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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GrindingEvents implements Listener {
    private static Timer timer = new Timer();
    private static List<TimerTask> tasks = new ArrayList<>();
    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.YELLOW,
            BarStyle.SOLID
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobKill(EntityDeathEvent e) {
        if (!GeneralUtils.isMob(e.getEntityType()))
            return;

        if (e.getEntity().getKiller() == null)
            return;

        Player player = e.getEntity().getKiller();

        int playerLevel     = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING);
        int currentXP       = SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_GRINDING);
        int nextXP          = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_GRINDING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING) + 1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_GRINDING_XP_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_GRINDING_XP_INCREASE_RATE);

        e.setDroppedExp(e.getDroppedExp() + (e.getDroppedExp() * Math.round((xpIncreaseRate * (playerLevel/levelPerXPIncrease)))));

        SkillsUtils.ins.incrementXP(player.getUniqueId(), Tables.SKILLS_GRINDING);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            SkillsUtils.ins.levelUp(player.getUniqueId(), Tables.SKILLS_GRINDING);
            playerLevel     = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING);
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

    private void updateBossBar(Player player, int currentXP, int nextXP) {
        if (tasks.size() >= 2) {
            cancelTask(tasks.size()-2);
            tasks.remove(tasks.size()-2);
        }

        int level = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_GRINDING + " &6&lSkill &7&o(level "+level+" progress) &8| &e("+Math.round(percentageFull*100)+"%)"));

        bar.setProgress(percentageFull);

        bar.addPlayer(player);
        startNewTask(bar, player);
    }

    private static void startNewTask(BossBar bar, Player player) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (bar.getPlayers().contains(player))
                    bar.removePlayer(player);
            }
        };
        timer.schedule(task, 10000L);
        tasks.add(task);
    }

    private static void cancelTask(int index) {
        tasks.get(index).cancel();
    }
}
