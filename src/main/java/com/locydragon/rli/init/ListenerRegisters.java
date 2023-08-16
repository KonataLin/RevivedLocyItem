package com.locydragon.rli.init;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.editor.listeners.MainMenuListener;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.factory.OptionReaders;
import com.locydragon.rli.listeners.InteractFatherListener;
import com.locydragon.rli.listeners.heat.HeatFatherListener;
import com.locydragon.rli.listeners.heat.sub.*;
import com.locydragon.rli.listeners.sub.*;
import com.locydragon.rli.util.old.VersionHelper;
import com.locydragon.rli.util.secure.DisadvantageOpDefender;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerRegisters {
	private RevivedLocyItem registers;
	public ListenerRegisters(RevivedLocyItem plugin) {
		this.registers = plugin;
	}

	//TODO WRITE LISTENERS HERE
	public void registerListeners() {
		registerEventForBukkit(new DisadvantageOpDefender());
		registerEventForBukkit(new InteractFatherListener());
		registerEventForBukkit(new LaunchExecutor());
		registerEventForBukkit(new MainMenuListener());
		registerEventForBukkit(new OptionReaders());
		registerEventForBukkit(new OptionDamage());
		registerEventForBukkit(new CommandExecutor());
		registerEventForBukkit(new NearByExecutor());
		registerEventForBukkit(new ParticleExecutor());
		registerEventForBukkit(new MsgExecutor());
		registerEventForBukkit(new ReachExecutor());
		registerEventForBukkit(new PushExecutor());
		registerEventForBukkit(new LightningEffectExecutor());
		registerEventForBukkit(new AudioExecutor());
		registerEventForBukkit(new HeatFatherListener());
		registerEventForBukkit(new SkillListExecutor());
		registerEventForBukkit(new SoundExecutor());
		registerEventForBukkit(new DamageBoxExecutor());
		registerEventForBukkit(new TitleExecutor());
		registerEventForBukkit(new AudioHeat());
		registerEventForBukkit(new CommandHead());
		registerEventForBukkit(new ParticleHeat());
		registerEventForBukkit(new MsgHeat());
		registerEventForBukkit(new LineHeat());
		registerEventForBukkit(new LightningHeat());
		registerEventForBukkit(new PushHeat());
		registerEventForBukkit(new VersionHelper());
	}

	public void registerEventForBukkit(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, registers);
	}
}
