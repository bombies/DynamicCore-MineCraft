package dev.me.bombies.dynamiccore;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.tr7zw.nbtinjector.NBTInjector;
import dev.me.bombies.dynamiccore.commands.commands.misc.*;
import dev.me.bombies.dynamiccore.commands.commands.misc.bazooka.BazookaCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.bazooka.BazookaEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.commands.EnvoyCommandManager;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoyClickEvent;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoyFlareEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoySpawnEvent;
import dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events.EnvoyTimerEvent;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.DeleteHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.HomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.SetHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.replanttool.ReplantToolEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.replanttool.ReplantToolRecipe;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.commands.SetSkillLevelCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.GrindingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.MiningEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents.SkillGUIEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis.SkillsGUICommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents.MainGUIEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun.SnowBallGunCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun.SnowBallGunEvents;
import dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils.DynamicCoreCommandManager;
import dev.me.bombies.dynamiccore.constants.Tables;
import dev.me.bombies.dynamiccore.events.*;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.logging.Logger;

public final class DynamicCore extends JavaPlugin {
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        logger.info("Checking dependencies");
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            logger.severe("*** HolographicDisplays is not installed or not enabled. ***");
            logger.severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        logger.info("All dependencies available!");

        PluginUtils.registerEvents(this,
                new DeathEvents(),
                new ChatFormatEvent(),
                new PatchEvents(),
                new SnowBallGunEvents(),
                new BazookaEvents(),
                new AnvilViewEvents(),
                new MainGUIEvents(),
                new SkillGUIEvents(),
                new MiningEvents(),
                new GrindingEvents(),
                new FarmingEvents(),
                new BlazeEvents(),
                new ReplantToolEvents(),
                new ConnectEvents(),
                new EnvoyClickEvent(),
                new EnvoyFlareEvents()
        );
        logger.info("Events registered!");

        logger.info("Loading custom recipes!");
        loadRecipes();
        logger.info("Custom recipes loaded!");

        logger.info("Loading commands!");
        getCommand("deathstop").setExecutor(new DeathTopCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("dyncore").setExecutor(new DynamicCoreCommandManager());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("godmode").setExecutor(new GodModeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DeleteHomeCommand());
        getCommand("givesnowballgun").setExecutor(new SnowBallGunCommand());
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("givebazooka").setExecutor(new BazookaCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("invsee").setExecutor(new InventorySeeCommand());
        getCommand("workbench").setExecutor(new WorkBenchCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("skills").setExecutor(new SkillsGUICommand());
        getCommand("setskilllevel").setExecutor(new SetSkillLevelCommand());
        getCommand("envoy").setExecutor(new EnvoyCommandManager());
        logger.info("Commands loaded!");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        logger.info("Updating skills information");
        updateSkillInfo();
        logger.info("Skills information updated!");

        new EnvoyTimerEvent();
        logger.info("Started envoy timers!");

        logger.info("DynamicCore ready!");
    }

    private void loadRecipes() {
        ReplantToolRecipe.loadRecipe();
    }

    @Override
    public void onDisable() {
        unloadRecipes();
        updateSkillInfo();
        SkillsUtils.ins.closeConnection();
        stopEnvoys();
    }

    private void unloadRecipes() {
        ReplantToolRecipe.unloadRecipe();
    }

    private void stopEnvoys() {
        if (EnvoySpawnEvent.isEnvoysSpawned())
            EnvoySpawnEvent.endEvent();
    }

    private void updateSkillInfo() {
        for (Player player : getServer().getOnlinePlayers()) {
            if (MiningEvents.playerHasTempXPInfo(player))
                SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_MINING, MiningEvents.getXPForPlayer(player));
            if (MiningEvents.playerHasTempLevelInfo(player))
                SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_MINING, MiningEvents.getLevelForPlayer(player));

            if (GrindingEvents.playerHasTempXPInfo(player))
                SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_GRINDING, GrindingEvents.getXPForPlayer(player));
            if (GrindingEvents.playerHasTempLevelInfo(player))
                SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_GRINDING, GrindingEvents.getLevelForPlayer(player));

            if (FarmingEvents.playerHasTempXPInfo(player))
                SkillsUtils.ins.setXP(player.getUniqueId(), Tables.SKILLS_FARMING, FarmingEvents.getXPForPlayer(player));
            if (FarmingEvents.playerHasTempLevelInfo(player))
                SkillsUtils.ins.setPlayerLevel(player.getUniqueId(), Tables.SKILLS_FARMING, FarmingEvents.getLevelForPlayer(player));
        }
    }
}
