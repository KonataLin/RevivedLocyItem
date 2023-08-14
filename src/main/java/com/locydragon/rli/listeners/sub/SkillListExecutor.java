package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.api.SkillUnit;
import com.locydragon.rli.listeners.sub.cd.TickCDRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SkillListExecutor implements Listener {
	public static ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> CDAcheMap = new ConcurrentHashMap<>();
	public static Executor executor = Executors.newCachedThreadPool();

	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("skill")) {
			String name = e.getOption().getValue("NONE", "name", "n");
			if (LocyItemAPI.hasSkillUnit(name)) {
				SkillUnit unit = LocyItemAPI.getSkillUnit(name);
				if (getSkillCD(unit.getName(), e.getPlayer()) > 0 && unit.getCoolDown() > 0) {
					e.getPlayer().sendMessage(unit.getCDMessage().replace("{cd}",
							String.valueOf(getSkillCD(unit.getName(), e.getPlayer()))));
				} else {
					if (unit.getCoolDown() > 0) {
						ConcurrentHashMap<String, Integer> cd
								= CDAcheMap.getOrDefault(unit.getName(), new ConcurrentHashMap<>());
						cd.put(e.getPlayer().getName().toUpperCase(), unit.getCoolDown());
						CDAcheMap.put(unit.getName(), cd);
						executor.execute(new TickCDRunnable(unit.getName(), e.getPlayer()));
					}
					unit.run(e.getPlayer(), e.getOnUseItem());
				}
			}
		}
	}

	public static Integer getSkillCD(String skillName, Player who) {
		ConcurrentHashMap<String,Integer> cd = CDAcheMap.getOrDefault(skillName, new ConcurrentHashMap<>());
		return cd.getOrDefault(who.getName().toUpperCase(), -1);
	}
}
