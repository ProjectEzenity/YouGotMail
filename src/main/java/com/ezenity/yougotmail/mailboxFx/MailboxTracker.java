package com.ezenity.yougotmail.mailboxFx;

import java.util.HashMap;
import java.util.UUID;

/**
 * Mailbox Container.
 * <p>
 * This is the class for the mailboxes.
 *
 * @author Ezenity
 * @version 0.0.2
 * @since 0.0.1
 */
public class MailboxTracker {
    /**
     * NPC Hashmap. Will store in memory for now.
     */
    public final HashMap<UUID, Mailbox> MAILBOX = new HashMap<>();

    /**
     * This method will register the mailbox.
     *
     * @param mailbox mailbox to be set
     */
    public void registerMailbox(Mailbox mailbox) {
        if (mailbox != null) {
            MAILBOX.put(mailbox.getIdentifier(), mailbox);
        }
    }

    /**
     * This method will unregister the given mailbox.
     *
     * @param mailbox mailbox to be removed
     */
    public void unregisterMailbox(Mailbox mailbox) {
        if (mailbox != null) {
            MAILBOX.remove(mailbox.getIdentifier());
        }
    }

    /**
     * This method will get the mailbox that is uniquely identified with
     * the given mailboxIdentifier.
     *
     * @param mailboxIdentifier mailboxIdentifier
     * @return unique mailbox
     */
    public Mailbox getMailbox(UUID mailboxIdentifier) {
        return MAILBOX.getOrDefault(mailboxIdentifier, null);
    }
}
