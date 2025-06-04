// Wrapper class for ItemStack, allowing for extension of an ItemStack.
// This class contains all methods and properties of an ItemStack, except for the
// deprecated methods and internal methods used by the Paper API.

package dev.ethann.serene.wrapper;

import io.papermc.paper.datacomponent.DataComponentBuilder;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.inventory.tooltip.TooltipContext;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.papermc.paper.persistence.PersistentDataViewHolder;
import io.papermc.paper.registry.set.RegistryKeySet;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Material;
import org.bukkit.UndefinedNullability;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Obsolete;

@SuppressWarnings("unused")
public class Item implements Cloneable, ConfigurationSerializable, HoverEventSource<HoverEvent.ShowItem>, Translatable, PersistentDataViewHolder {

    protected final ItemStack itemStack;

    public Item(@NotNull Material type) {
        this.itemStack = new ItemStack(type);
    }

    public Item(@NotNull Material type, int amount) {
        this.itemStack = new ItemStack(type, amount);
    }

    @Obsolete(since = "1.21")
    public Item(@NotNull ItemStack stack) throws IllegalArgumentException {
        this.itemStack = stack.clone();
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Item of(@NotNull Material type) {
        return new Item(ItemStack.of(type));
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Item of(@NotNull Material type, int amount) {
        return new Item(ItemStack.of(type, amount));
    }

    public static @NotNull Item empty() {
        return new Item(ItemStack.empty());
    }

    public static @NotNull Item deserialize(@NotNull Map<String, Object> args) {
        return new Item(ItemStack.deserialize(args));
    }

    public static @NotNull Item deserializeBytes(byte @NotNull [] bytes) {
        return new Item(ItemStack.deserializeBytes(bytes));
    }

    public static byte @NotNull [] serializeItemsAsBytes(@NotNull Collection<Item> items) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Item item : items) {
            stacks.add(item.itemStack);
        }
        return ItemStack.serializeItemsAsBytes(stacks);
    }

    public static byte @NotNull [] serializeItemsAsBytes(@Nullable Item @NotNull [] items) {
        ItemStack[] stacks = Arrays.stream(items)
                .map(item -> item == null ? null : item.itemStack)
                .toArray(ItemStack[]::new);
        return ItemStack.serializeItemsAsBytes(stacks);
    }

    public static @NotNull Item @NotNull [] deserializeItemsFromBytes(byte @NotNull [] bytes) {
        ItemStack[] stacks = ItemStack.deserializeItemsFromBytes(bytes);
        Item[] items = new Item[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            items[i] = new Item(stacks[i]);
        }
        return items;
    }

    public @NotNull PersistentDataContainerView getPersistentDataContainer() {
        return itemStack.getPersistentDataContainer();
    }

    public boolean editPersistentDataContainer(@NotNull Consumer<PersistentDataContainer> consumer) {
        return itemStack.editPersistentDataContainer(consumer);
    }

    public @NotNull Material getType() {
        return itemStack.getType();
    }

    @Contract(value = "_ -> new", pure = true)
    public @NotNull Item withType(@NotNull Material type) {
        return new Item(itemStack.withType(type));
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public void setAmount(int amount) {
        itemStack.setAmount(amount);
    }

    public int getMaxStackSize() {
        return itemStack.getMaxStackSize();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            return itemStack.equals(((Item) obj).itemStack);
        }
        return itemStack.equals(obj);
    }

    public boolean isSimilar(@Nullable Item item) {
        return itemStack.isSimilar(item == null ? null : item.itemStack);
    }

    public @NotNull Item clone() {
        return new Item(itemStack.clone());
    }

    public int hashCode() {
        return itemStack.hashCode();
    }

    public boolean containsEnchantment(@NotNull Enchantment ench) {
        return itemStack.containsEnchantment(ench);
    }

    public int getEnchantmentLevel(@NotNull Enchantment ench) {
        return itemStack.getEnchantmentLevel(ench);
    }

    public @NotNull Map<Enchantment, Integer> getEnchantments() {
        return itemStack.getEnchantments();
    }

