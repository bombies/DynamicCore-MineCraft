package dev.me.bombies.dynamiccore.commands.commands.misc.bazooka;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import dev.me.bombies.dynamiccore.commands.commands.CooldownManager;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.constants.NBTTags;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
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
                    NBTEntity entity = new NBTEntity(bullet);
                    entity.setBoolean(NBTTags.BAZOOKA_BULLET.toString(), true);
                    entity.setString(NBTTags.BAZOOKA_SHOOTER.toString(), p.getUniqueId().toString());
                    bullet.setYield(Config.getFloat(Config.BAZOOKA_DMG_RADIUS));
                    CooldownManager.ins.setCooldown(p.getUniqueId(), System.currentTimeMillis());
                }
            } else {
                int newTimeLeft = (int) (CooldownManager.BAZOOKA_COOLDOWN - TimeUnit.MILLISECONDS.toSeconds(timeLeft));
                e.getPlayer().sendMessage(Config.getPrefix() + GeneralUtils.formatString(Config.getString(Config.BAZOOKA_COOLDOWN_MSG), newTimeLeft));
            }
        }
    }

    @EventHandler
    public void shooterDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player))
            return;

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
            return;

        if (!(e.getEntity().getLastDamageCause() instanceof Fireball fireball))
            return;

        NBTEntity fireballNBT = new NBTEntity(fireball);
        if (!fireballNBT.hasKey(NBTTags.BAZOOKA_BULLET.toString()))
            return;

        if (!fireballNBT.getBoolean(NBTTags.BAZOOKA_BULLET.toString()))
            return;

        if (!fireballNBT.getString(NBTTags.BAZOOKA_SHOOTER.toString()).equals(player.getUniqueId().toString()))
           return;

        e.setCancelled(true);
    }
}
