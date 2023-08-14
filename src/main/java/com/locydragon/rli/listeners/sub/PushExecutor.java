package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PushExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("push")) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection()
					.multiply(Double.valueOf(e.getOption().getValue("-0.6", "deltaPlane", "dp"))).setY(Double.valueOf(
							e.getOption().getValue("0.3", "deltaHeight", "dh"))));
		}
	}
}
