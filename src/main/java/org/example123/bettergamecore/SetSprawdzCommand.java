package org.example123.bettergamecore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SetSprawdzCommand implements CommandExecutor {
    private final Plugin plugin;
    public SetSprawdzCommand(Plugin plugin){
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        plugin.getConfig().set("Check-Player-Location",player.getLocation());
        player.sendMessage(ChatColor.GREEN + "Ustawiłes sprawdzarke");
        plugin.saveConfig();

        return true;
    }
}
