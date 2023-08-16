package com.locydragon.rli.api;

import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**技能执行事件
 * 如果你想新建一个技能，就监听这个事件
 * 具体写法请按照com.locydragon.rli.listeners.sub内事件的写法
 * @author LocyDragon
 * @version 1.3.3
 */
public class SkillExecuteEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private String event;
	private OptionReader reader;
	private LocyItem item;

	public SkillExecuteEvent(Player player, String event, OptionReader reader, LocyItem item) {
		this.player = player;
		this.event = event;
		this.reader = reader;
		this.item = item;
	}

	/**获取触发技能的物品
	 */
	public LocyItem getOnUseItem() {
		return this.item;
	}

	/**获取技能参数
	 */
	public OptionReader getOption() {
		return this.reader;
	}

	/**获取技能的名字，如near、box、title等
	 */
	public String getEventType() {
		return this.event;
	}

	/**获取触发技能的玩家
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
