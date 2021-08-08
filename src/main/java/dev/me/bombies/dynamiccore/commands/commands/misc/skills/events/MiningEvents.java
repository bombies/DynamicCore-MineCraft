package dev.me.bombies.dynamiccore.commands.commands.misc.skills.events;

import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.GUIs;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MiningEvents implements Listener {
    private static Timer timer = new Timer();
    private static List<TimerTask> tasks = new ArrayList<>();
    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.RED,
            BarStyle.SOLID
    );

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!GeneralUtils.isSolidBlock(e.getBlock()))
            return;


        int playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING);
        int currentXP   = SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_MINING);
        int nextXP      = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_MINING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING)+1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_MINING_BLOCK_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_MINING_BLOCK_INCREASE_RATE);

        if (GeneralUtils.isOre(e.getBlock())) {
            if (playerLevel >= 10) {
                if (!e.isDropItems())
                    return;

                e.setDropItems(false);

                Collection<ItemStack> drops = e.getBlock().getDrops();
                int timesToDrop = (int) (drops.size() * (xpIncreaseRate * (playerLevel/levelPerXPIncrease)))+1;

                for (int i = 0; i < timesToDrop; i++)
                    for (ItemStack drop : drops)
                        e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
            }
        }

        SkillsUtils.ins.incrementXP(player.getUniqueId(), Tables.SKILLS_MINING);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            SkillsUtils.ins.levelUp(player.getUniqueId(), Tables.SKILLS_MINING);
            playerLevel = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING);

            player.sendTitle(
                    GeneralUtils.getColoredString("&4&lMining Skill &e| &c↑" + playerLevel),
                    GeneralUtils.getColoredString("&7You have levelled up your mining skill!"),
                    10, 60, 20
            );
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3F, 0.5F);

            if (playerLevel % levelPerXPIncrease == 0)
                player.sendMessage(Config.getPrefix()
                        + GeneralUtils.getColoredString("&fAll &b&lore &fdrops have been increased by &a&l"+Math.round(xpIncreaseRate*100)+"%&f!"));
        }
    }

    private void updateBossBar(Player player, int currentXP, int nextXP) {
        if (tasks.size() >= 2) {
            cancelTask(tasks.size()-2);
            tasks.remove(tasks.size()-2);
        }

        int level = SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_MINING + " &4&lSkill &7&o(level "+level+" progress) &8| &c("+Math.round(percentageFull*100)+"%)"));

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
