package com.locydragon.rli.nms;

import com.locydragon.rli.util.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemNBTSetGet {
	private static String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	private static Class<?> craftItemClass = null;
	private static Class<?> nmsItemClass = null;
	private static Class<?> nbtCore = null;
	private static Class<?> nbtList = null;
	private static Class<?> nbtBase = null;

	static {
		try {
			Bukkit.getLogger().info("[Rli-DebugSection]Is Ignore NMS:"
					+ !(ParticleEffect.ParticlePacket.getVersion() < 17) + "|"
					+ ParticleEffect.ParticlePacket.getVersion());
			if (ParticleEffect.ParticlePacket.getVersion() < 17) {
				craftItemClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
				nmsItemClass = Class.forName("net.minecraft.server." + version + ".ItemStack");
				nbtCore = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
				nbtList = Class.forName("net.minecraft.server." + version + ".NBTTagList");
				nbtBase = Class.forName("net.minecraft.server." + version + ".NBTBase");
			} else {
				craftItemClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
				nmsItemClass = Class.forName("net.minecraft.world.item.ItemStack");
				nbtCore = Class.forName("net.minecraft.nbt.NBTTagCompound");
				nbtList = Class.forName("net.minecraft.nbt.NBTTagList");
				nbtBase = Class.forName("net.minecraft.nbt.NBTBase");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ItemStack addPluginTag(ItemStack item, String ID) {
		Object nmsItem = null;
		try {
			if (ParticleEffect.ParticlePacket.getVersion() < 18) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("setString", new Class[]{String.class, String.class}).invoke(tag, new Object[]{"LOCYITEM", ID});
				nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
			} else if (ParticleEffect.ParticlePacket.getVersion() < 20) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Method m1 = nmsItemClass.getMethod("u");
				m1.setAccessible(true);
				Object tag = m1.invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("a",
						new Class[]{String.class, String.class}).invoke(tag, new Object[]{"LOCYITEM", ID});
				Method m2 = nmsItemClass.getMethod("c", nbtCore);
				m2.setAccessible(true);
				m2.invoke(nmsItem, tag);
			} else if (ParticleEffect.ParticlePacket.getVersion() == 20) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Method m1 = nmsItemClass.getMethod("v");
				m1.setAccessible(true);
				Object tag = m1.invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("a",
						new Class[]{String.class, String.class}).invoke(tag, new Object[]{"LOCYITEM", ID});
				Method m2 = nmsItemClass.getMethod("c", nbtCore);
				m2.setAccessible(true);
				m2.invoke(nmsItem, tag);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static ItemStack unbreakable(ItemStack item) {
		Object nmsItem = null;
		try {
			if (ParticleEffect.ParticlePacket.getVersion() < 18) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("setByte", new Class[]{String.class, byte.class}).invoke(tag, new Object[]{"Unbreakable", (byte) 1});
				nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
			} else if (ParticleEffect.ParticlePacket.getVersion() < 20) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Object tag = nmsItemClass.getMethod("u").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("a", new Class[]{String.class, byte.class}).invoke(tag, new Object[]{"Unbreakable", (byte) 1});
				nmsItemClass.getMethod("c", nbtCore).invoke(nmsItem, tag);
			} else if (ParticleEffect.ParticlePacket.getVersion() == 20) {
				nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Object tag = nmsItemClass.getMethod("v").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
				nbtCore.getMethod("a", new Class[]{String.class, byte.class}).invoke(tag, new Object[]{"Unbreakable", (byte) 1});
				nmsItemClass.getMethod("c", nbtCore).invoke(nmsItem, tag);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static ItemStack setInt(ItemStack item, String key, double value) {
		Object nmsItem = null;
		try {
			nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			Object tag = null;
			if (ParticleEffect.ParticlePacket.getVersion() < 18) {
				tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
			} else {
				tag = nmsItemClass.getMethod("u").invoke(nmsItem);
				if (tag == null) {
					try {
						tag = nbtCore.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
			}
			Object list = nbtList.cast(nbtCore.getMethod("get", String.class).invoke(tag, new Object[] {"AttributeModifiers"}));
			if (list == null) {
				try {
					list = nbtList.cast(nbtList.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			try {
				Object tagInstance = nbtCore.newInstance();
				nbtCore.getMethod("setString", new Class[] {String.class, String.class}).invoke(tagInstance,
						new Object[] {"AttributeName", key});
				nbtCore.getMethod("setString", new Class[] {String.class, String.class}).invoke(tagInstance,
						new Object[] {"Name", key.split("\\.", 2)[1]});
				nbtCore.getMethod("setDouble", new Class[] {String.class, double.class}).invoke(tagInstance,
						new Object[] {"Amount", value});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"Operation", 0});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"UUIDLeast", 20000});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"UUIDMost", 1000});
				try {
					nbtList.getMethod("add", nbtBase).invoke(list,
							new Object[]{tagInstance});
				} catch (NoSuchMethodException ex) {
					//more than 1.15.2
					nbtList.getMethod("add", int.class, nbtBase).invoke(list,
							new Object[]{0, tagInstance});
				}
				nbtCore.getMethod("set", String.class, nbtBase).invoke(tag, "AttributeModifiers", list);
				if (ParticleEffect.ParticlePacket.getVersion() < 18) {
					nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
				} else {
					nmsItemClass.getMethod("setTagClone", nbtCore).invoke(nmsItem, tag);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static String getPluginTag(ItemStack item) {
		try {
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			Object tag = null;
			if (ParticleEffect.ParticlePacket.getVersion() < 18) {
				tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
			} else if (ParticleEffect.ParticlePacket.getVersion() < 20) {
				tag = nmsItemClass.getMethod("u").invoke(nmsItem);
			} else if (ParticleEffect.ParticlePacket.getVersion() == 20) {
				tag = nmsItemClass.getMethod("v").invoke(nmsItem);
			}

			if (tag == null) {
				return null;
			}
			Object obj = null;
			if (ParticleEffect.ParticlePacket.getVersion() < 18) {
				obj = nbtCore.getMethod("get", String.class).invoke(tag, "LOCYITEM");
			} else {
				obj = nbtCore.getMethod("c", String.class).invoke(tag, "LOCYITEM");
			}
			if (obj == null) {
				return null;
			}
			try {
				if (ParticleEffect.ParticlePacket.getVersion() < 17) {
					Field field = obj.getClass().getDeclaredField("data");
					field.setAccessible(true);
					return (String) field.get(obj);
				} else if (ParticleEffect.ParticlePacket.getVersion() == 17) {
					return (String) obj.getClass().getMethod("asString").invoke(obj);
				} else if (ParticleEffect.ParticlePacket.getVersion() > 17) {
					Field field = obj.getClass().getDeclaredField("A");
					field.setAccessible(true);
					return (String) field.get(obj);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
