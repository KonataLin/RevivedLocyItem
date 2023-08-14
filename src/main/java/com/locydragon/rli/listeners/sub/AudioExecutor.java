package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.nms.AudioReflexTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AudioExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("audio")) {
			AudioReflexTable.play(e.getPlayer(),
					e.getOption().getValue("TestMusic", "name", "n"));
		}
	}
}
