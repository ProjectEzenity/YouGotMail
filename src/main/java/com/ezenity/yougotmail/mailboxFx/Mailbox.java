package com.ezenity.yougotmail.mailboxFx;

import com.ezenity.yougotmail.util.CustomInventory;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Mailbox class which will handle created mailboxes.
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.1
 */
public class Mailbox {
    /**
     * Gets the custom inventory created for a mailbox
     */
    private final CustomInventory inventory;
    /**
     * Gets the player interacting with the mailbox
     */
    private final Player player;
    /**
     * String identifier. Utilizes an identification in a string format. Can be served as the players name
     */
    private final UUID identifier;
    /**
     * ItemStack Hashmap. Gets all the items inside a mailbox.
     */
    private final HashMap<UUID, ItemStack[]> itemStackHashMap;

    /**
     * Mailbox. Create a custom mailbox for the given player and provide its contents into a hashmap.
     *
     * @param inventory get custom mailbox inventory
     * @param player get player interacting with the mailbox
     * @param identifier get the mailboxes uuid associated with a player
     * @param itemStack get an array of itemStacks that are within the mailbox
     */
    public Mailbox(CustomInventory inventory, Player player, UUID identifier, HashMap<UUID, ItemStack[]> itemStack) {
        this.inventory = inventory;
        this.player = player;
        this.identifier = identifier;
        this.itemStackHashMap = itemStack;
    }

    /**
     * Get the uuid associated with the mailbox
     *
     * @return uuid from mailbox
     */
    public UUID getIdentifier(){
        return identifier;
    }

    /**
     * Get the custom inventory that is attached to the mailbox
     *
     * @return custom mailbox inventory
     */
    public CustomInventory getInventory() {
        return inventory;
    }

    /**
     * Get the player that is interacting with the mailbox
     *
     * @return player opening mailbox
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the list of itemStacks that are within the mailbox
     *
     * @return all items inside mailbox
     */
    public HashMap<UUID, ItemStack[]> getItemStackHashMap() {
        return itemStackHashMap;
    }
}
