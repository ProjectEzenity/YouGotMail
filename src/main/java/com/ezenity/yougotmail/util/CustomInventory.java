package com.ezenity.yougotmail.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Custom inventory. This class extends {@link InventoryView} which will add custom
 * properties to add additions to the default given inventory view.
 */
public class CustomInventory extends InventoryView {
    private Inventory inventory;
    private final Player player;
    private final InventoryHolder inventoryHolder;
    private InventoryType inventoryType;
    private final int inventorySize;
    private final String inventoryTitle;

    /**
     * Custom Inventory. This will get a custom inventory with a inventory type of CHEST. This method will allow you to create the amount of columns
     * you want for your inventory slot. The inventory size amount is as basic as it can get. You will need to add mathematical equation to ensure
     * that the correct inventory size was/is correctly set.
     *
     * @param player get the viewing player
     * @param invHolder get players mailbox inventory is attached to
     * @param inventorySize set the inventory slot amount
     * @param invTitle set the inventory title
     */
    public CustomInventory(Player player, InventoryHolder invHolder, int inventorySize, String invTitle) {
        this.player = player;
        this.inventoryHolder = invHolder;
        this.inventoryType = InventoryType.CHEST;
        this.inventorySize = inventorySize;
        this.inventoryTitle = invTitle;
    }

    /**
     * Custom Inventory. This will get a custom inventory type that is set with an inventory size amount of 0. This method is not intended for the
     * inventory type CHEST. This can be used for manipulating other inventory types for your purpose.
     *
     * @param player get the viewing player
     * @param inventoryHolder get players mailbox inventory is attached to
     * @param inventoryType set the inventory type, other then using a chest
     * @param inventoryTitle set the inventory type
     */
    public CustomInventory(Player player, InventoryHolder inventoryHolder, InventoryType inventoryType, String inventoryTitle) {
        this(player, inventoryHolder,  0, inventoryTitle);
        this.inventoryType = inventoryType;
    }

    /**
     * Get inventory. This will check if the inventory is null or not. If the inventory is null this method revert to check for the set inventory type.
     * If the inventory type is a CHEST then it will create an inventory with {@link CustomInventory#CustomInventory(Player, InventoryHolder, int, String)}
     * else it will create an inventory with {@link CustomInventory#CustomInventory(Player, InventoryHolder, InventoryType, String)}.
     *
     * @return inventory that was created
     */
    public Inventory getInventory() {
        if(inventory == null) inventory = inventoryType == InventoryType.CHEST ? Bukkit.createInventory(inventoryHolder, inventorySize, inventoryTitle) : Bukkit.createInventory(inventoryHolder, inventoryType, inventoryTitle);
        return inventory;
    }

    /**
     * Get inventory size. If the inventory is a chest this will return the inventory integer size. If the inventory is not a inventory type
     * of chest, this will return a 0.
     *
     * @return the inventory size amount
     */
    public int getInventorySize() {
        return inventorySize;
    }

    /**
     * Get the upper inventory involved in this transaction.
     *
     * @return the inventory
     */
    @Override
    public @NotNull Inventory getTopInventory() {
        return getInventory();
    }

    /**
     * Get the lower inventory involved in this transaction.
     *
     * @return the inventory
     */
    @Override
    public @NotNull Inventory getBottomInventory() {
        return getInventory();
    }

    /**
     * Get the player viewing.
     *
     * @return the player
     */
    @Override
    public @NotNull HumanEntity getPlayer() {
        return player;
    }

    /**
     * Determine the type of inventory involved in the transaction. This
     * indicates the window style being shown. It will never return PLAYER,
     * since that is common to all windows.
     *
     * @return the inventory type
     */
    @Override
    public @NotNull InventoryType getType() {
        return inventoryType;
    }

    /**
     * Get the title of this inventory window.
     *
     * @return The title.
     */
    @Override
    public @NotNull String getTitle() {
        return inventoryTitle;
    }
}
