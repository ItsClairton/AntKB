package dev.clairton.antkb;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {
    private WorldGuardPlugin wg;

    @Override
    public void onEnable() {
        wg = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Sistema ativado.");
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e) {
        if (isInRegion(getConfig().getStringList("regions"), e.getPlayer().getLocation())) {
            return;
        }

        e.setCancelled(true);
    }

    private boolean isInRegion(List<String> regions, Location loc) {
        for (ProtectedRegion rg : wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc)) {
            if (regions.contains(rg.getId())) {
                return true;
            }
        }

        return false;
    }

}
