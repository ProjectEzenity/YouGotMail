package com.ezenity.yougotmail.util.mailboxFx;

import com.ezenity.yougotmail.util.npcFx.NPC;
import java.util.HashMap;

/**
 * Mailbox Container.
 * <p>
 * This is the class for the mailbox's inbox.
 */
public abstract class MailboxInbox {
    /**
     * NPC Hashmap. Will store in memory for now.
     */
    public static HashMap<String, NPC> NPC_INBOXS = new HashMap<>();

    /**
     * This method will register the npcInbox.
     *
     * @param npcInbox npcInbox to be set
     */
    public static void registerInbox(NPC npcInbox) {
        if (npcInbox != null) {
            NPC_INBOXS.put(npcInbox.getIdentifier(), npcInbox);
        }
    }

    /**
     * This method will unregister the given npcInbox.
     *
     * @param npcInbox npcInbox to be removed
     */
    public static void unregisterInbox(NPC npcInbox) {
        if (npcInbox != null) {
            NPC_INBOXS.remove(npcInbox.getIdentifier());
        }
    }

    /**
     * This method will get the npcInbox this is uniquely identified with
     * the given inboxIdentifier.
     *
     * @param inboxIdentifier npc inboxIdentifier
     * @return unique npc inbox
     */
    public static NPC getNPCInbox(String inboxIdentifier) {
        return NPC_INBOXS.getOrDefault(inboxIdentifier, null);
    }
}
