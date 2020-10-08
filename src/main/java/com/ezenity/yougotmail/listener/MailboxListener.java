package com.ezenity.yougotmail.listener;

import com.ezenity.yougotmail.mailboxFx.Mailbox;
import com.ezenity.yougotmail.util.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Mailbox Listener
 *
 * @author Ezenity
 * @version 0.2.0
 * @since 0.0.4
 */
public class MailboxListener implements Listener {
    private CustomInventory customInventory;
    private Mailbox mailbox;
    // Stated I can use a hard coded UUID (Ezenity's UUID)
    private final OfflinePlayer ezenitysMailbox = Bukkit.getOfflinePlayer("123e4567-e89b-42d3-a456-556642440000");

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMailboxPlace(BlockPlaceEvent blockPlaceEvent) {
        if (blockPlaceEvent.getPlayer().getInventory().getItemInOffHand().equals(Material.DIRT)
                && blockPlaceEvent.getBlockPlaced().getType().equals(Material.PLAYER_HEAD) || blockPlaceEvent.getBlockPlaced().getType().equals(Material.PLAYER_WALL_HEAD)) { // Not sure wht the major difference is with PALYER_HEAD & PLAYER_WALL_HEAD
            // Create a custom inventory for the mailbox
            setCustomInventory(new CustomInventory(blockPlaceEvent.getPlayer(), (InventoryHolder) blockPlaceEvent.getBlockPlaced(), 6 * 9, blockPlaceEvent.getPlayer().getDisplayName() + "'s Mailbox") );
            // Set the custom inventory to a mailbox with a hard corded UUID
            setMailbox(new Mailbox(
                    getCustomInventory(),
                    ezenitysMailbox.getUniqueId()
            ));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMailboxClick(PlayerInteractEvent playerInteractEvent) {
        // Make sure mailbox is clicked
        if (playerInteractEvent.getClickedBlock() == null) {
            return;
        }

        // Check if block was is a player head (mailbox)
        if (playerInteractEvent.getClickedBlock().getType().equals(Material.PLAYER_HEAD) || playerInteractEvent.getClickedBlock().getType().equals(Material.PLAYER_WALL_HEAD)){
            // Check if mailbox was right clicked
            if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Check if player owns mailbox
                if (playerInteractEvent.getPlayer().getUniqueId() ==  getMailbox().getIdentifier()) {

                    // If mailbox not hard coded could do some enhanced for loop for checking a uuid and/or location
                    // create inventory for mailbox with the item that was clicked if no mailbox was created
                    if (!(playerInteractEvent.getClickedBlock() instanceof InventoryHolder)) {
                        setCustomInventory(new CustomInventory(playerInteractEvent.getPlayer(), (InventoryHolder) playerInteractEvent.getClickedBlock(), 6 * 9, "Mailbox"));
                        setMailbox(new Mailbox(
                                getCustomInventory(),
                                ezenitysMailbox.getUniqueId()
                        ));
                    }

                    if (getMailbox().getItemStackList() != null) {
                        // Player owns mailbox so, Spawn the hardcoded mailbox.
                        mailbox.spawn(getMailbox());
//                    mailbox.getMailboxFromUUID(ezenitysMailbox.getUniqueId());
                    } else {
                        playerInteractEvent.getPlayer().sendMessage("You have no mail in your mailbox.");
                    }
                } else { // Not owner of mailbox (input envelope here)
                    // Check if item is an paper (envelope)
                    if (playerInteractEvent.getPlayer().getInventory().getItemInMainHand().equals(Material.PAPER)) {
                        // Check if paper is actually an envelope
                        if (playerInteractEvent.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()
                                && playerInteractEvent.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1337) {
                            // Add envelope to players mailbox
                            mailbox.getInventory().getTopInventory().addItem(playerInteractEvent.getPlayer().getInventory().getItemInMainHand());
                            playerInteractEvent.getPlayer().sendMessage("Your envelope has been mailed.");
                        }
                    }

                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoinMessage(PlayerJoinEvent playerJoinEvent) {
        if (getMailbox().getItemStackList() != null) {
            playerJoinEvent.getPlayer().sendMessage("You have mail, please check your mailbox.");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMailboxInventoryClick(InventoryClickEvent inventoryClickEvent) {
        // Check for mailbox title
        if (inventoryClickEvent.getInventory().getType().name().contains("Mailbox")) {
            // Check for item clicked has model data of 1337 (most likely not needed)
            if (inventoryClickEvent.getCurrentItem().getItemMeta().getCustomModelData() == 1337) {
                // Check if players inventory is not full
                if (inventoryClickEvent.getWhoClicked().getInventory().firstEmpty() != -1) {
                    // Get stored contents inside envelope
                    for (ItemStack itemStack : getCustomInventory().restoreContents()) {
                        // Set stored contents inside envelope to players inventory on next empty slot
                        inventoryClickEvent.getWhoClicked().getInventory().addItem(itemStack);
                    }
                } else { // Inventory is full but lets check for stackable items
                    for (ItemStack itemStack : getCustomInventory().restoreContents()) {
                        // Check each slot
                        for (int slot = 0; slot < 35; slot++) {
                            // Check slot & itemStack for maxed stackable amount
                            if (inventoryClickEvent.getWhoClicked().getInventory().getItem(slot).getAmount() + itemStack.getAmount() <= itemStack.getMaxStackSize()) {
                                // Check for same items within envelope against players inventory
                                if (inventoryClickEvent.getWhoClicked().getInventory().getItem(slot).getType().equals(itemStack.getType())) {
                                    // Add to stack
                                    inventoryClickEvent.getWhoClicked().getInventory().addItem(itemStack);
                                    // Makes sure to stop when no matching items are found
                                    break;
                                }
                            }
                            if (slot == 34) {
                                inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.DARK_RED + "Your inventory is full, please free up some slots");
                            }
                        }
                    }
                }
                inventoryClickEvent.setCancelled(true);

                // if envelope has no contents remove from mailbox
                if (getCustomInventory().restoreContents() == null)
                    inventoryClickEvent.getCurrentItem().setAmount(-1);
            }
        }
    }

    public CustomInventory getCustomInventory() {
        return customInventory;
    }

    public void setCustomInventory(CustomInventory customInventory) {
        this.customInventory = customInventory;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }
}
