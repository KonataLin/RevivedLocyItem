package com.locydragon.rli.api;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.listeners.Condition;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**技能组类
 * @author LocyDragon
 * @version 1.3.3
 */
public class SkillUnit {
	private static Executor executor = Executors.newCachedThreadPool();

	private ConcurrentLinkedQueue<String> skills = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<String> conditions = new ConcurrentLinkedQueue<>();
	private String name;
	private int coolDown;
	private String outMessage;

	public SkillUnit(String name, int coolDown, String outMessage) {
		this.name = name;
		this.coolDown = coolDown;
		this.outMessage = outMessage;
	}

	/**添加一个技能
	 * @param param 技能指令（与配置文件中一致）
	 */
	public void addSkill(String param) {
		skills.add(param);
	}

	/**添加一个条件
	 * @param param 条件指令（与配置文件中一致）
	 */
	public void addCondition(String param) { conditions.add(param); }

	/**获取ID
	 */
	public String getName() {
		return this.name;
	}

	/**获取冷却时间
	 */
	public int getCoolDown() {
		return this.coolDown;
	}

	/**获取CD消息
	 */
	public String getCDMessage() {
		return this.outMessage;
	}

	/**给玩家执行这个技能组
	 * @param who 目标玩家
	 * @param onItem 执行的LocyItem（可以为null）
	 */
	public void run(Player who, LocyItem onItem) {
		executor.execute(() -> {
			//DO:Check Conditions
			int i = 0;
			for (String condition : this.conditions) {
				i++;
				for (Condition conditionEach : LocyItemAPI.conditionRegistered) {
					LangReader lang = new LangReader(condition);
					OptionReader option = new OptionReader(lang.value());
					try {
						if (!conditionEach.check(who, lang.headValue(), option, onItem)) {
							String failMessage = option.getValue("null", "fail");
							if (!PlaceholderAPI.setPlaceholders(who, failMessage).equalsIgnoreCase("null")) {
								who.sendMessage(Colors.color(failMessage));
							}
							return;
						}
					} catch (Exception e) {
						who.sendMessage(Colors.color("&5条件错误在: &b&l"
								+ name + " &5的条件第 &b&l" + i + " &5行."));
						who.sendMessage("具体信息：" + e.getMessage());
						return;
					}
				}
			}
			//DO:Run skills
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
