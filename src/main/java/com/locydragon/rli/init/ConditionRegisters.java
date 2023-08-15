package com.locydragon.rli.init;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.listeners.condition.ConditionCompare;
import com.locydragon.rli.listeners.condition.ConditionHasPermission;
import com.locydragon.rli.listeners.condition.ConditionRandomness;

public class ConditionRegisters {
    public static void init() {
        LocyItemAPI.registerCondition(new ConditionRandomness());
        LocyItemAPI.registerCondition(new ConditionHasPermission());
        LocyItemAPI.registerCondition(new ConditionCompare());
    }
}
