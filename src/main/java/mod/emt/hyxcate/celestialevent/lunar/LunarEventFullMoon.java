package mod.emt.hyxcate.celestialevent.lunar;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.api.celestialevent.HyxcateLunarEvent;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class LunarEventFullMoon extends HyxcateLunarEvent {
    public LunarEventFullMoon(CapabilityCelestialEvent HyxcateWorld) {
        super("full_moon", HyxcateWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Hyxcate.ID + ".full_moon").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return HyxcateSoundEvents.EVENT_FULL_MOON_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastDaytime) {
        if (!HyxcateConfig.EVENTS_LUNAR.FULL_MOON.actAsEvent) return false;
        if (!lastDaytime || WorldUtil.isDaytime(this.world)) return false;
        return this.world.getCurrentMoonPhaseFactor() >= 1;
    }

    @Override
    public boolean shouldStop(boolean lastDaytime) {
        return WorldUtil.isDaytime(this.world);
    }
}
