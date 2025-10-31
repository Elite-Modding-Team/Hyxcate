package de.ellpeck.nyx.events.solar;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Supplier;

public abstract class NyxSolarEvent implements INBTSerializable<NBTTagCompound> {

    public final String name;
    protected final NyxWorld nyxWorld;
    protected final World world;

    protected NyxSolarEvent(String name, NyxWorld nyxWorld) {
        this.name = name;
        this.nyxWorld = nyxWorld;
        this.world = nyxWorld.world;
    }

    public abstract ITextComponent getStartMessage();

    public SoundEvent getStartSound() {
        return null;
    }

    public abstract boolean shouldStart(boolean lastNighttime);

    public abstract boolean shouldStop(boolean lastNighttime);

    public int getSkyColor() {
        return 0;
    }

    public String getSunTexture() {
        return null;
    }

    public void update(boolean lastNighttime) {
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    public class ConfigImpl implements INBTSerializable<NBTTagCompound> {

        public final Supplier<NyxConfig.SolarEventConfig> config;
        public int daysSinceLast;
        public int startDays;
        public int graceDays;

        public ConfigImpl(Supplier<NyxConfig.SolarEventConfig> config) {
            this.config = config;
        }

        public void update(boolean lastNighttime) {
            if (NyxSolarEvent.this.nyxWorld.currentSolarEvent == NyxSolarEvent.this) {
                this.daysSinceLast = 0;
                this.graceDays = 0;
            }

            if (!lastNighttime && NyxWorld.isNighttime(NyxSolarEvent.this.world)) {
                this.daysSinceLast++;
                if (this.startDays < this.config.get().startDay) this.startDays++;
                if (this.graceDays < this.config.get().graceDays) this.graceDays++;
            }
        }

        public boolean canStart() {
            if (NyxSolarEvent.this.nyxWorld.forcedSolarEvent == NyxSolarEvent.this) return true;
            if (this.startDays < this.config.get().startDay) return false;
            if (this.graceDays < this.config.get().graceDays) return false;
            if (this.config.get().dayInterval > 0) {
                return this.daysSinceLast >= this.config.get().dayInterval;
            } else {
                return NyxSolarEvent.this.world.rand.nextDouble() <= this.config.get().chance;
            }
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
