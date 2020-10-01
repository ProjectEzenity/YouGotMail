package com.ezenity.yougotmail.mailboxFx;

import com.ezenity.yougotmail.util.CustomInventory;
import com.mysql.fabric.xmlrpc.base.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Mailbox class which will handle created mailboxes.
 *
 * @author Ezenity
 * @version 0.0.3
 * @since 0.0.1
 */
public class Mailbox extends Tracker {
    /**
     * Gets the custom inventory created for a mailbox
     */
    private final CustomInventory inventory;
    /**
     * String identifier. Utilizes an identification in a string format. Can be served as the players name
     */
    private final UUID identifier;
    /**
     * ItemStack Hashmap. This hash will key to a mailbox and value any items linked to the specified mailbox.
     */
//    private HashMap<Mailbox, ItemStack[]> itemStackHashMap;
    private List<ItemStack> itemStackList;

    /**
     * Mailbox. Create a custom mailbox for the given player and provide its contents into a hashmap.
     *
     * @param inventory get custom mailbox inventory
     * @param identifier get the mailboxes uuid associated with a player
     */
    public Mailbox(CustomInventory inventory, UUID identifier) {
        this.inventory = inventory;
        this.identifier = identifier;
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

//    /**
//     * Get the list of itemStacks that are within the mailbox
//     *
//     * @return all items inside mailbox
//     */
//    public HashMap<Mailbox, ItemStack[]> getItemStackHashMap() {
//        return itemStackHashMap;
//    }
//
//    /**
//     * Set an itemStack into a given mailbox.
//     *
//     * @param itemStackHashMap set itemStack to mailbox
//     */
//    public void setItemStackHashMap(HashMap<Mailbox, ItemStack[]> itemStackHashMap) {
//        this.itemStackHashMap = itemStackHashMap;
//    }

    /**
     * Ge the list of itemStacks that are within the mailbox as a list.
     *
     * @return list of itemStacks
     */
    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    /**
     * Set an itemStack into a given mailbox as a list.
     *
     * @param itemStackList set list of itemStacks to mailbox
     */
    public void setItemStackList(List<ItemStack> itemStackList) {
        this.itemStackList = itemStackList;
    }

    /**
     * Despawn Mailbox
     *
     * This will despawn a registered mailbox.
     *
     * @param mailbox remove the specified mailbox
     */
    public void despawnMailbox(Mailbox mailbox) {
        unregisterTracker(mailbox);
    }

    /**
     * Spawn Mailbox.
     *
     * This will spawn (register) a mailbox.
     *
     * @param mailbox spawn the specified mailbox
     */
    public void spawnMailbox(Mailbox mailbox) {
        registerTracker(mailbox);
    }

    /**
     * Open Mailbox. This method will check for the mailbox identifier and get the items from the
     * itemStack list. After the itemStacks are set to the looking mailbox, the players opened
     * top inventory will update to ensure the itemStacks are set.
     *
     * @param player get the player who clicked the mailbox
     * @param mailbox get mailbox that was clicked with its contents
     */
    public void openMailbox(Player player, Mailbox mailbox) {
        if (getRegisteredTracker(getIdentifier()) != null )  {
//            player.getInventory().setContents(getItemStackHashMap().get(mailbox));
            mailbox.getInventory().getTopInventory().setContents((ItemStack[]) getItemStackList().toArray());
//            player.getInventory().setContents((ItemStack[]) getItemStackList().toArray());
        }

        player.updateInventory(); // Not sure if this is needed
    }

    /**
     * Get mailbox from title. This will get the mailbox from the title via String.
     *
     * @param title get mailbox string title
     * @return mailbox with contents or null if no mailbox with given string title
     */
    public Mailbox getMailboxFromTitle(@NotNull String title) {
        for (Mailbox mailbox : MAILBOX.values()) {
            if (mailbox.getInventory().getTitle().equals(title))
                return mailbox;
        }
        return null;
    }

    public Mailbox getMailboxFromUUID(UUID uuid) {
        return getRegisteredTracker(getIdentifier());
    }
}
