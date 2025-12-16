package de.ellpeck.nyx.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class NyxDamageSource extends EntityDamageSource {
    public static final DamageSource DEEP_FREEZE = new DamageSource("nyx_deep_freeze").setDamageBypassesArmor();
    public static final DamageSource INFERNO = new DamageSource("nyx_inferno").setDamageBypassesArmor(); // It's fire but we don't want fire immune mobs to completely protect against it lol
    public static final DamageSource PARALYSIS = new DamageSource("nyx_paralysis").setDamageBypassesArmor();
    
    public NyxDamageSource(String damageType, @Nullable Entity damageSourceEntity) {
        super(damageType, damageSourceEntity);
        this.damageType = damageType;
    }
}
