package mod.emt.hyxcate.util.helpers;

import mod.emt.hyxcate.client.sound.HyxcateSoundBeamSword;
import mod.emt.hyxcate.client.sound.HyxcateSoundCelestialWarhammer;
import mod.emt.hyxcate.client.sound.HyxcateSoundFallenEntity;
import mod.emt.hyxcate.client.sound.HyxcateSoundFallingEntity;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoundHelper {
    @SideOnly(Side.CLIENT)
    public static void playClientSoundBeamSword(ItemStack stack) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HyxcateSoundBeamSword(stack));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallenStar(EntityItem entityItem) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HyxcateSoundFallenEntity(entityItem, HyxcateSoundEvents.ENTITY_STAR_IDLE.getSoundEvent(), 1F));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallingMeteor(Entity entity) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HyxcateSoundFallingEntity(entity, HyxcateSoundEvents.ENTITY_METEOR_FALLING.getSoundEvent(), 5F));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallingStar(Entity entity) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HyxcateSoundFallingEntity(entity, HyxcateSoundEvents.ENTITY_STAR_FALLING.getSoundEvent(), (float) HyxcateConfig.FALLING_STARS.volumeAmbient));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundWarhammer(World world) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HyxcateSoundCelestialWarhammer(1.35F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F)));
    }
}
