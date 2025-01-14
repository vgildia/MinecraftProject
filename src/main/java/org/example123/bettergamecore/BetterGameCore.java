package org.example123.bettergamecore;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterGameCore extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("gamma").setExecutor(new Gamma());
        getServer().getPluginManager().registerEvents(new BlockMap(this),this);
        getServer().getPluginManager().registerEvents(new DropToEq(this),this);
        getServer().getPluginManager().registerEvents(new AntyGrief(this),this);
        getCommand("sprawdz").setExecutor(new CheckPlayer(this));
        getCommand("sprawdzczysty").setExecutor(new CheckPlayer(this));
        getCommand("setsprawdz").setExecutor(new SetSprawdzCommand(this));
        getCommand("przyznajesie").setExecutor(new PrzyznajeSieCommand());
        getCommand("tempban").setExecutor(new TempBan());

        getLogger().info(ChatColor.GREEN + "BetterGameCore.java został wlaczony");
        getServer().getPluginManager().registerEvents(new CheckPlayer(this),this);
        getServer().getPluginManager().registerEvents(new SprawdzanyListener(),this);
        getServer().getPluginManager().registerEvents(new Anvil(),this);
        getServer().getPluginManager().registerEvents(new JoinMessage(), this);
        getServer().getPluginManager().registerEvents(new BlockedPlaceTintedGlass(), this);
        getServer().getPluginManager().registerEvents(new AntyCobweb(), this);


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
