package dev.me.bombies.dynamiccore;

import dev.me.bombies.dynamiccore.commands.commands.misc.DeathTopCommand;
import dev.me.bombies.dynamiccore.commands.commands.misc.SuicideCommand;
import dev.me.bombies.dynamiccore.commands.commands.utils.dynamiccoreutils.DynamicCoreCommandManager;
import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.events.ChatFormatEvent;
import dev.me.bombies.dynamiccore.events.DeathCounterEvent;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicCore extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginUtils.registerEvents(this,
                new DeathCounterEvent(),
                new ChatFormatEvent()
        );

        getCommand("deathstop").setExecutor(new DeathTopCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("dyncore").setExecutor(new DynamicCoreCommandManager());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println(PLUGIN.PREFIX + "DynamicCore ready!");
    }
}
