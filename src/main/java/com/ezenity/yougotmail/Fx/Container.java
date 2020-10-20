package com.ezenity.yougotmail.Fx;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * a container class that will handle items being placed in a inventory
 * and its position.
 *
 * @author Ezenity
 * @version 0.3.0
 * @since 0.3.0
 */
public class Container extends Handler {
    private int position;
    private ItemStack itemStack;

    public Container() { }

    /**
     * Get the itemStack that is being placed into the inventory
     *
     * @return itemStack inside inventory
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the itemStack inside the inventory
     *
     * @param itemStack itemstack in inventory
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Get the set itemStack position in the inventory
     *
     * @return itemStack position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the itemStack position in the inventory
     *
     * @param position set itemStack position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Adds the output to the inventory
     *
     * @param holder        The holder for the mailboxes inventory
     * @param inventory     The inventory that will be rendered on
     */
    @Override
    public void render(Holder holder, Inventory inventory) {
        inventory.addItem(getItemStack());
    }

    /**
     * Get the mailbox view
     *
     * @param holder                 The holder for the mailboxes inventory
     * @param inventoryClickEvent    The even which will fire
     * @return true if the mailbox rendered is clicked
     */
    @Override
    public boolean click(Holder holder, InventoryClickEvent inventoryClickEvent) {
        return inventoryClickEvent.getRawSlot() == getPosition();
    }
}
