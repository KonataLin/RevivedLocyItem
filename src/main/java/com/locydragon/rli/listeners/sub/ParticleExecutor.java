package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParticleExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("particle")) {
			String name = e.getOption().getValue("UNKNOWN", "name", "n");
			if (LocyItemAPI.hasParticleEffect(name)) {
				LocyItemAPI.getEffect(name).draw(e.getPlayer().getLocation(), e.getPlayer().getEyeLocation().getDirection());
			}
		}
	}
}
