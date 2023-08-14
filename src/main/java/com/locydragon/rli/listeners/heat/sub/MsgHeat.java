package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.util.Colors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MsgHeat implements Listener  {
    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("msg")) {
            e.getPlayer().sendMessage(Colors.color(e.getOption().getValue(null, "msg", "m")));
        }
    }
}
