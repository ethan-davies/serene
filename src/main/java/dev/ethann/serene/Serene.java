package dev.ethann.serene;

import dev.ethann.serene.item.restrictions.RestrictionListeners;
import fr.skytasul.glowingentities.GlowingBlocks;
import fr.skytasul.glowingentities.GlowingEntities;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import lombok.Getter;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class Serene {
    @Getter private static JavaPlugin instance;

    @Getter private static GlowingEntities glowingEntities;
    @Getter private static GlowingBlocks glowingBlocks;

    public static void init(JavaPlugin plugin) {
        instance = plugin;

        glowingEntities = new GlowingEntities(plugin);
        glowingBlocks = new GlowingBlocks(plugin);

        Bukkit.getPluginManager().registerEvents(new RestrictionListeners(), plugin);
    }

    public static @NotNull Logger logger() {
        return instance.getLogger();
    }
}
