package com.ezenity.yougotmail.util;

import com.ezenity.yougotmail.util.npcFx.NPC;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * This class will add support for Citizens plugin.
 */
public class Citizens implements Listener {
    /**
     * Create an instance of citizens class
     */
    private static Citizens instance;

    /**
     * This method will get the citizens events. If none are found will create a new instance of citizens
     *
     * @return citizens instance
     */
    private static Citizens getCitizens() {
        if (instance == null) instance = new Citizens();
        return instance;
    }

    public void load() {}

    public void unload() {}

    /**
     * NPC Despawn Event.
     *
     * This method will check if the npc has despawn. If the npc is despawned by not {@link DespawnReason#PENDING_RESPAWN}.
     * {@link DespawnReason#RELOAD} or despawning due to "UNLOAD" then will getAPI().
     *
     * @param npcDespawnEvent npc despawn event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void npcDespawnEvent(NPCDespawnEvent npcDespawnEvent) {
        final DespawnReason despawnReason = npcDespawnEvent.getReason();
        if (!despawnReason.equals(DespawnReason.PENDING_RESPAWN) && !despawnReason.name().contains("UNLOAD") && !despawnReason.equals(DespawnReason.RELOAD)) {
            // TODO: Make getAPI method
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void npcLeftClickEvent(NPCLeftClickEvent npcLeftClickEvent) {
        final NPC npc = null; // = getAPI();
        if (npc != null && npc.isAccessibleFromNPC()) {
            // getAPI();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void npcRightClickEvent(NPCRightClickEvent npcRightClickEvent) {
        final NPC npc = null; // getAPI();
        if (npc != null && npc.isAccessibleFromNPC()) {
            // getAPI();
        }
    }
}
