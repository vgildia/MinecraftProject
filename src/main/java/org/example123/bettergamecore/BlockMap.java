package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockMap implements Listener {
    private final Plugin plugin;
    private ConfigurationSection placedBlocksSection;
    private List<Material> allowedMaterials = List.of(Material.EMERALD_BLOCK,Material.EMERALD_ORE,Material.IRON_BLOCK,Material.IRON_ORE,Material.GOLD_BLOCK,Material.GOLD_ORE,Material.DIAMOND_BLOCK,Material.DIAMOND_ORE,Material.CYAN_WOOL,Material.TINTED_GLASS,Material.ANCIENT_DEBRIS,Material.NETHERITE_BLOCK); //Generatory
    private Set<Block> blocksPlacedByPlayers = new HashSet<>();

    public BlockMap(Plugin plugin){
        this.plugin = plugin;

        placedBlocksSection = plugin.getConfig().getConfigurationSection("Placed-Blocks");
        if (placedBlocksSection == null) {
            placedBlocksSection = plugin.getConfig().createSection("Placed-Blocks");
        }

        if (!plugin.getConfig().contains("Allowed-Materials")) {
            plugin.getConfig().set("Allowed-Materials",allowedMaterials.stream().map(Material::toString).collect(Collectors.toList()));
            plugin.saveConfig();
        }
        allowedMaterials = plugin.getConfig().getStringList("Allowed-Materials").stream().map(Material::getMaterial).collect(Collectors.toList());

        if (plugin.getConfig().contains("Placed-Blocks")) {
            Set<String> blockKeys = placedBlocksSection.getKeys(false);
            blocksPlacedByPlayers.addAll(blockKeys.stream()
                    .map(this::deserialize)
                    .collect(Collectors.toSet()));
        }
    }
    private Block deserialize (String s){
        String[] split = s.split(";");
        String worldName = split[0];
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        World world = Bukkit.getWorld(worldName);
        return world.getBlockAt(x,y,z);

    }
    private String serialize (Block block){
        return block.getWorld().getName()+ ";" + block.getLocation().getBlockX() + ";" +
                block.getLocation().getBlockY() + ";" + block.getLocation().getBlockZ();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer().hasPermission("bettergamecore.editmap")){
            return;
        }
        if(allowedMaterials.contains(event.getBlock().getType())){
            return;
        }
        if(blocksPlacedByPlayers.contains(event.getBlock())) {
            blocksPlacedByPlayers.remove(event.getBlock());
            return;
        }
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED + "Nie możesz wykonać tej interakcji");
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getPlayer().hasPermission("bettergamecore.editmap")){
            return;
        }

        // zapisujemy postawiony blok do listy blockPlacedByPlayers
        blocksPlacedByPlayers.add(event.getBlock());
        placedBlocksSection.set(serialize(event.getBlock()), "1");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, plugin::saveConfig);
    }
}
