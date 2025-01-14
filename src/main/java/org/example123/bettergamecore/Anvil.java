package org.example123.bettergamecore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Currency;

public class Anvil implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();
        if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.ANVIL) {
            event.setCancelled(true);
            // Gracz kliknął kowadło
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            ItemMeta meta = itemInHand.getItemMeta();
            int levels = player.getLevel();
            if (meta instanceof Damageable damageable) {
                if (player.getLevel() >= 3) {
                    player.setLevel(player.getLevel() - 3);
                        damageable.setDamage(0);
                        itemInHand.setItemMeta(damageable);

                        player.sendTitle(ChatColor.GREEN + "Naprawiłes przedmiot", " ");
                    } else{
                        player.sendTitle(ChatColor.RED + " ", "Nie masz wystarczająco dużo poziomów doświadczenia.");
                    }
                } else {
                    player.sendMessage("Tego przedmiotu nie da się naprawic");
                }

                // sprawdzic czy gracz ma 8 diamentow, jezeli tak (if) to naprawic przedmiot i zabrac diamenty
                // jezeli nie, to wyswietlic stosowny komunikat

            }

        }

    }


