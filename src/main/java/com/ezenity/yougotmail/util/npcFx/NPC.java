package com.ezenity.yougotmail.util.npcFx;

import com.ezenity.yougotmail.util.CustomInventory;
import java.util.HashMap;
import org.bukkit.Location;

public interface NPC extends Identifiable {
    String getCommand();

    boolean isAccessibleFromCommand();
    String getCommandPermission();

    boolean isAccessibleFromNPC();
    String getNPCPermission();

    CustomInventory getInventory();
    /*
     * TODO: Change HashMap to customHashMap from Reach to add additional lookup situation.
     */
    HashMap<Integer, HashMap<Integer, NPCItem>> getPages();

    default void spawn(Location location, String name){
        // TODO
    }
}
