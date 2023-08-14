package com.locydragon.rli.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class AudioReflexTable {
	private static Class<?> target = null;

	static {
		try {
			target = Class.forName("com.locydragon.abf.api.AudioBufferAPI");
		} catch (ClassNotFoundException e) {}
	}

	public static void play(Player name, String music) {
		if (target == null) {
			return;
		}
		try {
			target.getMethod("playFor", new Class[] {Player.class, String.class}).invoke(null,
					new Object[] {name, music});
		} catch (IllegalAccessException |
				InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
