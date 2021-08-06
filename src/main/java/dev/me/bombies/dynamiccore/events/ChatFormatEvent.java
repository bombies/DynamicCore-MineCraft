package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.plugin.PluginUtils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatFormatEvent implements Listener {
    private static final String itemFormat = "[item]";

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        String format = GeneralUtils.formatString(PluginUtils.getStringFromConfig(CONFIG.CHAT_FORMAT), e.getPlayer(), e.getMessage());

        if (!format.contains(itemFormat))
            e.setFormat(format);
        else {
            Bukkit.spigot().broadcast(combineTextComponents(
                    new TextComponent(format.substring(0, format.indexOf(itemFormat))),
                    getItemShow(e),
                    new TextComponent(format.substring(format.indexOf(itemFormat) + itemFormat.length()) + ChatColor.RESET))
            );
            e.setCancelled(true);
        }
    }

    private TextComponent getItemShow(PlayerEvent e) {
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getInventory().getItemInMainHand();

        String displayName;

        if (itemInHand.getItemMeta().hasDisplayName())
            displayName = itemInHand.getItemMeta().getDisplayName();
        else displayName = "&b&o" + GeneralUtils.toSnakeCase(itemInHand.getType().name().replaceAll("_", " "));

        TextComponent ret = new TextComponent(ChatColor.translateAlternateColorCodes(
                '&', displayName + " &f&lx" + itemInHand.getAmount() + "&r"
        ));

        StringBuilder itemInfo = new StringBuilder();
        itemInfo.append(displayName).append("\n");

        Map<Enchantment, Integer> enchantments = itemInHand.getEnchantments();

        if (!enchantments.isEmpty())
            for (Enchantment ench : enchantments.keySet())
                itemInfo.append("&7").append(ench.getKey().getKey()).append(" ").append(enchantments.get(ench)).append("\n");

        itemInfo.append("\n");
        List<String> lore = itemInHand.getItemMeta().getLore();

        if (lore != null)
            if (!lore.isEmpty())
                for (String s : lore)
                    itemInfo.append(s).append("\n");

        ret.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new Text(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                itemInfo.toString()
                        )
                )
        ));

        return ret;
    }

    private TextComponent combineTextComponents(TextComponent... components) {
        StringBuilder combinedComponents = new StringBuilder();
        for (TextComponent component : components)
            combinedComponents.append(TextComponent.toPlainText(component)).append(" ");
        return new TextComponent(combinedComponents.toString());
    }
}
