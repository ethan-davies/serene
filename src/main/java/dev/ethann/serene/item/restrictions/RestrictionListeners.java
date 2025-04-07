package dev.ethann.serene.item.restrictions;

import dev.ethann.serene.bukkit.BukkitUtils;
import dev.ethann.serene.item.DataStack;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RestrictionListeners implements Listener {
    @Getter
    private static final Set<Player> updateInventoryQueue = new HashSet<>();
    @Getter
    private static final Set<UUID> playerSet = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST)
    private void handlePlayerOnly(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) ||
                player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ClickType click = event.getClick();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null ||
                clickedInventory.getType() == InventoryType.PLAYER) {
            return;
        }

        switch (click) {
            case NUMBER_KEY -> {
                ItemStack hotbar = player.getInventory().getItem(event.getHotbarButton());
                if (hotbar != null) {
                    DataStack hotbarItem = new DataStack(hotbar);
                    if (hotbarItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                        event.setCancelled(true);
                    }
                }
            }
            case LEFT, RIGHT -> {
                ItemStack cursor = event.getCursor();
                if (cursor != null) {
                    DataStack cursorItem = new DataStack(cursor);
                    if (cursorItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                        event.setCancelled(true);
                    }
                }
            }
            case SHIFT_LEFT, SHIFT_RIGHT -> {
                ItemStack clicked = event.getCurrentItem();
                if (clicked != null) {
                    DataStack clickedItem = new DataStack(clicked);
                    if (clickedItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                        event.setCancelled(true);
                    }
                }
            }
            case SWAP_OFFHAND -> {
                ItemStack offhand = player.getInventory().getItemInOffHand();
                if (offhand != null) {
                    DataStack offhandItem = new DataStack(offhand);
                    if (offhandItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        ItemStack current = event.getCurrentItem();
        if (current != null) {
            DataStack currentItem = new DataStack(current);
            if (currentItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                event.setCancelled(true);
            }
        }

        ItemStack cursor = event.getCursor();
        if (cursor != null) {
            DataStack cursorItem = new DataStack(cursor);
            if (cursorItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                event.setCancelled(true);
            }
        }

        updateInventoryQueue.add(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void handlePreventMove(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) ||
                player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ClickType click = event.getClick();
        Inventory clickedInventory = event.getClickedInventory();
        Inventory topInventory = player.getOpenInventory().getTopInventory();

        if (clickedInventory != null &&
                clickedInventory.getType() == InventoryType.PLAYER) {
            switch (click) {
                case NUMBER_KEY -> {
                    ItemStack hotbar = player.getInventory().getItem(event.getHotbarButton());
                    if (hotbar != null) {
                        DataStack item = new DataStack(hotbar);
                        if (topInventory.getType() != InventoryType.PLAYER &&
                                item.hasRestriction(Restrictions.PREVENT_MOVE)) {
                            event.setCancelled(true);
                        }
                    }
                }
                case LEFT, RIGHT -> {
                    ItemStack cursor = event.getCursor();
                    if (cursor != null) {
                        DataStack cursorItem = new DataStack(cursor);
                        if (topInventory.getType() != InventoryType.PLAYER &&
                                cursorItem.hasRestriction(Restrictions.PREVENT_MOVE)) {
                            event.setCancelled(true);
                        }
                    }
                }
                case SHIFT_LEFT, SHIFT_RIGHT -> {
                    ItemStack clicked = event.getCurrentItem();
                    if (clicked != null) {
                        DataStack clickedItem = new DataStack(clicked);
                        if (clickedItem.hasRestriction(Restrictions.PREVENT_MOVE)) {
                            event.setCancelled(true);
                        }
                    }
                }
                case SWAP_OFFHAND -> {
                    ItemStack offhand = player.getInventory().getItemInOffHand();
                    if (offhand != null) {
                        DataStack offhandItem = new DataStack(offhand);
                        if (topInventory.getType() != InventoryType.PLAYER &&
                                offhandItem.hasRestriction(Restrictions.PREVENT_MOVE)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }

        updateInventoryQueue.add(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelDrop(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        DataStack stack = new DataStack(dropped);
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE ||
                !stack.hasRestriction(Restrictions.NO_DROP)) {
            return;
        }

        playerSet.add(player.getUniqueId());

        if (player.getInventory().firstEmpty() == -1) {
            player.setItemOnCursor(dropped);
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelDestroy(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Item item)) {
            return;
        }

        ItemStack stackItem = item.getItemStack();
        DataStack stack = new DataStack(stackItem);
        if (stack.hasRestriction(Restrictions.PREVENT_DESTROY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void cancelInteract(PlayerInteractEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (playerSet.contains(uuid)) {
            event.setCancelled(true);
            playerSet.remove(uuid);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void updateInventory(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player &&
                updateInventoryQueue.remove(player)) {
            BukkitUtils.syncLater(5L, player::updateInventory);
        }
    }
}