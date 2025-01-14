package org.example123.bettergamecore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PrzyznajeSieCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (!player.isOp() && player.hasPermission("no-logout")){
            Instant instant = LocalDateTime.now().plus(3, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant();
            player.banPlayer("Przyznanie sie do winy", Date.from(instant));
        }else {
            player.sendMessage(ChatColor.RED + "Nie jesteś sprawdzany wiec do czego sie przyznajesz ?");
        }

        return true;
    }
}
