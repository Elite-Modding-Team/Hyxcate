package de.ellpeck.nyx.mixin.common;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface NyxEntityAccessor {
    @Accessor("isImmuneToFire")
    void setIsImmuneToFire(boolean isImmuneToFire);
}
