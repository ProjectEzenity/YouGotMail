package com.ezenity.yougotmail.Fx;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Handler class for rendering the inventory and inventory click
 * events.
 *
 * @author Ezenity
 * @version 0.3.0
 * @since 0.3.0
 */
public abstract class Handler {
    /**
     * Adds the output to the inventory
     *
     * @param holder The holder for the mailboxes inventory
     * @param inventory The inventory that will be rendered on
     */
    public abstract void render(Holder holder, Inventory inventory);

    /**
     * Get the mailbox view
     *
     * @param holder The holder for the mailboxes inventory
     * @param inventoryClickEvent The even which will fire
     * @return true if the mailbox rendered is clicked
     */
    public abstract boolean click(Holder holder, InventoryClickEvent inventoryClickEvent);
}
