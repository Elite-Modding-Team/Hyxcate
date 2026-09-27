package mod.emt.hyxcate.compat.urkazmoontools.mixin.client;

import com.urkaz.moontools.item.MoonClockItem;
import mod.emt.hyxcate.compat.urkazmoontools.HyxcateMoonPhaseGetter;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MoonClockItem.class)
public abstract class UMTMoonClockItemMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/urkaz/moontools/item/MoonClockItem;addPropertyOverride(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/item/IItemPropertyGetter;)V"))
    public void HyxcateUMTMoonClockItem(MoonClockItem instance, ResourceLocation resourceLocation, IItemPropertyGetter iItemPropertyGetter) {
        instance.addPropertyOverride(new ResourceLocation("moonphase"), new HyxcateMoonPhaseGetter());
    }
}
