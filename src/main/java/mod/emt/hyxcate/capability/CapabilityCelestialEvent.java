package mod.emt.hyxcate.capability;

import mod.emt.hyxcate.api.celestialevent.HyxcateCelestialEventRegistry;
import mod.emt.hyxcate.api.celestialevent.HyxcateLunarEvent;
import mod.emt.hyxcate.api.celestialevent.HyxcateSolarEvent;
import mod.emt.hyxcate.compat.astralsorcery.AstralSorcery;
import mod.emt.hyxcate.compat.gamestages.GameStages;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.init.HyxcateRegistry;
import mod.emt.hyxcate.network.HyxcatePacketHandler;
import mod.emt.hyxcate.network.HyxcatePacketWorld;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
                boolean isDirty = false;

                if (this.currentLunarEvent == null) {
                    if (this.forcedLunarEvent != null && this.forcedLunarEvent.shouldStartBasic(this.wasDaytime)) {
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
                        isDirty = true;

                        if (this.world.isRaining() || this.world.isThundering()) {
                            this.world.provider.resetRainAndThunder();
                        }
                    }
                }

                if (this.currentLunarEvent != null && this.currentLunarEvent.shouldStop(this.wasDaytime)) {
                    this.currentLunarEvent = null;
                    isDirty = true;
                }

                if (isDirty) this.sendToClients();

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
                boolean isDirty = false;

                if (this.currentSolarEvent == null) {
                    if (this.forcedSolarEvent != null && this.forcedSolarEvent.shouldStartBasic(this.wasNighttime)) {
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
                        isDirty = true;

                        if (this.world.isRaining() || this.world.isThundering()) {
                            this.world.provider.resetRainAndThunder();
                        }
                    }
                }

                if (this.currentSolarEvent != null && this.currentSolarEvent.shouldStop(this.wasNighttime)) {
                    this.currentSolarEvent = null;
                    isDirty = true;
                }

                if (isDirty) this.sendToClients();

                this.wasNighttime = WorldUtil.isNighttime(this.world);
            }
        }
    }

    public void sendToClients() {
        for (EntityPlayer player : this.world.playerEntities)
            HyxcatePacketHandler.sendTo(player, new HyxcatePacketWorld(this));
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
        compound.setBoolean("was_daytime", this.wasDaytime);
        for (HyxcateLunarEvent event : this.lunarEvents)
            compound.setTag(event.name, event.serializeNBT());

        // Solar events
        if (this.currentSolarEvent != null) compound.setString("eventSolar", this.currentSolarEvent.name);
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
        this.wasDaytime = compound.getBoolean("was_daytime");
        for (HyxcateLunarEvent event : this.lunarEvents)
            event.deserializeNBT(compound.getCompoundTag(event.name));

        // Solar events
        String nameSolar = compound.getString("eventSolar");
        this.currentSolarEvent = this.solarEvents.stream().filter(e -> e.name.equals(nameSolar)).findFirst().orElse(null);
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

    @SideOnly(Side.CLIENT)
    public void onClientSync(HyxcateLunarEvent oldLunar, HyxcateSolarEvent oldSolar) {
        if (oldLunar == null && this.currentLunarEvent != null) {
            if (HyxcateConfig.GENERAL.eventNotifications) {
                Minecraft.getMinecraft().player.sendMessage(this.currentLunarEvent.getStartMessage());
            }
            if (this.currentLunarEvent.getStartSound() != null && HyxcateConfig.GENERAL.eventIntroSounds) {
                EntityPlayer player = Minecraft.getMinecraft().player;
                this.world.playSound(player, player.posX, player.posY, player.posZ, this.currentLunarEvent.getStartSound(), SoundCategory.AMBIENT, 10.0F, 1.0F);
            }
        }
        if (oldSolar == null && this.currentSolarEvent != null) {
            if (HyxcateConfig.GENERAL.eventNotifications) {
                Minecraft.getMinecraft().player.sendMessage(this.currentSolarEvent.getStartMessage());
            }
            if (this.currentSolarEvent.getStartSound() != null && HyxcateConfig.GENERAL.eventIntroSounds) {
                EntityPlayer player = Minecraft.getMinecraft().player;
                this.world.playSound(player, player.posX, player.posY, player.posZ, this.currentSolarEvent.getStartSound(), SoundCategory.AMBIENT, 10.0F, 1.0F);
            }
        }
    }
}
