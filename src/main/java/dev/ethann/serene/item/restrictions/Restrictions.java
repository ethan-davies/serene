package dev.ethann.serene.item.restrictions;

import dev.ethann.serene.Serene;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public enum Restrictions {
    PLAYER_ONLY("player_only"), // Only can be in a player's inventory
    PREVENT_MOVE("prevent_move"),
    PREVENT_DESTROY("prevent_destroy"),
    NO_DROP("no_drop");

    private final String key;

    Restrictions(String key) {
        this.key = key;
    }

    public @NotNull NamespacedKey getNamespacedKey() {
        return new NamespacedKey(Serene.getInstance(), "item_restriction/" + key);
    }
}
