package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntyCobweb implements Listener {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final int COOLDOWN_TIME = 30;


    @EventHandler
    public void onPlayerUseEndRod(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.END_ROD) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.GOLD + "ANTY-COBWEB")) {


                if (cooldowns.containsKey(player.getUniqueId())) {
                    long timeLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + COOLDOWN_TIME) - (System.currentTimeMillis() / 1000);
                    if (timeLeft > 0) {
                        player.sendMessage(ChatColor.RED + "Musisz poczekać " + timeLeft + " sekund przed ponownym użyciem!");
                        return;
                    }
                }

                // Usuń pajęczyny w promieniu 3x3x3 wokół gracza
                int radius = 1;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = player.getLocation().add(x, y, z).getBlock();
                            if (block.getType() == Material.COBWEB) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }


                cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.getInventory().remove(item);
                }



                player.sendMessage(ChatColor.GREEN + "Pajęczyny zostały usunięte!");
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        if (item.getType() == Material.END_ROD && meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.GOLD + "ANTY-COBWEB")) {
            // Anuluj event postawienia bloku
            event.setCancelled(true);
        }
    }
}


