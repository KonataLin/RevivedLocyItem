package com.locydragon.rli.api;

import com.locydragon.rli.nms.ItemNBTSetGet;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LocyItemAPI {
	private static ConcurrentHashMap<String,LocyItem> itemStack = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,ParticleUnit> unitMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String,SkillUnit> skillMap = new ConcurrentHashMap<>();

	public static SkillUnit getSkillUnit(String name) {
		if (!hasSkillUnit(name)) {
			return null;
		}
		return skillMap.get(name);
	}

	public static Set<String> availableItems() {
		return itemStack.keySet();
	}

	public static boolean hasSkillUnit(String name) { return skillMap.containsKey(name); }

	public static void clearRegisteredSkill() {skillMap.clear();}

	public static void registerSkillUnit(SkillUnit unit) { skillMap.put(unit.getName(), unit);}

	public static void clearRegisteredParticle() { unitMap.clear(); }

	public static boolean hasParticleEffect(String name) { return unitMap.containsKey(name); };

	public static ParticleUnit getEffect(String name) {
		if (!hasParticleEffect(name)) {
			return null;
		}
		return unitMap.get(name);
	}

	public static void registerParticleEffect(ParticleUnit unit) { unitMap.put(unit.name, unit); }

	public static void registerItem(LocyItem item) {
		itemStack.put(item.getID(), item);
	}

	public static LocyItem getItem(String ID) {
		return itemStack.getOrDefault(ID, null);
	}

	public static void clearRegisteredItem() {
		itemStack.clear();
	}

	public static boolean isLocyItem(ItemStack item) {
		if (item == null || !item.hasItemMeta()) {
			return false;
		}
		String obj = ItemNBTSetGet.getPluginTag(item);
		return ItemNBTSetGet.getPluginTag(item) != null && itemStack.containsKey(obj);
	}

	public static String getID(ItemStack item) {
		if (isLocyItem(item)) {
			return ItemNBTSetGet.getPluginTag(item);
		} else {
			return null;
		}
	}

	public static LocyItem asLocyItem(ItemStack item) {
		if (!isLocyItem(item)) {
			return null;
		}
		return getItem(getID(item));
	}
}
