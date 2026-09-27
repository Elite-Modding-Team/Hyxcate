package mod.emt.hyxcate.event.solar;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class SolarEventGrimEclipse extends HyxcateSolarEvent {
    private final ConfigImpl config = new ConfigImpl(HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.chance, HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.startDay, HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.gracePeriod, HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.dayInterval);

    public SolarEventGrimEclipse(HyxcateWorld HyxcateWorld) {
        super("grim_eclipse", HyxcateWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info." + Hyxcate.ID + ".grim_eclipse").setStyle(new Style().setColor(TextFormatting.DARK_GRAY).setItalic(true));
    }

    @Override
    public SoundEvent getStartSound() {
        return HyxcateSoundEvents.EVENT_GRIM_ECLIPSE_START.getSoundEvent();
    }

    @Override
    public boolean shouldStart(boolean lastNighttime) {
        if (!lastNighttime || HyxcateWorld.isNighttime(this.world)) return false;
        return this.config.canStart();
    }

    @Override
    public boolean shouldStop(boolean lastNighttime) {
        return HyxcateWorld.isNighttime(this.world);
    }

    @Override
    public int getSkyColor() {
        return HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.skyColor;
    }

    @Override
    public int getCloudColor() {
        return HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.cloudColor;
    }

    @Override
    public int getLightmapColor() {
        return HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.lightmapColor;
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
