package mod.emt.hyxcate.celestialevent.solar;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.api.celestialevent.HyxcateSolarEvent;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class SolarEventRedGiant extends HyxcateSolarEvent {
    private final ConfigImpl config = new ConfigImpl(HyxcateConfig.EVENTS_SOLAR.RED_GIANT.chance, HyxcateConfig.EVENTS_SOLAR.RED_GIANT.startDay, HyxcateConfig.EVENTS_SOLAR.RED_GIANT.gracePeriod, HyxcateConfig.EVENTS_SOLAR.RED_GIANT.dayInterval);

    public SolarEventRedGiant(HyxcateWorld HyxcateWorld) {
        super("red_giant", HyxcateWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Hyxcate.ID + ".red_giant").setStyle(new Style().setColor(TextFormatting.RED).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return this.world.rand.nextInt(100) < 1 ? HyxcateSoundEvents.EVENT_RED_SUN_START_SPECIAL.getSoundEvent() : HyxcateSoundEvents.EVENT_RED_SUN_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastNighttime) {
        super.shouldStart(lastNighttime);
        return this.config.canStart();
    }

    @Override
    public int getSkyColor() {
        return HyxcateConfig.EVENTS_SOLAR.RED_GIANT.skyColor;
    }

    @Override
    public int getCloudColor() {
        return HyxcateConfig.EVENTS_SOLAR.RED_GIANT.cloudColor;
    }

    @Override
    public int getLightmapColor() {
        return HyxcateConfig.EVENTS_SOLAR.RED_GIANT.lightmapColor;
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
