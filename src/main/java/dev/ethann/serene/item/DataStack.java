package dev.ethann.serene.item;

import dev.ethann.serene.item.restrictions.Restrictions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class DataStack {
    private final ItemStack item;

    public DataStack(@NotNull ItemStack item) {
        this.item = item;
    }

    public DataStack(@NotNull Material material) {
        this.item = new ItemStack(material);
    }

    public DataStack(@NotNull Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public @NotNull ItemStack getStack() {
        return item;
    }

    public void addRestriction(@NotNull Restrictions restriction) {
        addData(
                restriction.getNamespacedKey(),
                PersistentDataType.INTEGER,
                restriction.ordinal()
        );
    }

    public void removeRestriction(@NotNull Restrictions restriction) {
        removeData(restriction.getNamespacedKey());
    }

    public boolean hasRestriction(@NotNull Restrictions restriction) {
        return hasData(
                restriction.getNamespacedKey(),
                PersistentDataType.INTEGER
        );
    }

    public <T, Z> void addData(
            @NotNull NamespacedKey key,
            @NotNull PersistentDataType<T, Z> type,
            Z value
    ) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(key, type, value);
            item.setItemMeta(meta);
        }
    }

    @Nullable
    public <T, Z> Z getData(
            @NotNull NamespacedKey key,
            @NotNull PersistentDataType<T, Z> type
    ) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(key, type)) {
                return container.get(key, type);
            }
        }
        return null;
    }

    public <T, Z> boolean hasData(
            @NotNull NamespacedKey key,
            @NotNull PersistentDataType<T, Z> type
    ) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            return container.has(key, type);
        }
        return false;
    }

    public void removeData(@NotNull NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.remove(key);
            item.setItemMeta(meta);
        }
    }
}