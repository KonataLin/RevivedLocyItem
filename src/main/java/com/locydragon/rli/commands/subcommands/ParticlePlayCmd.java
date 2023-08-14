package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.ParticleUnit;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class ParticlePlayCmd implements SubCmd {
	@Override
	public void runSubCommand(Player commandSender, String[] arguments) {
		if (!commandSender.hasPermission("RevivedLocyItem.admin")) {
			return;
		}
		if (arguments.length == 2) {
			if (LocyItemAPI.hasParticleEffect(arguments[1])) {
				ParticleUnit unit = LocyItemAPI.getEffect(arguments[1]);
				unit.draw(commandSender.getLocation(), commandSender.getEyeLocation().getDirection());
			} else {
				String msg = ConfigLinker.readConfigLang
						("NotFound", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
				commandSender.sendMessage(msg);
			}
		} else {
			String msg = ConfigLinker.readConfigLang
					("Particle", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
			commandSender.sendMessage(msg);
		}
	}

	@Override
	public String tellMeCmdPrefix() {
		return "p";
	}
}
