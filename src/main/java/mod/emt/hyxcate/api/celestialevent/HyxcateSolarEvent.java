package mod.emt.hyxcate.api.celestialevent;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class HyxcateSolarEvent implements INBTSerializable<NBTTagCompound> {
    public final String name;
    protected final CapabilityCelestialEvent hyxcateWorld;
    protected final World world;

    protected HyxcateSolarEvent(String name, CapabilityCelestialEvent hyxcateWorld) {
        this.name = name;
        this.hyxcateWorld = hyxcateWorld;
        this.world = hyxcateWorld.world;
    }

    public abstract ITextComponent getStartMessage();

    public SoundEvent getStartSound() {
        return null;
    }

    public boolean shouldStart(boolean lastNighttime) {
        return (!lastNighttime || !WorldUtil.isNighttime(this.world));
    }

    public boolean shouldStop(boolean lastNighttime) {
        return WorldUtil.isNighttime(this.world);
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
        public int daysSinceLast;
        public int startDays;
        public int graceDays;

        public double chance;
        public int startDay;
        public int gracePeriod;
        public int dayInterval;

        public ConfigImpl(double chance, int startDay, int gracePeriod, int dayInterval) {
            this.chance = chance;
            this.startDay = startDay;
            this.gracePeriod = gracePeriod;
            this.dayInterval = dayInterval;
        }

        public void update(boolean lastNighttime) {
            if (HyxcateSolarEvent.this.hyxcateWorld.currentSolarEvent == HyxcateSolarEvent.this) {
                this.daysSinceLast = 0;
                this.graceDays = 0;
            }

            if (!lastNighttime && WorldUtil.isNighttime(HyxcateSolarEvent.this.world)) {
                this.daysSinceLast++;
                if (this.startDays < this.startDay) this.startDays++;
                if (this.graceDays < this.gracePeriod) this.graceDays++;
            }
        }

        public boolean canStart() {
            if (this.startDays < this.startDay) return false;
            if (this.graceDays < this.gracePeriod) return false;
            if (this.dayInterval > 0) {
                return this.daysSinceLast >= this.dayInterval;
            } else {
                return HyxcateSolarEvent.this.world.rand.nextDouble() <= this.chance;
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
