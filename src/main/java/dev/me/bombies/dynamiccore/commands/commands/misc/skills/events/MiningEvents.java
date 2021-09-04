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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MiningEvents implements Listener {
    private static Timer timer = new Timer();
    private static HashMap<Player, TimerTask> playerTasks = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerXP = new HashMap<>();
    private static HashMap<Player, Integer> tempPlayerLevel = new HashMap<>();
    private BossBar bar = Bukkit.createBossBar(
            "nothing",
            BarColor.RED,
            BarStyle.SOLID
    );

    @EventHandler (priority = EventPriority.NORMAL)
    public synchronized void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!GeneralUtils.isSolidBlock(e.getBlock()))
            return;

        if (!tempPlayerLevel.containsKey(player))
            tempPlayerLevel.put(player, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING));

        if (!tempPlayerXP.containsKey(player))
            tempPlayerXP.put(player, SkillsUtils.ins.getXP(player.getUniqueId(), Tables.SKILLS_MINING));

        int playerLevel = tempPlayerLevel.get(player);
        int currentXP   = tempPlayerXP.get(player);
        int nextXP      = SkillsUtils.ins.getNextXPForLevel(GUIs.SKILLS_MINING, SkillsUtils.ins.getPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING)+1);
        int levelPerXPIncrease  = Config.getInt(Config.SKILLS_MINING_BLOCK_INCREASE_LEVEL);
        float xpIncreaseRate    = Config.getFloat(Config.SKILLS_MINING_BLOCK_INCREASE_RATE);

        ItemStack tool = player.getInventory().getItemInMainHand();

        // Item multiplier logic
        if (GeneralUtils.isOre(e.getBlock()) && !tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
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

        tempPlayerXP.put(player, tempPlayerXP.get(player)+1);

        updateBossBar(player, currentXP, nextXP);

        if (currentXP == nextXP) {
            tempPlayerLevel.put(player, tempPlayerLevel.get(player)+1);
            tempPlayerXP.put(player, 0);

            playerLevel = tempPlayerLevel.get(player);

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

    private synchronized void updateBossBar(Player player, int currentXP, int nextXP) {
        if (playerTasks.containsKey(player))
            playerTasks.get(player).cancel();

        int level = tempPlayerLevel.get(player)+1;

        double percentageFull = (currentXP/(double)nextXP);

        bar.setTitle(GeneralUtils.getColoredString(GUIs.SKILLS_MINING + " &4&lSkill &7&o(level "+level+" progress) &8| &c("+Math.round(percentageFull*100)+"%)"));

        bar.setProgress(percentageFull);

        bar.addPlayer(player);
        startNewTask(bar, player);
    }

    private synchronized static void startNewTask(BossBar bar, Player player) {
        taskRunnable(bar, player);
    }

    protected synchronized static void taskRunnable(BossBar bar, Player player) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (bar.getPlayers().contains(player)) {
                    bar.removeAll();
                    playerTasks.remove(player);
                }
            }
        };
        timer.schedule(task, 10000L);
        playerTasks.put(player, task);
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
