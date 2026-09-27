package mod.emt.hyxcate.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

import javax.annotation.Nullable;

public class HyxcateDamageSource extends EntityDamageSource {
    public static final DamageSource CELESTIAL = new DamageSource("hyxcate_celestial").setDamageBypassesArmor().setDamageIsAbsolute();
    public static final DamageSource DEEP_FREEZE = new DamageSource("hyxcate_deep_freeze").setDamageBypassesArmor();
    public static final DamageSource INFERNO = new DamageSource("hyxcate_inferno").setDamageBypassesArmor(); // It's fire but we don't want fire immune mobs to completely protect against it lol
    public static final DamageSource LASER = new DamageSource("hyxcate_laser").setDamageBypassesArmor();
    public static final DamageSource PARALYSIS = new DamageSource("hyxcate_paralysis").setDamageBypassesArmor();

    public HyxcateDamageSource(String damageType, @Nullable Entity damageSourceEntity) {
        super(damageType, damageSourceEntity);
        this.damageType = damageType;
    }

    public static DamageSource causeIndirectLaserDamage(Entity source, @Nullable Entity indirectEntity) {
        return (new EntityDamageSourceIndirect("hyxcate_laser", source, indirectEntity)).setDamageBypassesArmor();
    }
}
