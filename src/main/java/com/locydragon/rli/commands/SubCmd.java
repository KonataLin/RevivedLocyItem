package com.locydragon.rli.commands;

import org.bukkit.entity.Player;

public interface SubCmd {
	void runSubCommand(Player commandSender, String arguments[]);
	String tellMeCmdPrefix();
}
