package mod.emt.hyxcate.api.event;

import mod.emt.hyxcate.capability.HyxcateWorld;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class HyxcateLunarEvent implements INBTSerializable<NBTTagCompound> {
    public final String name;
    protected final HyxcateWorld hyxcateWorld;
    protected final World world;

    protected HyxcateLunarEvent(String name, HyxcateWorld hyxcateWorld) {
        this.name = name;
        this.hyxcateWorld = hyxcateWorld;
        this.world = hyxcateWorld.world;
    }

    public abstract ITextComponent getStartMessage();

    public SoundEvent getStartSound() {
        return null;
    }

    public abstract boolean shouldStart(boolean lastDaytime);

    public abstract boolean shouldStop(boolean lastDaytime);

    public boolean shouldStartBasic(boolean lastDaytime) {
        return lastDaytime && !HyxcateWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return 0;
    }

    public int getCloudColor() {
        return 0;
    }

    public int getLightmapColor() {
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
        public int daysSinceLast;
        public int startDays;
        public int graceDays;

        public double chance;
        public int startNight;
        public int gracePeriod;
        public int nightInterval;

        public ConfigImpl(double chance, int startNight, int gracePeriod, int nightInterval) {
            this.chance = chance;
            this.startNight = startNight;
            this.gracePeriod = gracePeriod;
            this.nightInterval = nightInterval;
        }

        public void update(boolean lastDaytime) {
            if (HyxcateLunarEvent.this.hyxcateWorld.currentLunarEvent == HyxcateLunarEvent.this) {
                this.daysSinceLast = 0;
                this.graceDays = 0;
            }

            if (!lastDaytime && HyxcateWorld.isDaytime(HyxcateLunarEvent.this.world)) {
                this.daysSinceLast++;
                if (this.startDays < this.startNight) this.startDays++;
                if (this.graceDays < this.gracePeriod) this.graceDays++;
            }
        }

        public boolean canStart(boolean useChance) {
            if (HyxcateLunarEvent.this.hyxcateWorld.forcedLunarEvent == HyxcateLunarEvent.this) return true;
            if (this.startDays < this.startNight) return false;
            if (this.graceDays < this.gracePeriod) return false;
            if (this.nightInterval > 0) return this.daysSinceLast >= this.nightInterval;
            if (useChance) return HyxcateLunarEvent.this.world.rand.nextDouble() <= this.getChance();
            return true;
        }

        public double getChance() {
            return this.chance;
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
