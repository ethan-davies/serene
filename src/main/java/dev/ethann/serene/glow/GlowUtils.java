package dev.ethann.serene.glow;

import dev.ethann.serene.Serene;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@SuppressWarnings({"unused", "deprecation"})
public final class GlowUtils {
    public static void setGlobalGlowing(@NotNull Entity target, @NotNull ChatColor color) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingEntities().setGlowing(target, p, color);
        }
    }

    public static void setPerPlayerGlowing(@NotNull Entity target, @NotNull ChatColor color, @NotNull Collection<Player> receivers) throws ReflectiveOperationException {
        for (Player p : receivers) {
            Serene.getGlowingEntities().setGlowing(target, p, color);
        }
    }

    public static void clearGlowing(@NotNull Entity target) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingEntities().unsetGlowing(target, p);
        }
    }

    public static void perPlayerGlowingClear(@NotNull Entity target, @NotNull Collection<Player> receivers) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingEntities().unsetGlowing(target, p);
        }
    }

    public static void setGlobalBlockGlowing(@NotNull Block target, @NotNull ChatColor color) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingBlocks().setGlowing(target, p, color);
        }
    }

    public static void setPerPlayerBlockGlowing(@NotNull Block target, @NotNull ChatColor color, @NotNull Collection<Player> receivers) throws ReflectiveOperationException {
        for (Player p : receivers) {
            Serene.getGlowingBlocks().setGlowing(target, p, color);
        }
    }


    public static void clearBlockGlowing(@NotNull Block target) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingBlocks().unsetGlowing(target, p);
        }
    }

    public static void perPlayerBlockGlowingClear(@NotNull Block target, @NotNull Collection<Player> receivers) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Serene.getGlowingBlocks().unsetGlowing(target, p);
        }
    }
}