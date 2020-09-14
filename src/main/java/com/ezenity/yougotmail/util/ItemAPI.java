package com.ezenity.yougotmail.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAPI {
    protected ItemStack item = new ItemStack(Material.APPLE);
    protected ItemMeta itemMeta = item.getItemMeta();
    protected List<String> itemLore = new ArrayList<>();

    /**
     * Give Item.
     * <p>
     * This will give an itemStack to the player. The item will be placed in the next
     * empty inventory slot
     *
     * @param player get player to give item to
     * @param itemStack get itemStack to give to player
     * @param amount sets itemStack to one item
     */
    protected void giveItem(Player player, ItemStack itemStack, int amount) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        itemStack.setAmount(1);
        final World world = player.getWorld();
        final Location location = player.getLocation();
        final PlayerInventory playerInventory = player.getInventory();
        for (int item = 1; item <= amount; item++) {
            if (playerInventory.firstEmpty() == -1) {
                world.dropItem(location, itemStack);
            } else {
                playerInventory.addItem(itemStack);
            }
        }
        player.updateInventory();
    }

    /**
     * Remove Item
     * <p>
     * This will remove the itemStack from the players inventory.
     *
     * @param player get player to remove item from
     * @param itemStack get the itemStack to remove from players inventory
     * @param amount checks for the amount of items in inventory slot
     */
    protected void removeItem(Player player, ItemStack itemStack, int amount) {
        final Inventory playerInventory = player.getInventory();
        int nextSlot = getNextSlot(player, itemStack); // TODO: Create method
        for (int item = 1; item <= amount; item++) {
            if (nextSlot >= 0) {
                final ItemStack nextItemStack = playerInventory.getItem(nextSlot);
                if (nextItemStack.getAmount() == 1) {
                    playerInventory.setItem(nextSlot, new ItemStack(Material.AIR));
                    nextSlot = getNextSlot(player, itemStack);
                }
            }
        }
        player.updateInventory();
    }

    /**
     * Get Next Slot.
     *
     * This will get the next itemStack inside the players inventory.
     *
     * @param player get player to cycle through inventory
     * @param itemStack get the itemStack to check for
     * @return negative 1 if no similar itemStacks
     */
    private int getNextSlot(Player player, ItemStack itemStack) {
        final Inventory playerInventory = player.getInventory();
        for (int itemInt = 0; itemInt < playerInventory.getSize(); itemInt++) {
            item = playerInventory.getItem(itemInt);
            if (item != null && item.isSimilar(itemStack)) {
                return itemInt;
            }
        }
        return -1;
    }

    /**
     * Get available amount.
     * <p>
     * This will get the amount available for stacking. This method will also collect the itemMeta.
     *
     * @param playerInventory get player inventory
     * @param itemStack get itemStack to check for
     * @return the amount available for stacking
     */
    protected int getAvailableAmount(PlayerInventory playerInventory, ItemStack itemStack) {
        int available = 0;
        if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            for (int object = 0; object < playerInventory.getSize(); object++) {
                final ItemStack playerItemStack = playerInventory.getItem(object);
                if (playerItemStack == null) {
                    available += itemStack.getMaxStackSize();
                } else if (playerItemStack.getType().equals(itemStack.getType()) && playerItemStack.getItemMeta().equals(itemMeta)) {
                    available += itemStack.getMaxStackSize() - playerItemStack.getAmount();
                }
            }
        }
        return available;
    }

    /**
     * Get amount availalbe.
     * <p>
     * This will get the amount available for stacking. This will not collect the items
     * itemMeta.
     *
     * @param playerInventory get player inventory
     * @param itemStack get itemStack to check for
     * @return the amount available for stacking
     */
    protected int getAmount(PlayerInventory playerInventory, ItemStack itemStack) {
        int amount = 0;
        if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
            for (int object = 0; object < playerInventory.getSize(); object++) {
                final ItemStack playerItemStack = playerInventory.getItem(object);
                if (playerItemStack != null && playerItemStack.isSimilar(itemStack)) {
                    amount += playerItemStack.getAmount();
                }
            }
        }
        return amount;
    }

    /**
     * Round double
     * <p>
     * This helper method will round up the double value in 'x' decimal places. This will
     * allow us to round exact values.
     * <p>
     * <a href="https://www.baeldung.com/java-round-decimal-number#rounding-doubles-with-bigdecimal">source</a>
     *
     * @param value input to round up
     * @param decimals amount of decimal places you wish to round up to
     * @return the rounded double
     */
    protected double round(double value, int decimals) {
        if (decimals < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
