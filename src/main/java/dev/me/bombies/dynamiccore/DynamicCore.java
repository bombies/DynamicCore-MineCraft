package dev.me.bombies.dynamiccore;

import dev.me.bombies.dynamiccore.constants.PLUGIN;
import dev.me.bombies.dynamiccore.events.DeathCounterEvent;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicCore extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginUtils.registerEvents(this,
                new DeathCounterEvent()
        );
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println(PLUGIN.PREFIX + "DynamicCore ready!");
    }
}
