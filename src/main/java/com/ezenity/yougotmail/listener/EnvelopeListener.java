package com.ezenity.yougotmail.listener;

import com.ezenity.yougotmail.Main;
import com.ezenity.yougotmail.conversations.GeneralPrompt;
import com.ezenity.yougotmail.util.CustomInventory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Envelope Listener
 *
 * @author Ezenity
 * @version 0.2.0
 * @since 0.0.4
 */
public class EnvelopeListener implements Listener {
    private final ConversationFactory conversationFactory;
    private CustomInventory customInventory;

    public EnvelopeListener(Main instance) {
        conversationFactory = new ConversationFactory(instance);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        // Check for item in hand
        if (inventoryClickEvent.getCurrentItem() != null) {
            // Check for Paper & was right clicked
            if (inventoryClickEvent.getCurrentItem().getType() == Material.PAPER && inventoryClickEvent.isRightClick()) {
                // Check if paper has itemMeta
                if (inventoryClickEvent.getCurrentItem().hasItemMeta()) {
                    // Get Paper (envelope) ItemMeta
                    ItemMeta envelopeMeta = inventoryClickEvent.getCurrentItem().getItemMeta();
                    // Get the player who clicked "Envelope"
                    Player player = (Player) inventoryClickEvent.getWhoClicked();

                    // Check if envelope has custom data which states a custom name was set already
                    if (envelopeMeta.hasCustomModelData() && envelopeMeta.getCustomModelData() == 1337) {
                        player.sendMessage(ChatColor.AQUA + "Your envelope title was already set and sealed, please mail it already...");
                    }

                    // Check if Envelope is sealed
                    if (envelopeMeta.hasDisplayName() && envelopeMeta.getDisplayName().contains("Sealed")) {
                        // Create maps for envelope checking and setting (for mvp purposes)
                        final Map<Object, Object> map = new HashMap<>();
                        map.put("envTitle", null);
                        map.put("sealEnv", null);

                        // check if envelope is sealed.
                        if (map.get("sealEnv") == null) {
                            // Create conversation with "conversation api"

                            // Create a convo with a player
                            Conversation conversation = conversationFactory
                                    .withFirstPrompt(new GeneralPrompt()) // Convo starting point
//                                .withEscapeSequence("/exit") // Will end convo instantly, anything extra to "/exit" will not activate escape
                                    .withPrefix(conversationContext -> ChatColor.AQUA + "Envelope " + ChatColor.GRAY) // Always have prefix for convo
                                    .withInitialSessionData(map) // Hold data in a hashmap
                                    .withLocalEcho(false) // Player see their responses in chat
                                    .buildConversation(player); // Build convo with player

                            // Check on convo status
                            conversation.addConversationAbandonedListener(conversationAbandonedEvent -> {
                                if (conversationAbandonedEvent.gracefulExit())
                                    getLogger().info("Graceful exit");

                                try {
                                    getLogger().info("Canceller" + conversationAbandonedEvent.getCanceller().toString());
                                } catch (NullPointerException nullPointerException) {
                                    getLogger().info("Null canceller");
                                }
                            });

                            // Start the convo
                            conversation.begin();

                            // Apply new custom envelope title
                            ItemStack sealedEnvelope = player.getInventory().getItemInMainHand();
                            ItemMeta sealedEnvelopeMeta = sealedEnvelope.getItemMeta();
                            sealedEnvelopeMeta.setDisplayName(conversation.getContext().getSessionData("envTitle").toString());
                            sealedEnvelopeMeta.setLore(null); // Remove lore stating to set their envelope title
                            sealedEnvelopeMeta.setCustomModelData(1337);
                            sealedEnvelope.setItemMeta(sealedEnvelopeMeta);
                        }
                    }

                    // Check if Envelope is not sealed.
                    if (envelopeMeta.hasDisplayName() && envelopeMeta.getDisplayName().contains("Envelope") && !envelopeMeta.getDisplayName().contains("sealed")) {
                        // Get items set inside envelop
//                        CustomInventory customInventory = new CustomInventory(player, (InventoryHolder) player.getInventory().getItemInMainHand(), 6*9, "Envelope");
                        setCustomInventory(new CustomInventory(player, (InventoryHolder) player.getInventory().getItemInMainHand(), 6*9, player.getDisplayName() + "'s Envelope"));
                        // Set items inside envelope inventory
//                        new LinkedList<>(Arrays.asList(customInventory.getTopInventory().getContents()));
                        // Create seal envelope itemStack to click inside inventory
                        ItemStack sealEnvelope = new ItemStack(Material.NAME_TAG);
                        ItemMeta sealEnvelopeMeta = sealEnvelope.getItemMeta();
                        sealEnvelopeMeta.setDisplayName("Seal");
                        List<String> sealEnvelopeLore = new ArrayList<>();
                        sealEnvelopeLore.add("");
                        sealEnvelopeLore.add("This will seal the");
                        sealEnvelopeLore.add("envelope. Once sealed");
                        sealEnvelopeLore.add("You cannot reopen");
                        sealEnvelopeMeta.setLore(sealEnvelopeLore);
                        sealEnvelope.setItemMeta(sealEnvelopeMeta);
                        // Set "Seal ItemStack" to slot 8
                        customInventory.setItem(8, sealEnvelope);
                        // Check if "Seal" was clicked
                        if (inventoryClickEvent.getCurrentItem().hasItemMeta() && inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("Seal")) {
                            // remove button inside envelope
                            customInventory.setItem(8, null);
                            // Store contents inside envelope
                            customInventory.storeContents(customInventory.getTopInventory().getContents());
                            customInventory.storeContents(customInventory.getTopInventory().getContents());
                            // Create sealed Envelope
                            player.updateInventory();
                            ItemStack sealedEnvelope = player.getInventory().getItemInMainHand();
                            ItemMeta sealedEnvelopeMeta = sealedEnvelope.getItemMeta();
                            sealedEnvelopeMeta.setDisplayName("Sealed");
                            List<String> sealedEnvelopeLore = new ArrayList<>();
                            sealedEnvelopeLore.add("");
                            sealedEnvelopeLore.add("This envelope is sealed.");
                            sealedEnvelopeLore.add("Please right click to set");
                            sealedEnvelopeLore.add("a custom envelope name");
                            sealedEnvelopeMeta.setLore(sealedEnvelopeLore);
                            sealedEnvelope.setItemMeta(sealedEnvelopeMeta);
                            // Close inventory to seal it
                            player.closeInventory();
                            // Set sealed envelope in main hand
//                            player.getInventory().setItemInMainHand(sealedEnvelope);
                        }
                    }
                }
            }
        }
    }

    public void setCustomInventory(CustomInventory customInventory) {
        this.customInventory = customInventory;
    }
}
