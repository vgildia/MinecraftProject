package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SprawdzanyListener implements Listener {
    @EventHandler
    public void onLogOut(PlayerQuitEvent event){
        if (!event.getPlayer().isOp() && event.getPlayer().hasPermission("no-logout")){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"ban %s".replace("%s",event.getPlayer().getName()));
        }
    }
}
