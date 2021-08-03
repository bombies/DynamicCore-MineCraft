package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormatEvent implements Listener {

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        e.setFormat(GeneralUtils.formatString(PluginUtils.getStringFromConfig(CONFIG.CHAT_FORMAT), e.getPlayer(), e.getMessage()));
    }
}
