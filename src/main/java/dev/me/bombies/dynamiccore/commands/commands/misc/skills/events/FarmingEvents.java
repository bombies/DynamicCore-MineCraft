package dev.me.bombies.dynamiccore.commands.commands.misc.skills.events;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.data.Ageable;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class FarmingEvents implements Listener {
    private static Timer timer = new Timer();
    private static HashMap<Player, TimerTask> playerTasks = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerXP = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerLevel = new HashMap<>();

    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.GREEN,
            BarStyle.SOLID
    );

    @EventHandler(priority = EventPriority.NORMAL)
    public synchronized void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!GeneralUtils.isCrop(e.getBlock()))
            return;

        if (e.getBlock().getBlockData() instanceof Ageable age) {
            age = (Ageable) e.getBlock().getBlockData();
            if (age.getAge() != age.getMaximumAge())
                return;
        }

        logic(player);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public synchronized void onMobKill(EntityDeathEvent e) {
        if (GeneralUtils.isMob(e.getEntityType()))
            return;

        if (e.getEntity().getKiller() == null)
            return;

        Player player = e.getEntity().getKiller();

        logic(player);
    }

    private synchronized void updateBossBar(Player player, int currentXP, int nextXP) {
        if (playerTasks.containsKey(player))
            playerTasks.get(player).cancel();

        int level = getLevelForPlayer(player)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_FARMING + " &a&lSkill &7&o(level "+level+" progress) &8| &a("+Math.round(percentageFull*100)+"%)"));

        bar.setProgress(percentageFull);

        bar.addPlayer(player);
        startNewTask(bar, player);
    }

    private synchronized static void startNewTask(BossBar bar, Player player) {
        MiningEvents.taskRunnable(bar, player, playerTasks, timer);
    }

    private void logic(Player player) {
        if (!tempPlayerLevel.containsKey(player))
            tempPlayerLevel.put(player, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING));

        if (!tempPlayerXP.containsKey(player))
            tempPlayerXP.put(player, SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_FARMING));

        int playerLevel = tempPlayerLevel.get(player);
        int currentXP   = tempPlayerXP.get(player);
        int nextXP      = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_FARMING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING)+1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_FARMING_CROP_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_FARMING_CROP_INCREASE_RATE);

        tempPlayerXP.put(player, tempPlayerXP.get(player)+1);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            tempPlayerLevel.put(player, tempPlayerLevel.get(player)+1);
            tempPlayerXP.put(player, 0);
            playerLevel = tempPlayerLevel.get(player);

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

    public static int getXPForPlayer(Player player) {
        if (!tempPlayerXP.containsKey(player))
            tempPlayerXP.put(player, SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_MINING));
        return tempPlayerXP.get(player);
    }

    public static boolean playerHasTempXPInfo(Player player) {
        return tempPlayerXP.containsKey(player);
    }

    public static void removeXPInfoForPlayer(Player player) {
        tempPlayerXP.remove(player);
    }

    public static int getLevelForPlayer(Player player) {
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