    public void addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
    }

    public void addEnchantment(@NotNull Enchantment ench, int level) {
        itemStack.addEnchantment(ench, level);
    }

    public void addUnsafeEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        itemStack.addUnsafeEnchantments(enchantments);
    }

    public void addUnsafeEnchantment(@NotNull Enchantment ench, int level) {
        itemStack.addUnsafeEnchantment(ench, level);
    }

    public int removeEnchantment(@NotNull Enchantment ench) {
        return itemStack.removeEnchantment(ench);
    }

    public void removeEnchantments() {
        itemStack.removeEnchantments();
    }

    public @NotNull Map<String, Object> serialize() {
        return itemStack.serialize();
    }

    public boolean editMeta(@NotNull Consumer<? super ItemMeta> consumer) {
        return itemStack.editMeta(consumer);
    }

    public <M extends ItemMeta> boolean editMeta(@NotNull Class<M> metaClass, @NotNull Consumer<? super M> consumer) {
        return itemStack.editMeta(metaClass, consumer);
    }

    @UndefinedNullability
    public ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
    }

    public boolean hasItemMeta() {
        return itemStack.hasItemMeta();
    }

    public boolean setItemMeta(@Nullable ItemMeta itemMeta) {
        return itemStack.setItemMeta(itemMeta);
    }

    public @NotNull Item enchantWithLevels(int levels, boolean allowTreasure, @NotNull Random random) {
        return new Item(itemStack.enchantWithLevels(levels, allowTreasure, random));
    }

    public @NotNull Item enchantWithLevels(int levels, @NotNull RegistryKeySet<@NotNull Enchantment> keySet, @NotNull Random random) {
        return new Item(itemStack.enchantWithLevels(levels, keySet, random));
    }

    public @NotNull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@NotNull UnaryOperator<HoverEvent.ShowItem> op) {
        return itemStack.asHoverEvent(op);
    }

    public @NotNull Component displayName() {
        return itemStack.displayName();
    }

    public @NotNull Component effectiveName() {
        return itemStack.effectiveName();
    }

    public @NotNull Item ensureServerConversions() {
        return new Item(itemStack.ensureServerConversions());
    }

    public byte @NotNull [] serializeAsBytes() {
        return itemStack.serializeAsBytes();
    }

    public int getMaxItemUseDuration(@NotNull LivingEntity entity) {
        return itemStack.getMaxItemUseDuration(entity);
    }

    public @NotNull Item asOne() {
        return new Item(itemStack.asOne());
    }

    public @NotNull Item asQuantity(int qty) {
        return new Item(itemStack.asQuantity(qty));
    }

    public @NotNull Item add() {
        return new Item(itemStack.add());
    }

    public @NotNull Item add(int qty) {
        return new Item(itemStack.add(qty));
    }

    public @NotNull Item subtract() {
        return new Item(itemStack.subtract());
    }

    public @NotNull Item subtract(int qty) {
        return new Item(itemStack.subtract(qty));
    }

    public @Nullable List<Component> lore() {
        return itemStack.lore();
    }

    public void lore(@Nullable List<? extends Component> lore) {
        itemStack.lore(lore);
    }

    public void addItemFlags(ItemFlag... itemFlags) {
        itemStack.addItemFlags(itemFlags);
    }

    public void removeItemFlags(ItemFlag... itemFlags) {
        itemStack.removeItemFlags(itemFlags);
    }

    public @NotNull Set<ItemFlag> getItemFlags() {
        return itemStack.getItemFlags();
    }

    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        return itemStack.hasItemFlag(flag);
    }

    public @NotNull String translationKey() {
        return itemStack.translationKey();
    }

    public boolean isRepairableBy(@NotNull Item repairMaterial) {
        return itemStack.isRepairableBy(repairMaterial.itemStack);
    }

    public boolean canRepair(@NotNull Item toBeRepaired) {
        return itemStack.canRepair(toBeRepaired.itemStack);
    }

    public @NotNull Item damage(int amount, @NotNull LivingEntity livingEntity) {
        return new Item(itemStack.damage(amount, livingEntity));
    }

    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    public @NotNull @Unmodifiable List<Component> computeTooltipLines(@NotNull TooltipContext tooltipContext, @Nullable Player player) {
        return itemStack.computeTooltipLines(tooltipContext, player);
    }

    @Contract(pure = true)
    @Experimental
    public <T> @Nullable T getData(DataComponentType.@NotNull Valued<T> type) {
        return itemStack.getData(type);
    }

    @Contract(value = "_, !null -> !null", pure = true)
    @Experimental
    public <T> @Nullable T getDataOrDefault(DataComponentType.@NotNull Valued<? extends T> type, @Nullable T fallback) {
        return itemStack.getDataOrDefault(type, fallback);
    }

    @Contract(pure = true)
    @Experimental
    public boolean hasData(@NotNull DataComponentType type) {
        return itemStack.hasData(type);
    }

    @Contract("-> new")
    @Experimental
    public @Unmodifiable Set<@NotNull DataComponentType> getDataTypes() {
        return itemStack.getDataTypes();
    }

    @Experimental
    public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull DataComponentBuilder<T> valueBuilder) {
        itemStack.setData(type, valueBuilder);
    }

    @Experimental
    public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull T value) {
        itemStack.setData(type, value);
    }

    @Experimental
    public void setData(DataComponentType.@NotNull NonValued type) {
        itemStack.setData(type);
    }

    @Experimental
    public void unsetData(@NotNull DataComponentType type) {
        itemStack.unsetData(type);
    }

    @Experimental
    public void resetData(@NotNull DataComponentType type) {
        itemStack.resetData(type);
    }

    @Experimental
    public void copyDataFrom(@NotNull Item source, @NotNull Predicate<@NotNull DataComponentType> filter) {
        itemStack.copyDataFrom(source.itemStack, filter);
    }

    @Experimental
    public boolean isDataOverridden(@NotNull DataComponentType type) {
        return itemStack.isDataOverridden(type);
    }

    @Experimental
    public boolean matchesWithoutData(@NotNull Item item, @NotNull Set<@NotNull DataComponentType> excludeTypes) {
        return itemStack.matchesWithoutData(item.itemStack, excludeTypes);
    }

    @Experimental
    public boolean matchesWithoutData(@NotNull Item item, @NotNull Set<@NotNull DataComponentType> excludeTypes, boolean ignoreCount) {
        return itemStack.matchesWithoutData(item.itemStack, excludeTypes, ignoreCount);
    }

    public @NotNull ItemStack toItemStack() {
        return itemStack.clone();
    }

    @Override
    public String toString() {
        return itemStack.toString();
    }
}