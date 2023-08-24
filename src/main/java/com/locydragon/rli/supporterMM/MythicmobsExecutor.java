package com.locydragon.rli.supporterMM;

import com.locydragon.rli.RevivedLocyItem;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicmobsExecutor implements Listener {
    @EventHandler
    public void onLoad(MythicMechanicLoadEvent e) {
        if (e.getMechanicName().equalsIgnoreCase("RPARTICLE")) {
            e.register(new ParticleMechanic(e.getConfig()));
            RevivedLocyItem.instance.getLogger().info("√ 成功挂钩MythicMobs!");
        }
    }
}
