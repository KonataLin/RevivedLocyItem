package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.nms.AudioReflexTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AudioHeat implements Listener {
    @EventHandler
    public void core(HeatCore c) {
        if (c.getSkillType().equalsIgnoreCase("audio")) {
            AudioReflexTable.play(c.getPlayer(),
                    c.getOption().getValue("TestMusic", "name", "n"));
        }
    }
}
