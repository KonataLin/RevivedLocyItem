package com.locydragon.rli.api;

import com.locydragon.rli.listeners.Condition;
import com.locydragon.rli.nms.ItemNBTSetGet;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**总API类
 * @author LocyDragon
 * @version 1.3.3
 */
public class LocyItemAPI {
	private static ConcurrentHashMap<String,LocyItem> itemStack = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,ParticleUnit> unitMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,SkillUnit> skillMap = new ConcurrentHashMap<>();
	protected static ConcurrentLinkedQueue<Condition> conditionRegistered = new ConcurrentLinkedQueue<>();

	/**通过技能组的名称获取一个技能组，返回null时则表明不存在
	 * @param name 技能组的名称
	 */
	public static SkillUnit getSkillUnit(String name) {
		if (!hasSkillUnit(name)) {
			return null;
		}
		return skillMap.get(name);
	}

	/**获取所有可用的物品列表
	 */
	public static Set<String> availableItems() {
		return itemStack.keySet();
	}

	/**判断是否存在一个技能组
	 * @param name 技能组的名称
	 */
	public static boolean hasSkillUnit(String name) { return skillMap.containsKey(name); }

	/**清空所有技能组
	 */
	public static void clearRegisteredSkill() {skillMap.clear();}

	/**注册一个技能组到LocyItem
	 */
	public static void registerSkillUnit(SkillUnit unit) { skillMap.put(unit.getName(), unit);}

	/**清空所有注册的粒子效果
	 */
	public static void clearRegisteredParticle() { unitMap.clear(); }


	/**判断是否存在一个粒子效果组
	 * @param name 粒子效果组的名称
	 */
	public static boolean hasParticleEffect(String name) { return unitMap.containsKey(name); };

	/**获取一个粒子效果组
	 * @param name 粒子效果组的名称
	 */
	public static ParticleUnit getEffect(String name) {
		if (!hasParticleEffect(name)) {
			return null;
		}
		return unitMap.get(name);
	}

	/**注册一个粒子效果组
	 * @param unit 你的粒子效果组
	 */
	public static void registerParticleEffect(ParticleUnit unit) { unitMap.put(unit.name, unit); }

	/**注册一个LocyItem
	 * @param item 你的技能物品
	 */
	public static void registerItem(LocyItem item) {
		itemStack.put(item.getID(), item);
	}

	/**通过名字来获取一个LocyItem
	 * @param ID 物品ID
	 */
	public static LocyItem getItem(String ID) {
		return itemStack.getOrDefault(ID, null);
	}

	/**清除所有已经注册的物品
	 */
	public static void clearRegisteredItem() {
		itemStack.clear();
	}

	/**判断一个ItemStack是否为LocyItem
	 * @param item 目标对象
	 */
	public static boolean isLocyItem(ItemStack item) {
		if (item == null || !item.hasItemMeta()) {
			return false;
		}
		String obj = ItemNBTSetGet.getPluginTag(item);
		return ItemNBTSetGet.getPluginTag(item) != null && itemStack.containsKey(obj);
	}

	/**获取一个ItemStack的LocyItem中ID，返回null表示不是LocyItem
	 * @param item 目标对象
	 */
	public static String getID(ItemStack item) {
		if (isLocyItem(item)) {
			return ItemNBTSetGet.getPluginTag(item);
		} else {
			return null;
		}
	}

	/**获取一个ItemStack的LocyItem中的实例，返回null表示不是LocyItem
	 * @param item 目标对象
	 */
	public static LocyItem asLocyItem(ItemStack item) {
		if (!isLocyItem(item)) {
			return null;
		}
		return getItem(getID(item));
	}

	/**注册一个SkillUnit条件
	 * @param condition 目标条件
	 */
	public static void registerCondition(Condition condition) { conditionRegistered.add(condition); }
}
