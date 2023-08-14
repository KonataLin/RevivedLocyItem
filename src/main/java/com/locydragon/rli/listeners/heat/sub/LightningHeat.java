package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LightningHeat implements Listener {
    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("lightning")) {
            if (e.getOption().getWhen().equalsIgnoreCase("target")) {
                e.getOnEntity().getLocation().getWorld()
                        .strikeLightningEffect(e.getOnEntity().getLocation());
            } else if (e.getOption().getWhen().equalsIgnoreCase("self")) {
                e.getPlayer().getLocation().getWorld()
                        .strikeLightningEffect(e.getPlayer().getLocation());
            }
        }
    }
}
