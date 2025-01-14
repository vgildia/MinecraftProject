package org.example123.bettergamecore;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockedPlaceTintedGlass implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (!event.getPlayer().isOp() && event.getBlock().getType() == Material.TINTED_GLASS) {
            event.setCancelled(true);
        }
    }
}
