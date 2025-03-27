package dev.ethann.serene.components.lore;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LoreParser {
    private final List<String> lore;

    @Setter
    private List<LoreReplacement> replacements;

    public LoreParser(List<String> lore) {
        this.lore = lore;
    }

    public LoreParser(List<String> lore, List<LoreReplacement> replacements) {
        this.lore = lore;
        this.replacements = replacements;
    }

    public LoreParser(List<String> lore, LoreReplacement... replacements) {
        this.lore = lore;
        this.replacements = List.of(replacements);
    }

    public void addReplacement(LoreReplacement replacement) {
        this.replacements.add(replacement);
    }

    public @NotNull List<Component> parse() {
        List<Component> parsedLore = new ArrayList<>();
        List<LoreReplacement> effectiveReplacements = replacements != null ? replacements : List.of();

        for (int lineNumber = 0; lineNumber < lore.size(); lineNumber++) {
            String currentLine = lore.get(lineNumber);

            for (LoreReplacement replacement : effectiveReplacements) {
                if (replacement.getLine() == -1 || replacement.getLine() == lineNumber) {
                    currentLine = currentLine.replace(
                            replacement.getKey(),
                            replacement.getValue()
                    );
                }
            }

            parsedLore.add(Component.text(currentLine));
        }

        return parsedLore;
    }
}