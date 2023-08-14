package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.nms.NpcUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PushHeat implements Listener {
    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("push")) {
            if (e.getOption().getWhen().equalsIgnoreCase("self")) {
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection()
                        .multiply(Double.valueOf(e.getOption().getValue("-0.6", "deltaPlane", "dp"))).setY(Double.valueOf(
                                e.getOption().getValue("0.3", "deltaHeight", "dh"))));
            } else if (e.getOption().getWhen().equalsIgnoreCase("target")) {
                if (NpcUtils.isNPC(e.getOnEntity())
                        || e.getOnEntity().getType() == EntityType.ARMOR_STAND
                        || e.getOnEntity().getType().equals(EntityType.ARMOR_STAND)) {
                    return;
                }
                e.getOnEntity().setVelocity(e.getOnEntity().getLocation().getDirection()
                        .multiply(Double.valueOf(e.getOption().getValue("-0.6", "deltaPlane", "dp"))).setY(Double.valueOf(
                                e.getOption().getValue("0.3", "deltaHeight", "dh"))));
            }
        }
    }
}
