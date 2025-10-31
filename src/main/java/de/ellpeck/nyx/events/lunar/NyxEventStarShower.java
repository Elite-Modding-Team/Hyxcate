package de.ellpeck.nyx.events.lunar;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class NyxEventStarShower extends NyxLunarEvent {

    private final ConfigImpl config = new ConfigImpl(() -> NyxConfig.starShowers);

    public NyxEventStarShower(NyxWorld nyxWorld) {
        super("star_shower", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Nyx.ID + ".star_shower")
                .setStyle(new Style().setColor(TextFormatting.GOLD).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return NyxSoundEvents.starShowerStart.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastDaytime) {
        if (!lastDaytime || NyxWorld.isDaytime(this.world))
            return false;
        return this.config.canStart(true);
    }

    @Override
    public boolean shouldStop(boolean lastDaytime) {
        return NyxWorld.isDaytime(this.world);
    }

    @Override
    public boolean shouldPreventSleeping() {
        return false;
    }

    @Override
    public int getSkyColor() {
        return 0xdec25f;
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
