package com.ezenity.yougotmail.Fx;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class represents view objects which will retrieve and store the state
 * in a HashMap.
 *
 * @author Ezenity
 * @version 0.3.0
 * @since 0.0.1
 */
public class Tracker {
    public final HashMap<UUID, View> mailbox = new HashMap<>();

    /**
     * This method will register the view.
     *
     * @param view view to be set
     */
    public void register(View view) {
        if (view != null) {
            this.mailbox.put(view.getIdentifier(), view);
        }
    }

    /**
     * This method will unregister the given view.
     *
     * @param view view to be removed
     */
    public void unregister(View view) {
        if (view != null) {
            this.mailbox.remove(view.getIdentifier());
        }
    }

    /**
     * This method will get the view that is uniquely identified with
     * the given identifier.
     *
     * @param identifier identifier
     * @return unique mailbox
     */
    public View getRegistered(UUID identifier) {
        return mailbox.get(identifier);
    }
}
