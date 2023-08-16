package com.locydragon.rli.api;

import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**技能造成伤害类，注意这个事件被取消了不会取消LocyItem的伤害事件
 * 请取消EntityDamageByEntity事件
 * 该事件在near、reach、box技能时会被call
 * @author LocyDragon
 * @version 1.3.3
 */
public class ItemCauseDamageEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private String event;
	private OptionReader reader;
	private LocyItem item;
	private UUID eventID;
	private double damage;
	private Entity who;

	public ItemCauseDamageEvent(Player player, String event, OptionReader reader, LocyItem item, double damage, Entity who) {
		this.player = player;
		this.event = event;
		this.reader = reader;
		this.item = item;
		eventID = UUID.randomUUID();
		this.damage = damage;
		this.who = who;
	}

	/**获取被伤害的对象
	 */
	public Entity getWho () {
		return this.who;
	}

	/**获取伤害
	 */
	public double getDamage() {
		return this.damage;
	}

	/**设置伤害（不会有用）
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}

	/**获取事件ID
	 */
	public UUID getEventID() {
		return this.eventID;
	}

	/**获取触发该事件的物品（可能为null，当用指令触发时）
	 */
	public LocyItem getOnUseItem() {
		return this.item;
	}

	/**获取触发的参数
	 */
	public OptionReader getOption() {
		return this.reader;
	}

	/**获取EventType
	 */
	public String getEventType() {
		return this.event;
	}

	/**获取造成伤害的玩家
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
