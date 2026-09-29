package mod.emt.hyxcate.event.world;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.compat.gamestages.GameStages;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.entity.EntityFallingMeteor;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.stream.Collectors;

public class FallingMeteorEvent {
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        CapabilityCelestialEvent data = CapabilityCelestialEvent.get(event.world);
        if (data == null) return;
        data.update();

        // Meteors
        meteors:
        if (!event.world.isRemote && event.world.getTotalWorldTime() >= HyxcateConfig.METEORS.gracePeriod * 24000L && event.world.getTotalWorldTime() % 1200 == 0) {
            if (event.world.playerEntities.isEmpty()) break meteors;
            EntityPlayer selectedPlayer = event.world.playerEntities.get(event.world.rand.nextInt(event.world.playerEntities.size()));
            if (selectedPlayer == null || !GameStages.checkGameStageMeteorEvents(selectedPlayer)) break meteors;
            double spawnX = selectedPlayer.posX + MathHelper.nextDouble(event.world.rand, -HyxcateConfig.METEORS.spawnRadius, HyxcateConfig.METEORS.spawnRadius);
            double spawnZ = selectedPlayer.posZ + MathHelper.nextDouble(event.world.rand, -HyxcateConfig.METEORS.spawnRadius, HyxcateConfig.METEORS.spawnRadius);
            BlockPos spawnPos = new BlockPos(spawnX, 0, spawnZ);
            double chance = ConfigHelper.getMeteorChance(event.world, data);
            MutableInt ticksInArea = data.playersPresentTicks.get(new ChunkPos(spawnPos));
            if (ticksInArea != null && ticksInArea.intValue() >= HyxcateConfig.METEORS.disallowTime)
                chance /= Math.pow(2, ticksInArea.intValue() / (double) HyxcateConfig.METEORS.disallowTime);
            if (chance <= 0 || event.world.rand.nextFloat() > chance) break meteors;
            if (!event.world.isBlockLoaded(spawnPos, false)) {
                // Add meteor information to cache
                data.cachedMeteorPositions.add(spawnPos);
                data.sendWorldToClients();
            } else {
                // Spawn meteor entity
                EntityFallingMeteor.spawn(data.world, spawnPos);
            }
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        World world = event.getWorld();
        if (world.isRemote) return;
        CapabilityCelestialEvent data = CapabilityCelestialEvent.get(world);
        if (data == null) return;
        Chunk chunk = event.getChunk();
        ChunkPos cp = chunk.getPos();

        // Spawn meteors from the cache
        List<BlockPos> meteors = data.cachedMeteorPositions.stream().filter(p -> p.getX() >= cp.getXStart() && p.getZ() >= cp.getZStart() && p.getX() <= cp.getXEnd() && p.getZ() <= cp.getZEnd()).collect(Collectors.toList());
        for (BlockPos pos : meteors) {
            EntityFallingMeteor.spawn(data.world, pos);
        }
        meteors.forEach(data.cachedMeteorPositions::remove);
        data.sendWorldToClients();
    }
}
