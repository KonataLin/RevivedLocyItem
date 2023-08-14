package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.util.Colors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MsgExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("msg")) {
			e.getPlayer().sendMessage
					(Colors.color(e.getOption().getValue(null, "msg", "m")));
		}
	}
}
