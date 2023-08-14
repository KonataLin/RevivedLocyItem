package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LightningEffectExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("lightning")) {
			e.getPlayer().getWorld().strikeLightningEffect(e.getPlayer().getLocation());
		}
	}
}
