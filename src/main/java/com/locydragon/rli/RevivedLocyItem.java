package com.locydragon.rli;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.init.*;
import com.locydragon.rli.runnable.ItemSyncRunnable;
import com.locydragon.rli.supporterMM.MythicmobsExecutor;
import com.locydragon.rli.util.old.VersionHelper;
import com.locydragon.rli.util.particle.ParticleEffect;
import com.locydragon.rli.util.secure.OpCmdExecutor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author LocyDragon
 */
public class RevivedLocyItem extends JavaPlugin {
	public static FileConfiguration mainConfiguration = null;
	public static ConfigMaster configMaster = null;
	public static RevivedLocyItem instance;
	public static boolean isCitizensOnServer = false;
	public static boolean enableOverParticleLib = false;
	public static ProtocolManager manager;

	public static boolean launchOnCauldron = false;

	@Override
	public void onEnable() {
		instance = this;
		/** Init **/
		ParticleEffect.values();

		try {
			manager = ProtocolLibrary.getProtocolManager();
		} catch (Throwable e) {
			getLogger().info("× 版本过低(less than 1.8.0)或您未安装ProtoclLib");
		}
		/** 输出插件信息 **/
		new InfoLogger(this).printOutLog();
		/** 加载配置文件 **/
		configMaster = new ConfigMaster(this);
		configMaster.initConfig();
		/** 注册指令 **/
		new ListenerRegisters(this).registerListeners();
		/** 注册条件事件 **/
		new ConditionRegisters().init();
		/** 注册指令呀 **/
		Bukkit.getPluginCommand("rli").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("rlitem").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("revivedlocyitem").setExecutor(new CommandDiverter());
		/** 初始化编辑者模式 **/
		EditorInventoryIniter.initMainInventory();

		SubCommandRegister.registerSubCommands();/** 注册子命令呀 **/
		OpCmdExecutor.init();

		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			getLogger().info("× Failed to find plugin PlaceholderAPI……");
			getLogger().info("× Plugin disabled automatically……");
			getLogger().info("× 我们没能找到 PlaceholderAPI 这个插件……");
			getLogger().info("× 插件自动地关闭了……");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		if (getConfig().getInt("SyncDelay") * 20 > 0) {
			new ItemSyncRunnable().runTaskTimer(this, 0
					, getConfig().getInt("SyncDelay") * 20);
		}

		getLogger().info("√ Sync: " + getConfig().getInt("SyncDelay") * 20);

		isCitizensOnServer = Bukkit.getPluginManager().isPluginEnabled("Citizens");
		getLogger().info("√ (Server Version)Load On:" + ParticleEffect.ParticlePacket.getVersion());
		getLogger().info("√ 粒子效果库A支持性：" + (ParticleEffect.ParticlePacket.getVersion() < 13));
		if (!(ParticleEffect.ParticlePacket.getVersion() < 13)) {
			getLogger().info("√ 将启用ProtcolLib粒子库(Version>1.12.2)");
			enableOverParticleLib = true;
		}
		new Metrics(this);

		VersionHelper.getLatestVersion();
		calculateCauldron();
		if (launchOnCauldron) {
			getLogger().info("√ 检测到您使用Cauldron/Uranium/Mohist服务端……开启低版本Mod服兼容");
		} else {
			getLogger().info("× 您未使用Cauldron/Uranium/Mohist服务端……自动关闭低版本Mod服兼容");
		}
		if (!launchOnCauldron && ParticleEffect.ParticlePacket.getVersion() < 8) {
			getLogger().info("√ 检测到您使用低版本(less than 1.8.0)但未使用Mod服务端，很抱歉，本插件暂不支持.");
			getLogger().info("× 插件自动关闭……");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
			getLogger().info("√ 检测到MythicMobs插件，已开启兼容引擎……");
			Bukkit.getPluginManager().registerEvents(new MythicmobsExecutor(), this);
		} else {
			getLogger().info("× 暂未检测到MythicMobs插件，已关闭兼容引擎……");
			getLogger().info("MythicMobs兼容引擎有什么功能？");
			getLogger().info("1.可以支持您在mm怪物上运行本插件的粒子效果组.");
		}
	}

	public static void calculateCauldron() {
		try {
			Class.forName("cpw.mods.fml.common.Mod");
			launchOnCauldron = true;
		} catch (Throwable e) {
			launchOnCauldron = false;
		}
	}
}
