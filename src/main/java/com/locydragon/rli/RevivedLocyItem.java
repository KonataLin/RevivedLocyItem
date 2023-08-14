package com.locydragon.rli;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.init.*;
import com.locydragon.rli.runnable.ItemSyncRunnable;
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

	@Override
	public void onEnable() {
		/** Init **/
		ParticleEffect.values();

		manager = ProtocolLibrary.getProtocolManager();
		/** 输出插件信息 **/
		new InfoLogger(this).printOutLog();
		/** 加载配置文件 **/
		configMaster = new ConfigMaster(this);
		configMaster.initConfig();
		/** 注册指令 **/
		new ListenerRegisters(this).registerListeners();
		/** 注册指令呀 **/
		Bukkit.getPluginCommand("rli").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("rlitem").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("revivedlocyitem").setExecutor(new CommandDiverter());
		/** 初始化编辑者模式 **/
		EditorInventoryIniter.initMainInventory();

		SubCommandRegister.registerSubCommands();/** 注册子命令呀 **/
		instance = this;
		OpCmdExecutor.init();

		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			getLogger().info("Failed to find plugin PlaceholderAPI……");
			getLogger().info("Plugin disabled automatically……");
			getLogger().info("我们没能找到 PlaceholderAPI 这个插件……");
			getLogger().info("插件自动地关闭了……");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		if (getConfig().getInt("SyncDelay") * 20 > 0) {
			new ItemSyncRunnable().runTaskTimer(this, 0
					, getConfig().getInt("SyncDelay") * 20);
		}

		getLogger().info("Sync: " + getConfig().getInt("SyncDelay") * 20);

		isCitizensOnServer = Bukkit.getPluginManager().isPluginEnabled("Citizens");
		getLogger().info("(Server Version)Load On:" + ParticleEffect.ParticlePacket.getVersion());
		getLogger().info("粒子效果库A支持性：" + (ParticleEffect.ParticlePacket.getVersion() < 13));
		if (!(ParticleEffect.ParticlePacket.getVersion() < 13)) {
			getLogger().info("将启用ProtcolLib粒子库(Version>1.12.2)");
			enableOverParticleLib = true;
		}
		new Metrics(this);
	}
}
