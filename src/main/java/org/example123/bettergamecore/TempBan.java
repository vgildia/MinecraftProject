package org.example123.bettergamecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TempBan implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try{
            //tempban player 10d/10m/10h Powod za cheatowanie
            String playerName = args[0];
            String time = args[1];
            LocalDateTime duration = parseTime(time);
            StringBuilder reason = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            Instant instant = duration.atZone(ZoneId.systemDefault()).toInstant();
            offlinePlayer.banPlayer(reason.toString(),Date.from(instant));

            return true;
        }catch (IllegalArgumentException exception){
            sender.sendMessage(ChatColor.RED + exception.getMessage());
        } return true;

    }

    private LocalDateTime parseTime(String time) {
        if (time.contains("m")){
            time = time.replace("m", "");
            int number = Integer.parseInt(time);
            return LocalDateTime.now().plus(number,ChronoUnit.MINUTES);
        }
        if (time.contains("h")){
            time = time.replace("h", "");
            int number = Integer.parseInt(time);
            return LocalDateTime.now().plus(number,ChronoUnit.HOURS);
        }
        if (time.contains("d")){
            time = time.replace("d", "");
            int number = Integer.parseInt(time);
            return LocalDateTime.now().plus(number,ChronoUnit.DAYS);
        }
        throw new IllegalArgumentException("Niepoprawny czas. Sprobuj: 10m/10h/10d");
    }
}
