package mod.emt.hyxcate.event.lunar;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.api.event.HyxcateLunarEvent;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class LunarEventStarShower extends HyxcateLunarEvent {

    private final ConfigImpl config = new ConfigImpl(HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.chance, HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.startNight, HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.gracePeriod, HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.nightInterval);

    public LunarEventStarShower(HyxcateWorld HyxcateWorld) {
        super("star_shower", HyxcateWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Hyxcate.ID + ".star_shower").setStyle(new Style().setColor(TextFormatting.GOLD).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return HyxcateSoundEvents.EVENT_STAR_SHOWER_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastDaytime) {
        if (HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.onFullMoon && this.world.getCurrentMoonPhaseFactor() < 1) return false;
        if (!lastDaytime || HyxcateWorld.isDaytime(this.world)) return false;
        return this.config.canStart(true);
    }

    @Override
    public boolean shouldStop(boolean lastDaytime) {
        return HyxcateWorld.isDaytime(this.world);
    }

    @Override
    public int getSkyColor() {
        return HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.skyColor;
    }

    @Override
    public int getCloudColor() {
        return HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.cloudColor;
    }

    @Override
    public int getLightmapColor() {
        return HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.lightmapColor;
    }

    @Override
    public String getMoonTexture() {
        return "starry_moon";
    }

    @Override
    public void update(boolean lastDaytime) {
        this.config.update(lastDaytime);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.config.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.config.deserializeNBT(nbt);
    }
}
