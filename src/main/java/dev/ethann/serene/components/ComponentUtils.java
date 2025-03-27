package dev.ethann.serene.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ComponentUtils {
    public static @NotNull Component parseMiniMessage(@NotNull String text) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static @NotNull Component text(@NotNull String text) {
        return Component.text(text).decoration(TextDecoration.ITALIC, false);
    }

    public static @NotNull String stringifyComponent(@NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
