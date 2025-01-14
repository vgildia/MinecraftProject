package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CheckPlayer implements Listener, CommandExecutor {

    private final Plugin plugin;
    public CheckPlayer(Plugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sprawdz")) {
            check(args, sender);

        } else if (command.getName().equalsIgnoreCase("sprawdzczysty")) {
            String playerName = args[0];
            Player player = Bukkit.getPlayer(playerName);
            // tutaj co ma sie stac kiedy gracz jest czysty
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user %player% parent remove sprawdzany".replace("%player%",playerName));

        }
        return true;
    }

    private void check(String[] args, CommandSender sender) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Poprawne użycie: /sprawdz nick");
            return;
        }
        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null){
            sender.sendMessage(ChatColor.RED + "Gracz nie jest online");
            return;
        }
        Location location = plugin.getConfig().getLocation("Check-Player-Location");
        if(location == null){
            location = player.getWorld().getSpawnLocation();
        }
        player.teleport(location);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user %player% parent add sprawdzany".replace("%player%",playerName));
        if (sender instanceof Player admin) {
            admin.teleport(location);
        }
    }


    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent event){
        if (event.getMessage().equalsIgnoreCase("/przyznajesie")){
            return;
        }
        if(!event.getPlayer().isOp() && event.getPlayer().hasPermission("sprawdzany")){
            event.setCancelled(true);
        }
    }
}
