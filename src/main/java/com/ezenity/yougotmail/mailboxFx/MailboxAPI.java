package com.ezenity.yougotmail.mailboxFx;

import com.ezenity.yougotmail.util.CustomInventory;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Mailbox API
 *
 * API for player mailbox.
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.2
 */
public class MailboxAPI {
    private MailboxTracker mailboxTracker;
    private Mailbox mailbox;
    private CustomInventory customInventory;
    private HashMap<Player, Mailbox> isOpen;

    /**
     * Constructor
     */
    public MailboxAPI() {
    }

    /**
     * Get player mailbox. This method will get the mailbox from the players uuid.
     *
     * @param uuid get uuid for player mailbox
     * @return the uuid from the player
     */
    public Mailbox getPlayerMailbox(UUID uuid) {
        return getMailboxTracker().getMailbox(uuid);
    }

    /**
     * Despawn mailbox.
     *
     * This will remove the players mailbox.
     *
     * @param mailbox remove that players mailbox
     */
    public void despawnMailbox(Mailbox mailbox) {
        getMailboxTracker().unregisterMailbox(mailbox);
    }

    /**
     * View mailbox inventory. This method will get the players mailbox along with its contents
     *
     * @param player get player linked to mailbox
     * @param mailbox get mailbox
     */
    public void viewMailboxInventory(@NotNull Player player, @NotNull Mailbox mailbox) {
        player.closeInventory();

        final CustomInventory mailboxInventory = getMailbox().getInventory();

        player.openInventory(mailboxInventory);

        player.getOpenInventory().getTopInventory().setContents(mailboxInventory.getInventory().getContents());

        player.updateInventory();
    }

    /**
     * Open mailbox. This will open the mailbox up for the player that clicked the the given players mailbox.
     *
     * @param player  get player who clicked on mailbox
     * @param mailbox get players mailbox that was clicked
     */
    public void openMailbox(Player player, Mailbox mailbox) {
        open(player, mailbox);
        getIsOpen().put(player, mailbox);
    }

    /**
     * Get mailbox from title. This will allow you to get the mailbox title from a String.
     *
     * @param title mailbox string title
     * @return mailbox if string title is present, null if no mailbox with given string name.
     */
    public Mailbox getMailboxFromTitle(@NotNull String title) {
        for (Mailbox mailbox : getMailboxTracker().MAILBOX.values()) {
            if (mailbox.getInventory().getTitle().equals(title)){
                return mailbox;
            }
        }
        return null;
    }

    /**
     * Open mailbox. This will get the top inventory of the mailbox from the given hashmap and return its contents.
     *
     * @param player get player who clicked mailbox
     * @param mailbox get mailbox that was clicked with its contents
     */
    private void open(Player player, Mailbox mailbox) {
        final Inventory inventoryTop = getCustomInventory().getTopInventory();
        final ItemStack[] mailboxItems = mailbox.getItemStackHashMap().get(player.getUniqueId());

        if (getIsOpen().containsKey(inventoryTop)){
            player.getInventory().setContents(mailboxItems);
        }

        player.updateInventory();
    }

    /**
     * Get mailbox tracker object.
     *
     * @return mailbox tracker
     */
    public MailboxTracker getMailboxTracker() {
        return mailboxTracker;
    }

    /**
     * Set the mailbox tracker object
     *
     * @param mailboxTracker set mailbox tracker
     */
    public void setMailboxTracker(MailboxTracker mailboxTracker) {
        this.mailboxTracker = mailboxTracker;
    }

    /**
     * Get the players mailbox
     *
     * @return players mailbox
     */
    public Mailbox getMailbox() {
        return mailbox;
    }

    /**
     * Set the players mailbox
     *
     * @param mailbox set players mailbox
     */
    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    /**
     * Get a custom inventory created for a mailbox
     *
     * @return get custom inventory
     */
    public CustomInventory getCustomInventory() {
        return customInventory;
    }

    /**
     * Set the custom inventory for a mailbox
     *
     * @param customInventory set custom inventory
     */
    public void setCustomInventory(CustomInventory customInventory) {
        this.customInventory = customInventory;
    }

    /**
     * Get the opened mailbox and its contents to the given player
     *
     * @return get open mailbox to player
     */
    public HashMap<Player, Mailbox> getIsOpen() {
        return isOpen;
    }

    /**
     * Set the opened mailbox to the given player
     *
     * @param isOpen set open mailbox to player
     */
    public void setIsOpen(HashMap<Player, Mailbox> isOpen) {
        this.isOpen = isOpen;
    }
}
