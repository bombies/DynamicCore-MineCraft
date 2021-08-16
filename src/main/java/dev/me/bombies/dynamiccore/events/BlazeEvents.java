package dev.me.bombies.dynamiccore.events;

import dev.me.bombies.dynamiccore.DynamicCore;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Blaze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class BlazeEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlazeDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Blaze blaze))
            return;

        if (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
            e.setCancelled(true); return;
        }

        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            final Vector vec = new Vector();
            blaze.setVelocity(vec);
            new BukkitRunnable() {

                @Override
                public void run() {
                    blaze.setVelocity(vec);
                }
            }.runTaskLater(DynamicCore.getPlugin(DynamicCore.class), 1L);
        }

        blaze.setNoDamageTicks(0);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlazeSpawn(SpawnerSpawnEvent e) {
        if (!(e.getEntity() instanceof Blaze blaze))
            return;

        if (e.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            AttributeInstance speed = blaze.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            speed.setBaseValue(0D);
        }

        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 99999, 300);
        PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1);
        blaze.addPotionEffect(slowness);
        blaze.addPotionEffect(blindness);
        blaze.setTarget(null);
        blaze.setNoDamageTicks(0);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlazeFireBall(EntityTargetLivingEntityEvent e) {
        if (!(e.getEntity() instanceof Blaze blaze))
            return;

        if (!blaze.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL))
            return;

        e.setCancelled(true);
    }
}
