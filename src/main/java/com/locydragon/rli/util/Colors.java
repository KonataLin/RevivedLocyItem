package com.locydragon.rli.util;

import org.bukkit.ChatColor;

public class Colors {
	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
