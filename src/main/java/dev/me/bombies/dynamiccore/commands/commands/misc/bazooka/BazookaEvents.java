package dev.me.bombies.dynamiccore.commands.commands.misc.bazooka;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.commands.commands.CooldownManager;
import dev.me.bombies.dynamiccore.constants.CONFIG;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class BazookaEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onShoot(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            long timeLeft = System.currentTimeMillis() - CooldownManager.ins.getCooldown(e.getPlayer().getUniqueId());
            if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.BAZOOKA_COOLDOWN) {
                ItemStack bazooka = e.getItem();
                NBTItem bazookaNBT = new NBTItem(bazooka);

                if (bazookaNBT.hasKey(NBTTags.BAZOOKA.toString())) {
                    Player p = e.getPlayer();
                    Fireball bullet = p.launchProjectile(Fireball.class);
                    bullet.setYield(CONFIG.getFloat(CONFIG.BAZOOKA_DMG_RADIUS));
                    CooldownManager.ins.setCooldown(p.getUniqueId(), System.currentTimeMillis());
                }
            } else {
                int newTimeLeft = (int) (CooldownManager.BAZOOKA_COOLDOWN - TimeUnit.MILLISECONDS.toSeconds(timeLeft));
                e.getPlayer().sendMessage(CONFIG.getPrefix() + GeneralUtils.formatString(CONFIG.getString(CONFIG.BAZOOKA_COOLDOWN_MSG), newTimeLeft));
            }
        }
    }
}