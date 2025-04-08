package dev.ethann.serene.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemBuilder {
    private final DataStack item;
    private final ItemMeta meta;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public ItemBuilder(Material material) {
        this(new DataStack(material));
    }

    public ItemBuilder(DataStack item) {
        this.item = Objects.requireNonNull(item, "item");
        this.meta = item.getStack().getItemMeta();

        if (this.meta == null) throw new IllegalArgumentException("The type " + item.getStack().getType() + " doesn't support item meta");
    }

    public ItemBuilder data(int data) {
        return durability((short) data);
    }

    public ItemBuilder durability(short durability) {
        if (!(this.meta instanceof Damageable damageable)) return this;
        int current = item.getStack().getType().getMaxDurability() - damageable.getDamage();
        damageable.setDamage(current - durability);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.item.getStack().setAmount(amount);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        return enchant(enchantment, 1);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        this.meta.getEnchants().keySet().forEach(this.meta::removeEnchant);
        return this;
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.meta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(this.meta)) {
            metaConsumer.accept(metaClass.cast(this.meta));
        }
        return this;
    }

    public ItemBuilder name(String name) {
        return name(MINI_MESSAGE.deserialize(name));
    }

    public ItemBuilder name(Component name) {
        this.meta.displayName(name);
        return this;
    }

    public ItemBuilder lore(Component... components) {
        return lore(Arrays.asList(components));
    }

    public ItemBuilder lore(List<Component> components) {
        this.meta.lore(components);
        return this;
    }

    public ItemBuilder addLore(Component... components) {
        List<Component> lore = this.meta.lore() != null ?
                new ArrayList<>(this.meta.lore()) : new ArrayList<>();
        Collections.addAll(lore, components);
        this.meta.lore(lore);
        return this;
    }

    public ItemBuilder loreMini(String... lines) {
        return loreMini(Arrays.asList(lines));
    }

    public ItemBuilder loreMini(List<String> lines) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) {
            components.add(MINI_MESSAGE.deserialize(line));
        }
        return lore(components);
    }

    public ItemBuilder addLoreMini(String... lines) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) {
            components.add(MINI_MESSAGE.deserialize(line));
        }
        return addLore(components.toArray(new Component[0]));
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder flags() {
        return flags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public ItemBuilder armorColor(Color color) {
        return meta(LeatherArmorMeta.class, m -> m.setColor(color));
    }

    public ItemBuilder customModelData(int data) {
        this.meta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder skull(String base64) {
        if (!(meta instanceof SkullMeta skullMeta)) return this;

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", base64));
        skullMeta.setPlayerProfile(profile);
        return this;
    }

    public ItemBuilder skull(OfflinePlayer player) {
        if (!(meta instanceof SkullMeta skullMeta)) return this;
        skullMeta.setPlayerProfile(player.getPlayerProfile());
        return this;
    }

    public ItemBuilder skull(UUID uuid) {
        if (!(meta instanceof SkullMeta skullMeta)) return this;
        PlayerProfile profile = Bukkit.createProfile(uuid, null);
        skullMeta.setPlayerProfile(profile);
        return this;
    }

    public ItemBuilder replace(String placeholder, String value) {
        Component displayName = meta.displayName();
        if (displayName != null) {
            meta.displayName(displayName.replaceText(b ->
                    b.matchLiteral(placeholder).replacement(value)
            ));
        }

        List<Component> lore = meta.lore();
        if (lore != null) {
            List<Component> newLore = new ArrayList<>();
            for (Component line : lore) {
                newLore.add(line.replaceText(b ->
                        b.matchLiteral(placeholder).replacement(value)
                ));
            }
            meta.lore(newLore);
        }
        return this;
    }

    public DataStack build() {
        this.item.getStack().setItemMeta(this.meta);
        return this.item;
    }

    public ItemStack buildStack() {
        this.item.getStack().setItemMeta(this.meta);
        return this.item.getStack();
    }
}