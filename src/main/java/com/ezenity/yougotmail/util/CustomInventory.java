package com.ezenity.yougotmail.util;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventory {
    private Inventory inventory;
    private InventoryHolder inventoryHolder;
    private InventoryType inventoryType;
    private int inventorySize;
    private String inventoryTitle;

    public CustomInventory(InventoryHolder inventoryHolder, int inventorySize, String inventoryTitle) {
        this.inventoryHolder = inventoryHolder;
        inventoryType = InventoryType.CHEST;
        this.inventorySize = inventorySize;
        this.inventoryTitle = inventoryTitle;
    }

    public CustomInventory(InventoryHolder inventoryHolder, InventoryType inventoryType, String inventoryTitle) {
        this.inventoryHolder = inventoryHolder;
        this.inventoryType = inventoryType;
        this.inventorySize = 0;
        this.inventoryTitle = inventoryTitle;
    }

    public Inventory getInventory() {
        if(inventory == null) inventory = inventoryType == InventoryType.CHEST ? Bukkit.createInventory(inventoryHolder, inventorySize, inventoryTitle) : Bukkit.createInventory(inventoryHolder, inventoryType, inventoryTitle);
        return inventory;
    }

    public InventoryHolder getInventoryHolder() {
        return inventoryHolder;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public int getSize() {
        return inventorySize;
    }

    public String getInventoryTitle() {
        return inventoryTitle;
    }
}
