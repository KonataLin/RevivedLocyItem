package com.example.plugin;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import org.bukkit.inventory.ItemStack;

public class ExampleAPIUse {
    public void useAPI() {
        LocyItem itemInstance = LocyItemAPI.getItem("ExampleItem");
        ItemStack itemStack = itemInstance.getItem();
    }

    public LocyItem asLocyItem(ItemStack item) {
        return LocyItemAPI.asLocyItem(item);
    }

    //SEE API Doc Also
}
