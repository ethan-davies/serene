package dev.ethann.serene.collections;

import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Potions {
    public static final List<PotionEffectType> POSITIVE_EFFECTS = Arrays.asList(
            PotionEffectType.SPEED, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.STRENGTH,
            PotionEffectType.INVISIBILITY, PotionEffectType.JUMP_BOOST, PotionEffectType.NIGHT_VISION,
            PotionEffectType.REGENERATION, PotionEffectType.WATER_BREATHING, PotionEffectType.HEALTH_BOOST,
            PotionEffectType.ABSORPTION, PotionEffectType.SATURATION, PotionEffectType.LUCK, PotionEffectType.CONDUIT_POWER
    );

    public static final List<PotionEffectType> NEGATIVE_EFFECTS = Arrays.asList(
            PotionEffectType.INSTANT_DAMAGE, PotionEffectType.MINING_FATIGUE, PotionEffectType.BAD_OMEN,
            PotionEffectType.BLINDNESS, PotionEffectType.DARKNESS, PotionEffectType.HUNGER,
            PotionEffectType.INFESTED, PotionEffectType.LEVITATION, PotionEffectType.NAUSEA,
            PotionEffectType.POISON, PotionEffectType.SLOWNESS, PotionEffectType.UNLUCK,
            PotionEffectType.WEAKNESS, PotionEffectType.WITHER, PotionEffectType.DARKNESS
    );
}
