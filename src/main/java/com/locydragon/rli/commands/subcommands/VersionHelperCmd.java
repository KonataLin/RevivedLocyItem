package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class VersionHelperCmd implements SubCmd {
	@Override
	public void runSubCommand(Player commandSender, String[] arguments) {
		commandSender.sendMessage(ConfigLinker.readConfigLang("ShowVersion", "{version}"
				, RevivedLocyItem.instance.getDescription().getVersion()));
	}

	@Override
	public String tellMeCmdPrefix() {
		return "VERSION";
	}
}
