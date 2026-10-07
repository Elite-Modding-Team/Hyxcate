package mod.emt.hyxcate.capability;

import mod.emt.hyxcate.api.celestialevent.HyxcateCelestialEventRegistry;
import mod.emt.hyxcate.api.celestialevent.HyxcateLunarEvent;
import mod.emt.hyxcate.api.celestialevent.HyxcateSolarEvent;
import mod.emt.hyxcate.compat.astralsorcery.AstralSorcery;
import mod.emt.hyxcate.compat.gamestages.GameStages;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.init.HyxcateRegistry;
import mod.emt.hyxcate.network.HyxcatePacketEventStart;
import mod.emt.hyxcate.network.HyxcatePacketHandler;
import mod.emt.hyxcate.network.HyxcatePacketWorld;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("unchecked")
public class CapabilityCelestialEvent implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    public static float moonPhase;

    public final World world;
    public final List<HyxcateLunarEvent> lunarEvents = new ArrayList<>();
    public final List<HyxcateSolarEvent> solarEvents = new ArrayList<>();
    public final Set<BlockPos> cachedMeteorPositions = new HashSet<>();
    public final Map<ChunkPos, MutableInt> playersPresentTicks = new HashMap<>();
    public final Set<BlockPos> meteorLandingSites = new HashSet<>();
    public final Set<String> visitedDimensions = new HashSet<>();
    public HyxcateLunarEvent currentLunarEvent;
    public HyxcateLunarEvent forcedLunarEvent;
    public HyxcateSolarEvent currentSolarEvent;
    public HyxcateSolarEvent forcedSolarEvent;
    private boolean wasDaytime;
    private boolean wasNighttime;
    public long lunarTransitionStartTime = -1;
    public long solarTransitionStartTime = -1;
    public boolean lunarTransitionStopping = false;
    public boolean solarTransitionStopping = false;
    public HyxcateLunarEvent lastLunarEvent;
    public HyxcateSolarEvent lastSolarEvent;

    public CapabilityCelestialEvent(World world) {
        this.world = world;
        this.lunarEvents.addAll(HyxcateCelestialEventRegistry.createLunarEvents(this));
        this.solarEvents.addAll(HyxcateCelestialEventRegistry.createSolarEvents(this));
    }

    public static CapabilityCelestialEvent get(World world) {
        if (world.hasCapability(HyxcateRegistry.worldCapability, null)) {
            return world.getCapability(HyxcateRegistry.worldCapability, null);
        }
        return null;
    }

    public void update() {
        if (HyxcateConfig.MASTER_SWITCHES.meteorEventsEnabled) updateMeteors();
        if (HyxcateConfig.MASTER_SWITCHES.lunarEventsEnabled && GameStages.checkGameStageLunarEvents(this.world))
            updateLunarEvents();
        if (HyxcateConfig.MASTER_SWITCHES.solarEventsEnabled && GameStages.checkGameStageSolarEvents(this.world))
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
            if (this.world.getTotalWorldTime() % interval == 0) {
                Set<ChunkPos> remaining = new HashSet<>(this.playersPresentTicks.keySet());
                for (EntityPlayer player : this.world.playerEntities) {
                    for (int x = -HyxcateConfig.METEORS.disallowRadius; x <= HyxcateConfig.METEORS.disallowRadius; x++) {
                        for (int z = -HyxcateConfig.METEORS.disallowRadius; z <= HyxcateConfig.METEORS.disallowRadius; z++) {
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
        int dimension = this.world.provider.getDimensionType().getId();
        if (HyxcateData.ALLOWED_DIMENSIONS_LUNAR.contains(dimension)) {
            moonPhase = this.world.getCurrentMoonPhaseFactor();

            for (HyxcateLunarEvent event : this.lunarEvents)
                event.update(this.wasDaytime);

            if (!this.world.isRemote) {
                boolean eventStart = false;
                boolean eventStop = false;

                if (this.currentLunarEvent == null) {
                    if (this.forcedLunarEvent != null && WorldUtil.shouldStartLunar(this.world, this.wasDaytime)) {
                        this.currentLunarEvent = this.forcedLunarEvent;
                        this.forcedLunarEvent = null;
                    } else if (!Loader.isModLoaded("astralsorcery") || !AstralSorcery.isDayOfLunarEclipse(this.world)) {
                        for (HyxcateLunarEvent event : this.lunarEvents) {
                            if (event.shouldStart(this.wasDaytime)) {
                                this.currentLunarEvent = event;
                                break;
                            }
                        }
                    }

                    if (this.currentLunarEvent != null) {
                        this.lastLunarEvent = this.currentLunarEvent;
                        this.lunarTransitionStartTime = this.world.getTotalWorldTime();
                        this.lunarTransitionStopping = false;
                        eventStart = true;

                        if (this.world.isRaining() || this.world.isThundering()) {
                            this.world.provider.resetRainAndThunder();
                        }
                    }
                }

                if (this.currentLunarEvent != null && this.currentLunarEvent.shouldStop(this.wasDaytime)) {
                    this.currentLunarEvent = null;
                    this.lunarTransitionStartTime = this.world.getTotalWorldTime();
                    this.lunarTransitionStopping = true;
                    eventStop = true;
                }

                if (eventStart) {
                    this.sendWorldToClients();
                    this.sendEventStartToClients(true);
                }
                if (eventStop) {
                    this.sendWorldToClients();
                }

                this.wasDaytime = WorldUtil.isDaytime(this.world);
            }
        }
    }

    public void updateSolarEvents() {
        int dimension = this.world.provider.getDimensionType().getId();
        if (HyxcateData.ALLOWED_DIMENSIONS_SOLAR.contains(dimension)) {

            for (HyxcateSolarEvent event : this.solarEvents)
                event.update(this.wasNighttime);

            if (!this.world.isRemote) {
                boolean eventStart = false;
                boolean eventStop = false;

                if (this.currentSolarEvent == null) {
                    if (this.forcedSolarEvent != null && WorldUtil.shouldStartSolar(this.world, this.wasNighttime)) {
                        this.currentSolarEvent = this.forcedSolarEvent;
                        this.forcedSolarEvent = null;
                    } else if (!Loader.isModLoaded("astralsorcery") || !AstralSorcery.isDayOfSolarEclipse(this.world)) {
                        for (HyxcateSolarEvent event : this.solarEvents) {
                            if (event.shouldStart(this.wasNighttime)) {
                                this.currentSolarEvent = event;
                                break;
                            }
                        }
                    }

                    if (this.currentSolarEvent != null) {
                        this.lastSolarEvent = this.currentSolarEvent;
                        this.solarTransitionStartTime = this.world.getTotalWorldTime();
                        this.solarTransitionStopping = false;
                        eventStart = true;

                        if (this.world.isRaining() || this.world.isThundering()) {
                            this.world.provider.resetRainAndThunder();
                        }
                    }
                }

                if (this.currentSolarEvent != null && this.currentSolarEvent.shouldStop(this.wasNighttime)) {
                    this.currentSolarEvent = null;
                    this.solarTransitionStartTime = this.world.getTotalWorldTime();
                    this.solarTransitionStopping = true;
                    eventStop = true;
                }

                if (eventStart) {
                    this.sendWorldToClients();
                    this.sendEventStartToClients(false);
                }
                if (eventStop) {
                    this.sendWorldToClients();
                }

                this.wasNighttime = WorldUtil.isNighttime(this.world);
            }
        }
    }

    public void sendWorldToClients() {
        for (EntityPlayer player : this.world.playerEntities)
            HyxcatePacketHandler.sendTo(player, new HyxcatePacketWorld(this));
    }

    public void sendEventStartToClients(boolean lunar) {
        for (EntityPlayer player : this.world.playerEntities)
            HyxcatePacketHandler.sendTo(player, new HyxcatePacketEventStart(lunar));
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
        if (this.currentLunarEvent != null) compound.setString("eventLunar", this.currentLunarEvent.name);
        if (this.lastLunarEvent != null) compound.setString("last_event_lunar", this.lastLunarEvent.name);
        compound.setLong("lunar_transition_start", this.lunarTransitionStartTime);
        compound.setBoolean("lunar_transition_stopping", this.lunarTransitionStopping);
        compound.setBoolean("was_daytime", this.wasDaytime);
        for (HyxcateLunarEvent event : this.lunarEvents)
            compound.setTag(event.name, event.serializeNBT());

        // Solar events
        if (this.currentSolarEvent != null) compound.setString("eventSolar", this.currentSolarEvent.name);
        if (this.lastSolarEvent != null) compound.setString("last_event_solar", this.lastSolarEvent.name);
        compound.setLong("solar_transition_start", this.solarTransitionStartTime);
        compound.setBoolean("solar_transition_stopping", this.solarTransitionStopping);
        compound.setBoolean("was_nighttime", this.wasNighttime);
        for (HyxcateSolarEvent event : this.solarEvents)
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
        String nameLunar = compound.getString("eventLunar");
        this.currentLunarEvent = this.lunarEvents.stream().filter(e -> e.name.equals(nameLunar)).findFirst().orElse(null);
        String lastNameLunar = compound.getString("last_event_lunar");
        this.lastLunarEvent = this.lunarEvents.stream().filter(e -> e.name.equals(lastNameLunar)).findFirst().orElse(null);
        this.lunarTransitionStartTime = compound.getLong("lunar_transition_start");
        this.lunarTransitionStopping = compound.getBoolean("lunar_transition_stopping");
        this.wasDaytime = compound.getBoolean("was_daytime");
        for (HyxcateLunarEvent event : this.lunarEvents)
            event.deserializeNBT(compound.getCompoundTag(event.name));

        // Solar events
        String nameSolar = compound.getString("eventSolar");
        this.currentSolarEvent = this.solarEvents.stream().filter(e -> e.name.equals(nameSolar)).findFirst().orElse(null);
        String lastNameSolar = compound.getString("last_event_solar");
        this.lastSolarEvent = this.solarEvents.stream().filter(e -> e.name.equals(lastNameSolar)).findFirst().orElse(null);
        this.solarTransitionStartTime = compound.getLong("solar_transition_start");
        this.solarTransitionStopping = compound.getBoolean("solar_transition_stopping");
        this.wasNighttime = compound.getBoolean("was_nighttime");
        for (HyxcateSolarEvent event : this.solarEvents)
            event.deserializeNBT(compound.getCompoundTag(event.name));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == HyxcateRegistry.worldCapability;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == HyxcateRegistry.worldCapability ? (T) this : null;
    }
}
