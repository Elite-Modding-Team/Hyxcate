package de.ellpeck.nyx.event.lunar;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

public abstract class NyxLunarEvent implements INBTSerializable<NBTTagCompound> {

    public final String name;
    protected final NyxWorld nyxWorld;
    protected final World world;

    protected NyxLunarEvent(String name, NyxWorld nyxWorld) {
        this.name = name;
        this.nyxWorld = nyxWorld;
        this.world = nyxWorld.world;
    }

    public abstract ITextComponent getStartMessage();

    public SoundEvent getStartSound() {
        return null;
    }

    public abstract boolean shouldStart(boolean lastDaytime);

    public abstract boolean shouldStop(boolean lastDaytime);

    public int getSkyColor() {
        return 0;
    }

    public String getMoonTexture() {
        return null;
    }

    public void update(boolean lastDaytime) {
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
    }

    public class ConfigImpl implements INBTSerializable<NBTTagCompound> {

        public final Supplier<NyxConfig.LunarEventConfig> config;
        public int daysSinceLast;
        public int startDays;
        public int graceDays;

        public ConfigImpl(Supplier<NyxConfig.LunarEventConfig> config) {
            this.config = config;
        }

        public void update(boolean lastDaytime) {
            if (NyxLunarEvent.this.nyxWorld.currentLunarEvent == NyxLunarEvent.this) {
                this.daysSinceLast = 0;
                this.graceDays = 0;
            }

            if (!lastDaytime && NyxWorld.isDaytime(NyxLunarEvent.this.world)) {
                this.daysSinceLast++;
                if (this.startDays < this.config.get().startNight) this.startDays++;
                if (this.graceDays < this.config.get().graceDays) this.graceDays++;
            }
        }

        public boolean canStart(boolean useChance) {
            if (NyxLunarEvent.this.nyxWorld.forcedLunarEvent == NyxLunarEvent.this) return true;
            if (this.startDays < this.config.get().startNight) return false;
            if (this.graceDays < this.config.get().graceDays) return false;
            if (this.config.get().nightInterval > 0) return this.daysSinceLast >= this.config.get().nightInterval;
            if (useChance) return NyxLunarEvent.this.world.rand.nextDouble() <= this.getChance();
            return true;
        }

        public double getChance() {
            return this.config.get().chance;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("days_since_last", this.daysSinceLast);
            compound.setInteger("start_days", this.startDays);
            compound.setInteger("grace_days", this.graceDays);
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound) {
            this.daysSinceLast = compound.getInteger("days_since_last");
            this.startDays = compound.getInteger("start_days");
            this.graceDays = compound.getInteger("grace_days");
        }
    }
}
