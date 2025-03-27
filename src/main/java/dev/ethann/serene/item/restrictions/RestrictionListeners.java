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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RestrictionListeners implements Listener {
    @Getter private static Set<Player> updateInventoryQueue;
    @Getter private static Set<UUID> playerSet;

    public RestrictionListeners() {
        updateInventoryQueue = new HashSet<>();
        playerSet = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void handlePlayerOnly(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) ||
                player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ClickType click = event.getClick();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null || clickedInventory.getType() == InventoryType.PLAYER) {
            return;
        }

        switch (click) {
            case NUMBER_KEY -> {
                DataStack hotbarItem = (DataStack) player.getInventory().getItem(event.getHotbarButton());
                if (hotbarItem != null && hotbarItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                    event.setCancelled(true);
                }
            }
            case LEFT, RIGHT -> {
                DataStack cursorItem = (DataStack) event.getCursor();
                if (cursorItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                    event.setCancelled(true);
                }
            }
            case SHIFT_LEFT, SHIFT_RIGHT -> {
                DataStack clickedItem = (DataStack) event.getCurrentItem();
                if (clickedItem != null && clickedItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                    event.setCancelled(true);
                }
            }
            case SWAP_OFFHAND -> {
                DataStack offhandItem = (DataStack) player.getInventory().getItemInOffHand();
                if (offhandItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
                    event.setCancelled(true);
                }
            }
        }

        DataStack currentItem = (DataStack) event.getCurrentItem();
        if (currentItem != null && currentItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
            event.setCancelled(true);
        }

        DataStack cursorItem = (DataStack) event.getCursor();
        if (cursorItem.hasRestriction(Restrictions.PLAYER_ONLY)) {
            event.setCancelled(true);
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

        if (clickedInventory != null && clickedInventory.getType() == InventoryType.PLAYER) {
            switch (click) {
                case NUMBER_KEY -> {
                    DataStack item = (DataStack) player.getInventory().getItem(event.getHotbarButton());
                    if (item != null && topInventory.getType() != InventoryType.PLAYER &&
                            item.hasRestriction(Restrictions.PREVENT_MOVE)) {
                        event.setCancelled(true);
                    }
                }
                case LEFT, RIGHT -> {
                    DataStack cursor = (DataStack) event.getCursor();
                    if (topInventory.getType() != InventoryType.PLAYER &&
                            cursor.hasRestriction(Restrictions.PREVENT_MOVE)) {
                        event.setCancelled(true);
                    }
                }
                case SHIFT_LEFT, SHIFT_RIGHT -> {
                    DataStack clicked = (DataStack) event.getCurrentItem();
                    if (clicked != null && clicked.hasRestriction(Restrictions.PREVENT_MOVE)) {
                        event.setCancelled(true);
                    }
                }
                case SWAP_OFFHAND -> {
                    DataStack offhand = (DataStack) player.getInventory().getItemInOffHand();
                    if (topInventory.getType() != InventoryType.PLAYER &&
                            offhand.hasRestriction(Restrictions.PREVENT_MOVE)) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        updateInventoryQueue.add(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelDrop(PlayerDropItemEvent event) {
        DataStack stack = (DataStack) event.getItemDrop().getItemStack();
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || !stack.hasRestriction(Restrictions.NO_DROP)) {
            return;
        }

        playerSet.add(player.getUniqueId());

        if (player.getInventory().firstEmpty() == -1) {
            player.setItemOnCursor(stack);
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void cancelDestroy(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Item item)) {
            return;
        }

        DataStack stack = (DataStack) item.getItemStack();
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
        if (event.getPlayer() instanceof Player player && updateInventoryQueue.remove(player)) {
            BukkitUtils.syncLater(5L, player::updateInventory);
        }
    }
}