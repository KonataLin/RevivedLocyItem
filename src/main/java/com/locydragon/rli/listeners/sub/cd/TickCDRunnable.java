package com.locydragon.rli.listeners.sub.cd;

import com.locydragon.rli.listeners.sub.SkillListExecutor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class TickCDRunnable implements Runnable {
	private String skillName;
	private String player;

	public TickCDRunnable(String skillName, Player who) {
		this.skillName = skillName;
		this.player = who.getName().toUpperCase();
	}

	@Override
	public void run() {
		while (SkillListExecutor.CDAcheMap.get(this.skillName).containsKey(this.player)) {
			ConcurrentHashMap<String,Integer> valueMap = SkillListExecutor.CDAcheMap.get(this.skillName);
			Integer value = valueMap.get(player);
			value--;
			if (value <= 0) {
				valueMap.remove(this.player);
				SkillListExecutor.CDAcheMap.put(this.skillName, valueMap);
				break;
			} else {
				valueMap.put(this.player, value);
				SkillListExecutor.CDAcheMap.put(this.skillName, valueMap);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
