package de.ellpeck.nyx.event.lunar;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class NyxEventFullMoon extends NyxLunarEvent {
    public NyxEventFullMoon(NyxWorld nyxWorld) {
        super("full_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Nyx.ID + ".full_moon")
                .setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return NyxSoundEvents.EVENT_FULL_MOON_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastDaytime) {
        if (!NyxConfig.fullMoon)
            return false;
        if (!lastDaytime || NyxWorld.isDaytime(this.world))
            return false;
        return this.world.getCurrentMoonPhaseFactor() >= 1;
    }

    @Override
    public boolean shouldStop(boolean lastDaytime) {
        return NyxWorld.isDaytime(this.world);
    }
}
