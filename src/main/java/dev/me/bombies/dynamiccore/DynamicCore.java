package dev.me.bombies.dynamiccore;

import dev.me.bombies.dynamiccore.commands.commands.misc.DeathTopCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.EnderChestCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.GodModeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.SuicideCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.DeleteHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.HomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.homes.SetHomeCommand;
import dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils.DynamicCoreCommandManager;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.events.ChatFormatEvent;
import dev.me.bombies.dynamiccore.events.DeathCounterEvent;
import dev.me.bombies.dynamiccore.events.PatchEvents;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicCore extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginUtils.registerEvents(this,
                new DeathCounterEvent(),
                new ChatFormatEvent(),
                new PatchEvents()
        );

        getCommand("deathstop").setExecutor(new DeathTopCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("dyncore").setExecutor(new DynamicCoreCommandManager());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("godmode").setExecutor(new GodModeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DeleteHomeCommand());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println(PLUGIN.PREFIX + "DynamicCore ready!");
    }
}
