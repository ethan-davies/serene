package dev.ethann.serene.entity;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

@SuppressWarnings("unused")
public class Damager {
    public static void trueDamage(Entity entity, double damage) {
        if(!(entity instanceof Damageable target)) {
            return;
        }

        double currentHealth = target.getHealth();
        double newHealth = currentHealth - damage;

        if(newHealth <= 0) {
            target.damage(Integer.MAX_VALUE);
            return;
        }

        target.setHealth(newHealth);
    }
}