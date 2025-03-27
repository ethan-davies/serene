package dev.ethann.serene.item;

import dev.ethann.serene.item.restrictions.Restrictions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class DataStack extends ItemStack {
    public DataStack(@NotNull Material material) {
        super(material);
    }

    public DataStack(@NotNull Material material, int amount) {
        super(material, amount);
    }

    public void addRestriction(@NotNull Restrictions restriction) {
        this.addData(restriction.getNamespacedKey(), PersistentDataType.INTEGER, restriction.ordinal());
    }

    public void removeRestriction(@NotNull Restrictions restriction) {
        this.removeData(restriction.getNamespacedKey());
    }

    public boolean hasRestriction(@NotNull Restrictions restriction) {
        return this.hasData(restriction.getNamespacedKey(), PersistentDataType.INTEGER);
    }

    public <T, Z> void addData(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, Z value) {
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(key, type, value);
            this.setItemMeta(meta);
        }
    }

    public <T, Z> Z getData(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(key, type)) {
                return container.get(key, type);
            }
        }
        return null;
    }

    public <T, Z> boolean hasData(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            return container.has(key, type);
        }
        return false;
    }

    public void removeData(@NotNull NamespacedKey key) {
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.remove(key);
            this.setItemMeta(meta);
        }
    }

    public @NotNull ItemStack toItemStack() {
        return this;
    }
}