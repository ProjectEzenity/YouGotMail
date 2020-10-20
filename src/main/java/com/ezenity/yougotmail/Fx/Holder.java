package com.ezenity.yougotmail.Fx;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * This is the inventory holder for when the view is opened
 *
 * @author Ezenity
 * @version 0.3.0
 * @since 0.3.0
 */
public class Holder implements InventoryHolder {
    private final View view;
    private final Material material;
    private final Inventory inventory;

    /**
     * The inventory holder for the created inventory view
     *
     * @param view inventory view created
     * @param material reference for the inventory holder
     */
    public Holder(View view, Material material) {
        this.view = view;
        this.material = material;

        this.inventory = Bukkit.createInventory(this, view.getSize(), view.getTitle());
    }

    /**
     * Gets the view object
     *
     * @return view
     */
    public View getView() {
        return view;
    }

    /**
     * Gets the material that is the inventory holder reference
     *
     * @return inventroy holder reference
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}