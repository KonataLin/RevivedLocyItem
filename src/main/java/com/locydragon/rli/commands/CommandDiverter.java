package com.locydragon.rli.commands;

import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ExpressionHelper;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author LocyDragon
 */
public class CommandDiverter implements CommandExecutor {
	public static List<SubCmd> cmdAche = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Colors.color("&c请在游戏内输入指令，请勿在控制台输入指令."));
			return false;
		}
		if (!(sender.hasPermission("RevivedLocyItem.admin"))) {
			sender.sendMessage(Colors.color("&c你没有权限执行该指令."));
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color("&c请输入正确的指令.&c&l使用/rli help来查看您能使用的指令！"));
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color("&8&lTip: &b查看Rli官方Wiki获取更多…… &3&l↓↓↓"));
			sender.sendMessage(Colors.color(""));
			TextComponent message = new TextComponent(Colors.color("&f[&6&l点我跳转&f]"));
			message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://locydragon.github.io/wiki/#/"));
			message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT,
					new ComponentBuilder("点击访问官方Wiki").create()));
			((Player)sender).spigot().sendMessage(message);
			sender.sendMessage(Colors.color(""));
			sender.sendMessage(Colors.color("&c若您无法打开Wiki，请启动代理访问，以确保您能连接Github."));
			sender.sendMessage(Colors.color("&c若使用过程出现乱码，请将配置文件转换为 &bANSI &c编码&7(默认utf8)"));
			sender.sendMessage(Colors.color(""));
			TextComponent message2 = new TextComponent(Colors.color("&5作者: &b&l弱鸡绿毛怪 &7| &f[&6&l在MCBBS支持我吧&f]"));
			message2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mcbbs.net/forum.php?mod=viewthread&tid=908852"));
			message2.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT,
					new ComponentBuilder("点击跳转至MCBBS链接").create()));
			((Player)sender).spigot().sendMessage(message2);
			return false;
		}
		for (SubCmd subCmd : cmdAche) {
			if (subCmd.tellMeCmdPrefix().equalsIgnoreCase(args[0])) {
				subCmd.runSubCommand((Player)sender, args);
				return false;
			}
		}

		HashMap<String,Integer> similarityMap = new HashMap<>();
		for (SubCmd subCmd : cmdAche) {
			if (subCmd.tellMeCmdPrefix().length() <= 1) {
				continue;
			}
			similarityMap.put(subCmd.tellMeCmdPrefix(), (int)(ExpressionHelper.levenshtein(args[0]
					, subCmd.tellMeCmdPrefix()) * 100000));
		}
		Comparator<Map.Entry<String, Integer>> valCmp = new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue()-o1.getValue();
			}
		};
		List<Map.Entry<String, Integer>> list =
				new ArrayList<>(similarityMap.entrySet());
		Collections.sort(list,valCmp);
		String mostSimilar = list.get(0).getKey();
		sender.sendMessage(Colors.color("&c没有找到您想使用的指令……"));
		sender.sendMessage(Colors.color("&a我猜测您想使用: &6" + mostSimilar));

		return false;
	}

	public static void addSubBasicCommand(SubCmd cmd) {
		cmdAche.add(cmd);
	}
}
