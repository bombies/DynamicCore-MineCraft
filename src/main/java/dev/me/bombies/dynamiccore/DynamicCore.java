package dev.me.bombies.dynamiccore;

import dev.me.bombies.dynamiccore.commands.commands.misc.*;
import dev.me.bombies.dynamiccore.commands.commands.misc.bazooka.BazookaCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.bazooka.BazookaEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.DeleteHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.HomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.SetHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.commands.SetSkillLevelCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.FarmingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.GrindingEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.events.MiningEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents.MiningGUIEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guis.SkillsGUICommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.guievents.MainGUIEvents;
import dev.me.bombies.dynamiccore.commands.commands.misc.skills.utils.SkillsUtils;
import dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun.SnowBallGunCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.snowballgun.SnowBallGunEvents;
import dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils.DynamicCoreCommandManager;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.events.AnvilViewEvents;
import dev.me.bombies.dynamiccore.events.ChatFormatEvent;
import dev.me.bombies.dynamiccore.events.DeathEvents;
import dev.me.bombies.dynamiccore.events.PatchEvents;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicCore extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginUtils.registerEvents(this,
                new DeathEvents(),
                new ChatFormatEvent(),
                new PatchEvents(),
                new SnowBallGunEvents(),
                new BazookaEvents(),
                new AnvilViewEvents(),
                new MainGUIEvents(),
                new MiningGUIEvents(),
                new MiningEvents(),
                new GrindingEvents(),
                new FarmingEvents()
        );

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

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println(PLUGIN.PREFIX + "DynamicCore ready!");
    }

    @Override
    public void onDisable() {
        SkillsUtils.ins.closeConnection();
    }
}
