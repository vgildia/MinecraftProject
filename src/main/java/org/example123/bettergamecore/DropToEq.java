package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DropToEq implements Listener {
    private final Random random = new Random();
    private final JavaPlugin plugin;
    private final Map<UUID, List<ItemStack>> playerRespawnItems = new HashMap<>();
    private final Map<Material,Material> drops = new HashMap<>();

    public DropToEq(JavaPlugin plugin){
        this.plugin = plugin;
        drops.put(Material.EMERALD_BLOCK,Material.EMERALD_BLOCK);
        drops.put(Material.EMERALD_ORE,Material.EMERALD);
        drops.put(Material.DIAMOND_BLOCK,Material.DIAMOND_BLOCK);
        drops.put(Material.DIAMOND_ORE,Material.DIAMOND);
        drops.put(Material.IRON_BLOCK,Material.IRON_BLOCK);
        drops.put(Material.IRON_ORE,Material.IRON_ORE);
        drops.put(Material.CRYING_OBSIDIAN,Material.CRYING_OBSIDIAN);
        drops.put(Material.GOLD_BLOCK,Material.GOLD_BLOCK);
        drops.put(Material.GOLD_ORE,Material.GOLD_INGOT);
        drops.put(Material.CYAN_WOOL,Material.CYAN_WOOL);
        drops.put(Material.TINTED_GLASS,Material.TINTED_GLASS);
        drops.put(Material.NETHERITE_BLOCK,Material.NETHERITE_BLOCK);
        drops.put(Material.ANCIENT_DEBRIS,Material.ANCIENT_DEBRIS);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (drops.containsKey(event.getBlock().getType())){
            int fortuneLvl = getFortuneLvl(event);
            int dropNumber = 1;
            if (fortuneLvl > 0 ) {
                Random random= new Random();
                dropNumber = random.nextInt(fortuneLvl + 1) + 1;
            }
            Player player = event.getPlayer();
            ItemStack item = new ItemStack(drops.get(event.getBlock().getType()),dropNumber);
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(item);
            } else {
                player.getWorld().dropItem(player.getLocation(), item);
                player.sendMessage("Twój ekwipunek jest pełny, " + item.getType().toString() + " upadł na ziemię!");
            }
            event.setDropItems(false);
        }
    }
    @EventHandler
    public void playerKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player){
            if (event.getEntity().getKiller() != null){
                event.getDrops().clear();
                Player killer = event.getEntity().getKiller();
                if (player.getUniqueId().equals(killer.getUniqueId())){
                    return;
                }
                List<ItemStack> itemsToGiveAfterRespawn = new ArrayList<>();
                for (ItemStack itemStack : player.getInventory().getContents()){
                    if (itemStack == null) {
                        continue;
                    }
                    if (random.nextBoolean()){
                        itemsToGiveAfterRespawn.add(itemStack);
                        continue;
                    }
                    if (killer.getInventory().firstEmpty() != -1) {
                        killer.getInventory().addItem(itemStack);
                    } else {
                        killer.getWorld().dropItem(player.getLocation(), itemStack);
                        killer.sendMessage("Twój ekwipunek jest pełny, " + itemStack.getType().toString() + " upadł na ziemię!");
                    }
                }
                playerRespawnItems.put(player.getUniqueId(), itemsToGiveAfterRespawn);
            } else {
                List<ItemStack> itemsToGiveAfterRespawn = new ArrayList<>();
                for (ItemStack itemStack : player.getInventory().getContents()){
                    if (itemStack == null) {
                        continue;
                    }
                    if (random.nextBoolean()){
                        itemsToGiveAfterRespawn.add(itemStack);
                    }
                }
                playerRespawnItems.put(player.getUniqueId(), itemsToGiveAfterRespawn);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        List<ItemStack> itemsToGive = playerRespawnItems.get(event.getPlayer().getUniqueId());
        if (itemsToGive != null && !itemsToGive.isEmpty()) {
            for (ItemStack item : itemsToGive) {
                if (item != null) {
                    event.getPlayer().getInventory().addItem(item);
                }
            }
        }
        playerRespawnItems.remove(event.getPlayer().getUniqueId());
    }

    private int getFortuneLvl(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        if(meta != null) {
            return meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
        }
        return 0;
    }
}
