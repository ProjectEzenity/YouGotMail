package com.ezenity.yougotmail.Fx;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * This uses a container layer to represent an opened view.
 *
 * @author Ezenity
 * @version 0.3.0
 * @since 0.0.1
 */
public class View extends Container {
    private final Player player;
    private final int size;
    private final String title;
    private final UUID identifier;
    private final Tracker tracker = new Tracker();

    /**
     * Custom inventory
     *
     * @param title inventory title
     * @param size inventory size
     * @param player inventory creator
     * @param identifier to identify the inventory
     */
    public View(String title, int size, Player player, UUID identifier) {
        this.title = title;
        this.size = size;
        this.player = player;

        this.identifier = identifier;

        this.tracker.register(this);
    }

    /**
     * Get the size of this inventory window.
     *
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the identifier of this inventory window.
     *
     * @return The identifier.
     */
    public UUID getIdentifier() {
        return identifier;
    }

    /**
     * Get the player creating the view
     *
     * @return view creator
     */
    public @NotNull HumanEntity getPlayer() {
        return player;
    }

    /**
     * Get the title of this inventory window.
     *
     * @return The title.
     */
    public @NotNull String getTitle() {
        return title;
    }

    /**
     * Creates the inventory holder for the newly created view.
     *
     * @param material inventory holder
     */
    public void open(Material material) {
        Holder holder = new Holder(this, material);

        Inventory inventory = holder.getInventory();

        tracker.getRegistered(getIdentifier());

        player.openInventory(inventory);
    }
}
