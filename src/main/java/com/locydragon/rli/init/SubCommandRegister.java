package com.locydragon.rli.init;

import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.commands.subcommands.*;

public class SubCommandRegister {
	public static void registerSubCommands() {
		CommandDiverter.addSubBasicCommand(new VersionHelperCmd());
		CommandDiverter.addSubBasicCommand(new ItemRewardCmd());
		CommandDiverter.addSubBasicCommand(new ItemRewardCmdCopy());
		CommandDiverter.addSubBasicCommand(new ReloadCmd());
		CommandDiverter.addSubBasicCommand(new ParticlePlayCmd());
		CommandDiverter.addSubBasicCommand(new ParticlePlayCmdCopy());
		CommandDiverter.addSubBasicCommand(new SkillPlayerCmd());
		CommandDiverter.addSubBasicCommand(new ItemListCmd());
		CommandDiverter.addSubBasicCommand(new HelpCmd());
		CommandDiverter.addSubBasicCommand(new AsItemCmd());
	}
}
