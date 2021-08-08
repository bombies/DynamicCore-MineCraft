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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FarmingEvents implements Listener {
    private static Timer timer = new Timer();
    private static List<TimerTask> tasks = new ArrayList<>();
    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.GREEN,
            BarStyle.SOLID
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!GeneralUtils.isCrop(e.getBlock()))
            return;

        logic(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobKill(EntityDeathEvent e) {
        if (GeneralUtils.isMob(e.getEntityType()))
            return;

        if (e.getEntity().getKiller() == null)
            return;

        Player player = e.getEntity().getKiller();

        logic(player);
    }

    private void updateBossBar(Player player, int currentXP, int nextXP) {
        if (tasks.size() >= 2) {
            cancelTask(tasks.size()-2);
            tasks.remove(tasks.size()-2);
        }

        int level = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_FARMING + " &a&lSkill &7&o(level "+level+" progress) &8| &a("+Math.round(percentageFull*100)+"%)"));

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

    private void logic(Player player) {
        int playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING);
        int currentXP   = SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_FARMING);
        int nextXP      = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_FARMING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING)+1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_FARMING_CROP_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_FARMING_CROP_INCREASE_RATE);

        SkillsUtils.ins.incrementXP(player.getUniqueId(), Tables.SKILLS_FARMING);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            SkillsUtils.ins.levelUp(player.getUniqueId(), Tables.SKILLS_FARMING);
            playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING);

            player.sendTitle(
                    GeneralUtils.getColoredString("&a&lFarming Skill &e| &a↑" + playerLevel),
                    GeneralUtils.getColoredString("&7You have levelled up your farming skill!"),
                    10, 60, 20
            );
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3F, 0.5F);

            if (playerLevel % levelPerXPIncrease == 0)
                player.sendMessage(Config.getPrefix()
                        + GeneralUtils.getColoredString("&fAll &a&lcrop &fdrops have been increased by &a&l"+Math.round(xpIncreaseRate*100)+"%&f!"));
        }
    }
}
