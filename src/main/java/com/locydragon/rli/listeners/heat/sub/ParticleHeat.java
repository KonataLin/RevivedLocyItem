package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.listeners.heat.HeatCore;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParticleHeat implements Listener  {
    @EventHandler
    public void core(HeatCore e) {
        if (!(e.getOnEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity entityI = (LivingEntity) e.getOnEntity();
        if (e.getSkillType().equalsIgnoreCase("particle")) {
            String name = e.getOption().getValue("UNKNOWN", "name", "n");
            if (e.getOption().getWhen().equalsIgnoreCase("target")) {
                if (LocyItemAPI.hasParticleEffect(name)) {
                    LocyItemAPI.getEffect(name).draw(e.getOnEntity().getLocation()
                            , entityI.getEyeLocation().getDirection());
                }
            } else if (e.getOption().getWhen().equalsIgnoreCase("self")) {
                if (LocyItemAPI.hasParticleEffect(name)) {
                    LocyItemAPI.getEffect(name).draw(e.getOnEntity().getLocation()
                            , entityI.getEyeLocation().getDirection());
                }
            }
        }
    }
}
