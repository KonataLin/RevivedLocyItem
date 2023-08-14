package com.locydragon.rli.init;

import org.bukkit.plugin.Plugin;

public class InfoLogger {
	private Plugin main;
	public InfoLogger(Plugin main) {
		this.main = main;
	}

	public void addOnEnableMsg(String msg) {
		main.getLogger().info(msg);
	}

	public void printOutLog() {
		printDraw();
		addOnEnableMsg("===========[RevivedLocyItem——欢迎语]===========");
		addOnEnableMsg("欢迎使用重生版RPG武器插件 RevivedLocyItem");
		addOnEnableMsg("作者: LocyDragon QQ2424441676");
		addOnEnableMsg("论坛id: MagicLocyDragon");
		addOnEnableMsg("请诸位听我一句诗: ");
		addOnEnableMsg("苟利国家生死以,岂因祸福避趋之");
		addOnEnableMsg("当你看到这个消息，你的寿命已经-1s了.");
		addOnEnableMsg("§bPluginsCDTribe-Group");
		addOnEnableMsg("===========[RevivedLocyItem——欢迎语]===========");
	}

	public void printDraw() {
		addOnEnableMsg(" ____        __         ______     ");
		addOnEnableMsg("/\\  _`\\     /\\ \\       /\\__  _\\    ");
		addOnEnableMsg("\\ \\ \\ \\ \\   \\ \\ \\      \\/_/\\ \\/    ");
		addOnEnableMsg(" \\ \\ ,  /    \\ \\ \\  __    \\ \\ \\    ");
		addOnEnableMsg("  \\ \\ \\\\ \\    \\ \\ \\ \\ \\    \\_\\ \\__ ");
		addOnEnableMsg("   \\ \\_\\ \\_\\   \\ \\____/    /\\_____\\");
		addOnEnableMsg("    \\/_/\\/ /    \\/___/     \\/_____/");
		addOnEnableMsg("");
	}
}
