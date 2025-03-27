package dev.ethann.serene.components.lore;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused") @Getter
public class LoreReplacement {
    private final String key;
    private final String value;
    private final int line;

    public LoreReplacement(@NotNull String key, @NotNull String value) {
        this.key = key;
        this.value = value;
        this.line = -1;
    }

    public LoreReplacement(@NotNull String key, @NotNull String value, int line) {
        this.key = key;
        this.value = value;
        this.line = line;
    }
}
