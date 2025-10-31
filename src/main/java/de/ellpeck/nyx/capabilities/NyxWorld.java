package de.ellpeck.nyx.capabilities;

import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.events.lunar.*;
import de.ellpeck.nyx.events.solar.NyxEventRedSun;
import de.ellpeck.nyx.events.solar.NyxEventSolarEclipse;
import de.ellpeck.nyx.events.solar.NyxSolarEvent;
import de.ellpeck.nyx.init.NyxRegistry;
import de.ellpeck.nyx.network.NyxPacketHandler;
import de.ellpeck.nyx.network.NyxPacketWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class NyxWorld implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    public static float moonPhase;

    public final World world;
    public final List<NyxLunarEvent> lunarEvents = new ArrayList<>();
    public final List<NyxSolarEvent> solarEvents = new ArrayList<>();
    public final Set<BlockPos> cachedMeteorPositions = new HashSet<>();
    public final Map<ChunkPos, MutableInt> playersPresentTicks = new HashMap<>();
    public final Set<BlockPos> meteorLandingSites = new HashSet<>();
    public final Set<String> visitedDimensions = new HashSet<>();
    public float eventSkyModifier;
    public int currentSkyColor;
    public NyxLunarEvent currentLunarEvent;
    public NyxLunarEvent forcedLunarEvent;
    public NyxSolarEvent currentSolarEvent;
    public NyxSolarEvent forcedSolarEvent;

    private boolean wasDaytime;
    private boolean wasNighttime;

    public NyxWorld(World world) {
        this.world = world;
        this.solarEvents.add(new NyxEventRedSun(this));
        this.solarEvents.add(new NyxEventSolarEclipse(this));

        this.lunarEvents.add(new NyxEventHarvestMoon(this));
        this.lunarEvents.add(new NyxEventStarShower(this));
        this.lunarEvents.add(new NyxEventBloodMoon(this));
        // this needs to stay at the end to prioritize random events
        this.lunarEvents.add(new NyxEventFullMoon(this));
    }

    public static boolean isDaytime(World world) {
        return !isNighttime(world);
    }

    public static boolean isNighttime(World world) {
        // https://minecraft.fandom.com/wiki/Daylight_cycle
        // 12542: Beds can be used
        // 23216: Solar zenith angle is 0
        long time = world.getWorldTime() % 24000;
        return time >= 12542 && time < 23216;
    }

    public static NyxWorld get(World world) {
        if (world.hasCapability(NyxRegistry.worldCapability, null))
            return world.getCapability(NyxRegistry.worldCapability, null);
        return null;
    }

    public void update() {
        updateMeteors();
        updateLunarEvents();
        updateSolarEvents();
    }

    public void updateMeteors() {
        if (!this.world.isRemote) {
            // add to visited dimensions list
            if (this.world.getTotalWorldTime() % 200 == 0) {
                for (EntityPlayer player : this.world.getMinecraftServer().getPlayerList().getPlayers())
                    this.visitedDimensions.add(player.world.provider.getDimensionType().getName());
            }

            // calculate which chunks have players close to them for meteor spawning
            int interval = 100;
            if (NyxConfig.meteors && this.world.getTotalWorldTime() % interval == 0) {
                Set<ChunkPos> remaining = new HashSet<>(this.playersPresentTicks.keySet());
                for (EntityPlayer player : this.world.playerEntities) {
                    for (int x = -NyxConfig.meteorDisallowRadius; x <= NyxConfig.meteorDisallowRadius; x++) {
                        for (int z = -NyxConfig.meteorDisallowRadius; z <= NyxConfig.meteorDisallowRadius; z++) {
                            ChunkPos pos = new ChunkPos(MathHelper.floor(player.posX / 16) + x, MathHelper.floor(player.posZ / 16) + z);
                            MutableInt time = this.playersPresentTicks.computeIfAbsent(pos, p -> new MutableInt());
                            time.add(interval);
                            remaining.remove(pos);
                        }
                    }
                }
                // all positions that weren't removed are player-free, so reduce them
                if (!remaining.isEmpty()) {
                    for (ChunkPos pos : remaining) {
                        MutableInt time = this.playersPresentTicks.get(pos);
                        time.subtract(interval);
                        if (time.intValue() <= 0) this.playersPresentTicks.remove(pos);
                    }
                }
            }
        }
    }

    public void updateLunarEvents() {
        String dimension = this.world.provider.getDimensionType().getName();
        if (NyxConfig.allowedDimensions.contains(dimension)) {
            moonPhase = this.world.getCurrentMoonPhaseFactor();

            for (NyxLunarEvent event : this.lunarEvents)
                event.update(this.wasDaytime);

            if (!this.world.isRemote) {
                boolean isDirty = false;

                if (this.currentLunarEvent == null) {
                    if (this.forcedLunarEvent != null && this.forcedLunarEvent.shouldStart(this.wasDaytime)) {
                        this.currentLunarEvent = this.forcedLunarEvent;
                        this.forcedLunarEvent = null;
                    } else {
                        for (NyxLunarEvent event : this.lunarEvents) {
                            if (event.shouldStart(this.wasDaytime)) {
                                this.currentLunarEvent = event;
                                break;
                            }
                        }
                    }
                    if (this.currentLunarEvent != null) {
                        isDirty = true;
                        if (NyxConfig.eventNotifications) {
                            ITextComponent text = this.currentLunarEvent.getStartMessage();
                            for (EntityPlayer player : this.world.playerEntities) {
                                player.sendMessage(text);
                                if (this.currentLunarEvent.getStartSound() != null)
                                    this.world.playSound(null, player.posX, player.posY, player.posZ, this.currentLunarEvent.getStartSound(), SoundCategory.AMBIENT, 1.0F, 1.0F);
                            }
                        }
                    }
                }

                if (this.currentLunarEvent != null && this.currentLunarEvent.shouldStop(this.wasDaytime)) {
                    this.currentLunarEvent = null;
                    isDirty = true;
                }

                if (isDirty) this.sendToClients();

                this.wasDaytime = isDaytime(this.world);
            } else {
                if (this.currentLunarEvent != null && this.currentSkyColor != 0) {
                    if (this.eventSkyModifier < 1) this.eventSkyModifier += 0.01F;
                } else {
                    if (this.currentSolarEvent == null && this.eventSkyModifier > 0) this.eventSkyModifier -= 0.01F;
                }
            }
        }
    }

    public void updateSolarEvents() {
        String dimension = this.world.provider.getDimensionType().getName();
        if (NyxConfig.allowedDimensionsSolar.contains(dimension)) {

            for (NyxSolarEvent event : this.solarEvents)
                event.update(this.wasNighttime);

            if (!this.world.isRemote) {
                boolean isDirty = false;

                if (this.currentSolarEvent == null) {
                    if (this.forcedSolarEvent != null && this.forcedSolarEvent.shouldStart(this.wasNighttime)) {
                        this.currentSolarEvent = this.forcedSolarEvent;
                        this.forcedSolarEvent = null;
                    } else {
                        for (NyxSolarEvent event : this.solarEvents) {
                            if (event.shouldStart(this.wasNighttime)) {
                                this.currentSolarEvent = event;
                                break;
                            }
                        }
                    }
                    if (this.currentSolarEvent != null) {
                        isDirty = true;
                        if (NyxConfig.eventNotifications) {
                            ITextComponent text = this.currentSolarEvent.getStartMessage();
                            for (EntityPlayer player : this.world.playerEntities) {
                                player.sendMessage(text);
                                if (this.currentSolarEvent.getStartSound() != null)
                                    this.world.playSound(null, player.posX, player.posY, player.posZ, this.currentSolarEvent.getStartSound(), SoundCategory.AMBIENT, 1.0F, 1.0F);
                            }
                        }
                    }
                }

                if (this.currentSolarEvent != null && this.currentSolarEvent.shouldStop(this.wasNighttime)) {
                    this.currentSolarEvent = null;
                    isDirty = true;
                }

                if (isDirty) this.sendToClients();

                this.wasNighttime = isNighttime(this.world);
            } else {
                if (this.currentSolarEvent != null && this.currentSkyColor != 0) {
                    if (this.eventSkyModifier < 1) this.eventSkyModifier += 0.01F;
                } else {
                    if (this.currentLunarEvent == null && this.eventSkyModifier > 0) this.eventSkyModifier -= 0.01F;
                }
            }
        }
    }

    public void sendToClients() {
        for (EntityPlayer player : this.world.playerEntities)
            NyxPacketHandler.sendTo(player, new NyxPacketWorld(this));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.serializeNBT(false);
    }

    public NBTTagCompound serializeNBT(boolean client) {
        NBTTagCompound compound = new NBTTagCompound();

        // Meteors
        NBTTagList landings = new NBTTagList();
        for (BlockPos pos : this.meteorLandingSites)
            landings.appendTag(new NBTTagLong(pos.toLong()));
        compound.setTag("meteor_landings", landings);
        NBTTagList meteors = new NBTTagList();
        for (BlockPos pos : this.cachedMeteorPositions)
            meteors.appendTag(new NBTTagLong(pos.toLong()));
        compound.setTag("cached_meteors", meteors);
        if (!client) {
            NBTTagList ticks = new NBTTagList();
            for (Map.Entry<ChunkPos, MutableInt> e : this.playersPresentTicks.entrySet()) {
                NBTTagCompound comp = new NBTTagCompound();
                comp.setInteger("x", e.getKey().x);
                comp.setInteger("z", e.getKey().z);
                comp.setInteger("ticks", e.getValue().intValue());
                ticks.appendTag(comp);
            }
            compound.setTag("players_present_ticks", ticks);
            NBTTagList dimensions = new NBTTagList();
            for (String dim : this.visitedDimensions)
                dimensions.appendTag(new NBTTagString(dim));
            compound.setTag("visited_dims", dimensions);
        }

        // Lunar events
        if (this.currentLunarEvent != null) compound.setString("event", this.currentLunarEvent.name);
        compound.setBoolean("was_daytime", this.wasDaytime);
        for (NyxLunarEvent event : this.lunarEvents)
            compound.setTag(event.name, event.serializeNBT());

        // Solar events
        if (this.currentSolarEvent != null) compound.setString("eventSolar", this.currentSolarEvent.name);
        compound.setBoolean("was_nighttime", this.wasNighttime);
        for (NyxSolarEvent event : this.solarEvents)
            compound.setTag(event.name, event.serializeNBT());

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        this.deserializeNBT(compound, false);
    }

    public void deserializeNBT(NBTTagCompound compound, boolean client) {
        // Meteors
        this.meteorLandingSites.clear();
        NBTTagList landings = compound.getTagList("meteor_landings", Constants.NBT.TAG_LONG);
        for (int i = 0; i < landings.tagCount(); i++)
            this.meteorLandingSites.add(BlockPos.fromLong(((NBTTagLong) landings.get(i)).getLong()));
        this.cachedMeteorPositions.clear();
        NBTTagList meteors = compound.getTagList("cached_meteors", Constants.NBT.TAG_LONG);
        for (int i = 0; i < meteors.tagCount(); i++)
            this.cachedMeteorPositions.add(BlockPos.fromLong(((NBTTagLong) meteors.get(i)).getLong()));
        if (!client) {
            this.playersPresentTicks.clear();
            NBTTagList ticks = compound.getTagList("players_present_ticks", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < ticks.tagCount(); i++) {
                NBTTagCompound comp = ticks.getCompoundTagAt(i);
                this.playersPresentTicks.put(new ChunkPos(comp.getInteger("x"), comp.getInteger("z")), new MutableInt(comp.getInteger("ticks")));
            }
            this.visitedDimensions.clear();
            NBTTagList dimensions = compound.getTagList("visited_dims", Constants.NBT.TAG_STRING);
            for (int i = 0; i < dimensions.tagCount(); i++)
                this.visitedDimensions.add(((NBTTagString) dimensions.get(i)).getString());
        }

        // Lunar events
        String nameLunar = compound.getString("event");
        this.currentLunarEvent = this.lunarEvents.stream().filter(e -> e.name.equals(nameLunar)).findFirst().orElse(null);
        if (this.currentLunarEvent != null) this.currentSkyColor = this.currentLunarEvent.getSkyColor();
        this.wasDaytime = compound.getBoolean("was_daytime");
        for (NyxLunarEvent event : this.lunarEvents)
            event.deserializeNBT(compound.getCompoundTag(event.name));

        // Solar events
        String nameSolar = compound.getString("eventSolar");
        this.currentSolarEvent = this.solarEvents.stream().filter(e -> e.name.equals(nameSolar)).findFirst().orElse(null);
        if (this.currentSolarEvent != null) this.currentSkyColor = this.currentSolarEvent.getSkyColor();
        this.wasNighttime = compound.getBoolean("was_nighttime");
        for (NyxSolarEvent event : this.solarEvents)
            event.deserializeNBT(compound.getCompoundTag(event.name));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == NyxRegistry.worldCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == NyxRegistry.worldCapability ? (T) this : null;
    }
}
