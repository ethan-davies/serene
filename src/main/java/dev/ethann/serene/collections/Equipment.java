package dev.ethann.serene.collections;

import org.bukkit.Material;

import java.util.EnumSet;

@SuppressWarnings("unused")
public class Equipment {
    public static final EnumSet<Material> HELMETS = EnumSet.of(
            Material.NETHERITE_HELMET, Material.DIAMOND_HELMET, Material.GOLDEN_HELMET,
            Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.LEATHER_HELMET, Material.TURTLE_HELMET
    );

    public static final EnumSet<Material> CHESTPLATES = EnumSet.of(
            Material.NETHERITE_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
            Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.ELYTRA
    );

    public static final EnumSet<Material> LEGGINGS = EnumSet.of(
            Material.NETHERITE_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLDEN_LEGGINGS,
            Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.LEATHER_LEGGINGS
    );

    public static final EnumSet<Material> BOOTS = EnumSet.of(
            Material.NETHERITE_BOOTS, Material.DIAMOND_BOOTS, Material.GOLDEN_BOOTS,
            Material.IRON_BOOTS, Material.CHAINMAIL_BOOTS, Material.LEATHER_BOOTS
    );

    public static final EnumSet<Material> SWORDS = EnumSet.of(
            Material.NETHERITE_SWORD, Material.DIAMOND_SWORD, Material.GOLDEN_SWORD,
            Material.IRON_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD
    );

    public static final EnumSet<Material> AXES = EnumSet.of(
            Material.NETHERITE_AXE, Material.DIAMOND_AXE, Material.GOLDEN_AXE,
            Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE
    );

    public static final EnumSet<Material> PICKAXES = EnumSet.of(
            Material.NETHERITE_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE
    );

    public static final EnumSet<Material> SHOVELS = EnumSet.of(
            Material.NETHERITE_SHOVEL, Material.DIAMOND_SHOVEL, Material.GOLDEN_SHOVEL,
            Material.IRON_SHOVEL, Material.STONE_SHOVEL, Material.WOODEN_SHOVEL
    );
}
