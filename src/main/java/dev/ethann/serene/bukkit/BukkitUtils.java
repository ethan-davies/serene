package dev.ethann.serene.bukkit;

import dev.ethann.serene.Serene;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BukkitUtils {
    public static @NotNull BukkitTask sync(@NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTask(Serene.getInstance(), runnable);
    }

    public static @NotNull BukkitTask syncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(Serene.getInstance(), runnable, delay);
    }

    public static @NotNull BukkitTask syncTimer(long delay, long period, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(Serene.getInstance(), runnable, delay, period);
    }

    public static @NotNull BukkitTask async(@NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(Serene.getInstance(), runnable);
    }

    public static @NotNull BukkitTask asyncLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(Serene.getInstance(), runnable, delay);
    }

    public static @NotNull BukkitTask asyncTimer(long delay, long period, Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(Serene.getInstance(), runnable, delay, period);
    }
}
