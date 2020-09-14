package com.ezenity.yougotmail.util.npcFx;

import java.math.BigDecimal;
import org.bukkit.inventory.ItemStack;

public interface NPCItem extends Itemable, Slotable {
    String getOpensNPC();
    BigDecimal getComposePrice();
    BigDecimal getReadPrice();
    ItemStack getEnvelope();
}
