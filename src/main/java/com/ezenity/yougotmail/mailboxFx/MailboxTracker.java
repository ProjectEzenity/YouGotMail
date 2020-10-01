package com.ezenity.yougotmail.mailboxFx;

import java.util.HashMap;
import java.util.UUID;

/**
 * Mailbox Container.
 * <p>
 * This is the class for the mailboxes.
 *
 * @author Ezenity
 * @version 0.0.3
 * @since 0.0.1
 */
public class MailboxTracker {
    /**
     * NPC Hashmap. Will store in memory for now.
     */
    public final HashMap<UUID, Mailbox> mailbox = new HashMap<>();

    /**
     * This method will register the mailbox.
     *
     * @param mailbox mailbox to be set
     */
    public void registerTracker(Mailbox mailbox) {
        if (mailbox != null) {
            this.mailbox.put(mailbox.getIdentifier(), mailbox);
        }
    }

    /**
     * This method will unregister the given mailbox.
     *
     * @param mailbox mailbox to be removed
     */
    public void unregisterTracker(Mailbox mailbox) {
        if (mailbox != null) {
            this.mailbox.remove(mailbox.getIdentifier());
        }
    }

    /**
     * This method will get the mailbox that is uniquely identified with
     * the given registeredIdentifier.
     *
     * @param registeredIdentifier registeredIdentifier
     * @return unique mailbox
     */
    public Mailbox getRegisteredTracker(UUID registeredIdentifier) {
        return mailbox.getOrDefault(registeredIdentifier, null);
    }
}
