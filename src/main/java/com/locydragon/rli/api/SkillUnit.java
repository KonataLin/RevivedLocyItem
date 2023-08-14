package com.locydragon.rli.api;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SkillUnit {
	private static Executor executor = Executors.newCachedThreadPool();

	private ConcurrentLinkedQueue<String> skills = new ConcurrentLinkedQueue<>();
	private String name;
	private int coolDown;
	private String outMessage;

	public SkillUnit(String name, int coolDown, String outMessage) {
		this.name = name;
		this.coolDown = coolDown;
		this.outMessage = outMessage;
	}

	public void addSkill(String param) {
		skills.add(param);
	}

	public String getName() {
		return this.name;
	}

	public int getCoolDown() {
		return this.coolDown;
	}

	public String getCDMessage() {
		return this.outMessage;
	}

	public void run(Player who, LocyItem onItem) {
		executor.execute(() -> {
			for (String skill : this.skills) {
				LangReader lang = new LangReader(skill);
				OptionReader option = new OptionReader(lang.value());
				if (lang.headValue().equalsIgnoreCase("delay")) {
					try {
						Thread.sleep(Long.valueOf(option.getInfo()));
						continue;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					Bukkit.getScheduler().runTask(RevivedLocyItem.instance, () -> {
						Bukkit.getPluginManager()
								.callEvent(new SkillExecuteEvent(who, lang.headValue(), option, onItem));
					});
				}
			}
		});
	}
}
