package com.locydragon.rli.supporterMM;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.ParticleUnit;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import org.bukkit.entity.LivingEntity;

public class ParticleMechanic implements ITargetedEntitySkill {
    protected final String name;

    public ParticleMechanic(MythicLineConfig config) {
        this.name = config.getString(new String[]
                {"id", "name", "i", "n", "type", "t"}, "ExampleSkill");
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        LivingEntity bukkitTarget = (LivingEntity) BukkitAdapter.adapt(target);

        ParticleUnit unit = LocyItemAPI.getEffect(this.name);
        if (unit == null) {
            RevivedLocyItem.instance.getLogger()
                    .info("× MythicMobs挂钩错误：名称为 " + this.name + " 的粒子效果组不存在！请确认你已经重载配置文件了.");
        } else {
            unit.draw(bukkitTarget.getLocation(), bukkitTarget.getEyeLocation().getDirection());
        }

        return SkillResult.SUCCESS;
    }
}
