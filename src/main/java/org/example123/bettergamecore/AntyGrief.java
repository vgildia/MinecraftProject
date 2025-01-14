package org.example123.bettergamecore;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AntyGrief implements Listener {
    private final List<Pair<Block, Long>> blocks = new ArrayList<>();

    public AntyGrief(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Pair<Block, Long> pair : blocks) {
                Block block = pair.first();
                Long time = pair.second();
                if (time < System.currentTimeMillis()) {
                    block.setType(Material.AIR);
                } else {
                    break;
                }
            }
        }, 20L, 20L);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (!player.hasPermission("disable-anti-grief")){
            blocks.add(Pair.of(event.getBlock(), System.currentTimeMillis() + 2 * 60 * 60 * 1000));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("disable-anti-grief")){
            blocks.removeIf((pair) -> pair.first().equals(event.getBlock()));
        }
    }
}
