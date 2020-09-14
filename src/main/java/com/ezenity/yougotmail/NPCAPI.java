package com.ezenity.yougotmail;

import com.ezenity.yougotmail.configuration.Lang;
import com.ezenity.yougotmail.util.CustomInventory;
import com.ezenity.yougotmail.util.ItemAPI;
import com.ezenity.yougotmail.util.mailboxFx.NPCInbox;
import com.ezenity.yougotmail.util.mailboxFx.OpenType;
import com.ezenity.yougotmail.util.npcFx.NPC;
import com.ezenity.yougotmail.util.npcFx.NPCItem;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

/**
 * NPC API
 *
 * API for NPC mailbox.
 *
 * @author Ezenity
 * @version 0.0.1
 * @since 0.0.1
 */
public class NPCAPI extends ItemAPI implements Listener, CommandExecutor {
    private static NPCAPI instance;

    public static NPCAPI getAPI() {
        if (instance == null) instance = new NPCAPI();
        return instance;
    }

    private boolean citizens = false;
    private boolean closeInventoryUponSuccessfulCompose = true;
    private boolean isCloseInventoryUponSuccessfulRead = true;

    private HashMap<Player, NPC> previousInbox;
    private HashMap<Player, NPCItem> isComposing;
    private HashMap<Player, NPCItem> isReading;

    private CustomInventory composeInventory;
    private CustomInventory readInventory;

    private List<Integer> composeDisplayItem;
    private List<Integer> readDisplayItem;

    private HashMap<Integer, List<Integer>> composeAmountSlots;
    private HashMap<Integer, List<Integer>> readAmountSlots;
    private HashMap<String, NPC> npcCommandIds;
    private HashMap<UUID, NPC> NPCSkulls;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        final Player player = playerCommandPreprocessEvent.getPlayer();
        final String message = playerCommandPreprocessEvent.getMessage().substring(1);
        final String toLowerCase = message.toLowerCase();
        final String target = toLowerCase.split(" ")[0];
        final NPC npc = npcCommandIds.getOrDefault(target, null);
        if (npc != null) {
            playerCommandPreprocessEvent.setCancelled(true);
            final String cmdPermission = npc.getCommandPermission();
            if (toLowerCase.equals(message)) {
                if (npc.isAccessibleFromCommand() && cmdPermission == null || player.hasPermission(cmdPermission)) {
                    viewInventory(player, npc);
                } else {
                    Lang.send(player,"[NPC] This NPC is not accessible from its command."); // TODO: Add as config
                }
            } else if (toLowerCase.startsWith(message + " spawn ") && cmdPermission == null || hasPermission(player, cmdPermission, false)) {
                if (citizens) {
                    final int citizensTrue = (toLowerCase + " spawn ").length();
                    npc.spawn(player.getLocation(), Lang.colorize(message.substring(citizensTrue)));
                } else {
                    Lang.send(player, "[NPC] You need the Citizens plugin installed to do this!"); // TODO: Add as config
                }
            }
        }
    }

    public boolean hasPermission(CommandSender sender, String permission, boolean sendNoPermission) {
        if (!(sender instanceof Player) || sender.hasPermission(permission)) return true;
        if (sendNoPermission) Lang.send(sender, "I'm sorry, but you do not have permission to perform this command."); // TODO: Apply as config option
        return false;
    }

    public void viewInventory(@NotNull Player player, @NotNull NPC npc) {
        player.closeInventory();
        final CustomInventory npcInventory = npc.getInventory();
        player.openInventory(Bukkit.createInventory(player, npcInventory.getSize(), npcInventory.getInventoryTitle()));
        player.getOpenInventory().getTopInventory().setContents(npcInventory.getInventory().getContents());
        player.updateInventory();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    private NPC getNPC(@NotNull String title) {
        for (NPC npc : NPCInbox.NPC_INBOXS.values()) {
            if (npc.getInventory().getInventoryTitle().equals(title)){
                return npc;
            }
        }
        return null;
    }

    public void openComposeView(Player player, NPCItem npcItem) {
        if (npcItem.getComposePrice().doubleValue() > 0.0) {
            open(player, npcItem, OpenType.COMPOSE);
            isComposing.put(player, npcItem);
        } else {
//            Lang.sendStringListMessage(player, "TODO", null); // have this as an option for charging for envelopes. Purchase different types of envelopes
            open(player, npcItem, OpenType.COMPOSE);
            isComposing.put(player, npcItem);
        }
    }

    public void openReadView(Player player, NPCItem npcItem) {
        if (npcItem.getReadPrice().doubleValue() > 0.00) {
            open(player, npcItem, OpenType.READ);
            isReading.put(player, npcItem);
        } else {
//            Lang.sendStringListMessage(player, "TODO", null); // have this as an option for charging for opening their envelopes. One time charge ofc.
            open(player, npcItem, OpenType.READ);
            isReading.put(player, npcItem);
        }
    }

    private void open(Player player, NPCItem npcItem, OpenType openType) {
        final boolean isComposing = openType.equals(OpenType.COMPOSE);
        final CustomInventory customInventory = isComposing ? composeInventory : readInventory; /// TODO: Create methods
        final Inventory inventory = customInventory.getInventory();
        final HashMap<Integer, List<Integer>> slotTypes = isComposing ? composeAmountSlots : readAmountSlots; // TODO: Create methods
        final List<Integer> itemsDisplayed = isComposing ? composeDisplayItem : readDisplayItem; // TODO: Create Methods
        player.closeInventory();
        final ItemStack envelope = npcItem.getEnvelope();
        player.openInventory(Bukkit.createInventory(player, customInventory.getSize(), customInventory.getInventoryTitle().replace("{ITEM}", envelope.getType().name())));
        final Inventory inventoryTop = player.getOpenInventory().getTopInventory();
        inventoryTop.setContents(inventory.getContents());
        for (int items : itemsDisplayed) {
            inventoryTop.setItem(items, envelope);
        }
        final double envelopePrice = (isComposing ? npcItem.getComposePrice() : npcItem.getReadPrice()).doubleValue(); // Not selling or buying anything May could add eco for composing envelope
        final PlayerInventory playerInventory = player.getInventory();
        final int envelopeStack = envelope.getMaxStackSize();
        final int envelopeInventory = isComposing ? getAvailableAmount(playerInventory, envelope) : getAmount(playerInventory, envelope); // TODO: create method
        // Can be used for charging per items inside the envelope
//        final String envelopeStackString = Integer.toString(envelopeStack);
//        final String envelopeInventoryString = Integer.toString(envelopeInventory);
        //
        final String envelopePerCost = Double.toString(envelopePrice); // Adding purchase amount for composing envelope? perhaps.
        final String envelopePageCost = Double.toString(round(envelopePrice * envelopeInventory, 2));
        for (int j : slotTypes.keySet()){
            for (int k : slotTypes.get(j)) {
                item = inventoryTop.getItem(k);
                itemMeta = item.getItemMeta();
                itemLore.clear();

                for (String loreString : itemMeta.getLore()) {
                    itemLore.add(loreString.replace("{COST}", j == 1 ? envelopePerCost : envelopePageCost ));
                }

//                itemLore.addAll(itemMeta.getLore()); // bulk 'Collection.addAll' call -> Can be used if not prices for mail
                itemMeta.setLore(itemLore);
                itemLore.clear();
                item.setItemMeta(itemMeta);
                inventoryTop.setItem(k, item);
            }
        }
        player.updateInventory();
    }
}
