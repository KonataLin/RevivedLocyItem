package com.locydragon.rli.nms;

import com.locydragon.rli.RevivedLocyItem;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NpcUtils {
	private static Class<?> entryClass = null;
	private static Method getAPIMethod = null;
	private static Class<?> apiClass = null;
	private static Method autoMethod = null;

	public static boolean isNPC(Entity entity) {
		if (!RevivedLocyItem.isCitizensOnServer) {
			return false;
		}
		if (entryClass == null) {
			try {
				entryClass = Class.forName("net.citizensnpcs.api.CitizensAPI");
				try {
					getAPIMethod = entryClass.getMethod("getNPCRegistry");
					apiClass = Class.forName("net.citizensnpcs.api.npc.NPCRegistry");
					autoMethod = apiClass.getMethod("isNPC", Entity.class);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			Object api = getAPIMethod.invoke(null);
			return (boolean) autoMethod.invoke(api, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
}
