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

public class NyxEventRedGiant extends NyxSolarEvent {
    private final ConfigImpl config = new ConfigImpl(() -> NyxConfig.redSun);

    public NyxEventRedGiant(NyxWorld nyxWorld) {
        super("red_giant", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Nyx.ID + ".red_giant")
                .setStyle(new Style().setColor(TextFormatting.RED).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return this.world.rand.nextInt(100) < 1 ? NyxSoundEvents.EVENT_RED_SUN_START_SPECIAL.getSoundEvent() : NyxSoundEvents.EVENT_RED_SUN_START.getSoundEvent();
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
        return 0x420d03;
    }

    @Override
    public String getSunTexture() {
        return "red_giant";
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
