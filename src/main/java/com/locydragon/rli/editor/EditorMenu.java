package com.locydragon.rli.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.HashMap;

public class EditorMenu {
	private MenuTag tag;
	private String menuDetail;
	private Inventory mainInventory;
	private HashMap<Integer,EditorMenu> editorMenuList = new HashMap<>();
	public EditorMenu(Inventory inventory, MenuTag tag, String detail) {
		this.mainInventory = inventory;
		this.tag = tag;
		this.menuDetail = detail;
	}

	public void open(Player entity) {
		entity.openInventory(mainInventory);
	}

	public void setSlotMenu(int slotLocation, EditorMenu menuNext) {
		editorMenuList.put(slotLocation, menuNext);
	}

	public EditorMenu whenClick(int slotLocation) {
		return editorMenuList.getOrDefault(slotLocation, null);
	}

	public Inventory targetInventory() {
		return mainInventory;
	}

	public MenuTag getTag() {
		return this.tag;
	}

	public String details() {
		return this.menuDetail;
	}
}
