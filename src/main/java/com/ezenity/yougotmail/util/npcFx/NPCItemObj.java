package com.ezenity.yougotmail.util.npcFx;

import org.bukkit.inventory.ItemStack;

public class NPCItemObj implements NPCItem {
    private String identifier;
    private String opens;
    private int slot;
    private ItemStack display;

    public NPCItemObj(String identifier, String opens, int slot, ItemStack display) {
        this.identifier = identifier;
        this.opens = opens;
        this.slot = slot;
        this.display = display;
    }

    @Override
    public String getOpensNPC() {
        return null;
    }

    @Override
    public ItemStack getEnvelope() {
        return null;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getOpens() {
        return opens;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getDisplay() {
        return display.clone();
    }
}
