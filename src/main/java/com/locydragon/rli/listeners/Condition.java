package com.locydragon.rli.listeners;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;

/**条件处理类
 * 别忘了在LocyItemAPI类中注册条件哦
 * @author LocyDragon
 * @version 1.3.3
 */
public interface Condition {
    /**当被处理条件时，检测这个条件是否可行
     * @param player 判断条件的玩家
     * @param head 条件的名称，如random、compare、permission
     * @param value 条件的值
     * @param onItem 触发条件使用的onItem（可能为null）
     * @return true当且仅当这个检测通过/false则表示不通过
     */
    public boolean check(Player player, String head, OptionReader value, LocyItem onItem);
}
