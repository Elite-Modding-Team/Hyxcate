package de.ellpeck.nyx.event.solar;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class NyxEventGrimEclipse extends NyxSolarEvent {
    private final ConfigImpl config = new ConfigImpl(() -> NyxConfig.solarEclipse);

    public NyxEventGrimEclipse(NyxWorld nyxWorld) {
        super("grim_eclipse", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Nyx.ID + ".grim_eclipse")
                .setStyle(new Style().setColor(TextFormatting.DARK_GRAY).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return NyxSoundEvents.EVENT_GRIM_ECLIPSE_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastNighttime) {
        if (!lastNighttime || NyxWorld.isNighttime(this.world))
            return false;
        return this.config.canStart();
    }

    @Override
    public boolean shouldStop(boolean lastNighttime) {
        return NyxWorld.isNighttime(this.world);
    }

    @Override
    public int getSkyColor() {
        return 0x131311;
    }

    @Override
    public String getSunTexture() {
        return "grim_eclipse";
    }

    @Override
    public void update(boolean lastNighttime) {
        this.config.update(lastNighttime);
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
