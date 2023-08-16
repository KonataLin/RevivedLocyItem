package com.locydragon.rli.api;

import com.locydragon.rli.nms.ItemNBTSetGet;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**LocyItem实例类
 * @author LocyDragon
 * @version 1.3.3
 */
public class LocyItem {
	private ItemStack buildItem;
	private String id;
	private HashMap<String,List<OptionReader>> skillsMap = new HashMap<>();
	private Vector<String> heatAfter = new Vector<>();

	/**创建LocyItem的实例对象
	 * @param id 物品id
	 * @param name 物品显示名称
	 * @param material 物品材质
	 */
	public LocyItem(String id, String name, Material material) {
		buildItem = new ItemStack(material);
		ItemMeta meta = getMeta();
		meta.setDisplayName(Colors.color(name));
		setMeta(meta);
		this.id = id;
		this.buildItem = ItemNBTSetGet.addPluginTag(this.buildItem, id);
	}

	/**创建一条Hit指令
	 * @param object Hit与物品配置中的Hit一致
	 */
	public void putHeatAfter(String object) {
		this.heatAfter.add(object);
	}

	/**获取所有hit指令
	 */
	public Vector<String> getHeat() {
		return this.heatAfter;
	}

	/**设置LocyItem的ItemStack(一般不使用)
	 * @param item 目标物品（注意设置了后不会被保存）
	 */
	public void setBuildItem(ItemStack item) {
		this.buildItem = item;
	}

	/**获取LocyItem的ItemStack实例对象
	 */
	public ItemStack getItem() {
		return this.buildItem;
	}

	/**获取LocyItem的ID名称
	 */
	public String getID() {
		return this.id;
	}

	/**设置Item的lore信息
	 * @param lore lore信息
	 */
	public void setLore(List<String> lore) {
		ItemMeta meta = getMeta();
		meta.setLore(lore);
		setMeta(meta);
	}

	/**给物品附魔
	 * @param en 附魔类型
	 * @param level 附魔等级
	 */
	public void enchantment(Enchantment en, int level) {
		this.buildItem.addUnsafeEnchantment(en, level);
	}

	/**添加技能
	 * @param param 技能指令（与配置文件中一致）
	 */
	public void addSkill(String param) {
		LangReader langReader = new LangReader(param);
		OptionReader reader = new OptionReader(langReader.value());
		List<OptionReader> readerList = skillsMap.getOrDefault(langReader.headValue().trim().toLowerCase(), new ArrayList<>());
		readerList.add(reader);
		skillsMap.put(langReader.headValue().trim().toLowerCase(), readerList);
	}

	private ItemMeta getMeta() {
		return buildItem.getItemMeta();
	}

	private void setMeta(ItemMeta meta) {
		buildItem.setItemMeta(meta);
	}

	/**获取所有技能（读取之后的）
	 */
	public HashMap<String, List<OptionReader>> getSkillsMap() {
		return this.skillsMap;
	}
}
