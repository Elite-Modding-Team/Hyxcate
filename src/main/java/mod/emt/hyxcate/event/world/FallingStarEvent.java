package mod.emt.hyxcate.event.world;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventStarShower;
import mod.emt.hyxcate.compat.gamestages.GameStages;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.entity.EntityFallingStar;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FallingStarEvent {
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        HyxcateWorld data = HyxcateWorld.get(event.world);
        if (data == null) return;
        data.update();

        // Falling Stars
        if (!event.world.isRemote && !WorldUtil.isDaytime(event.world) && event.world.getTotalWorldTime() % 1200 == 0) {
            int dimension = event.world.provider.getDimensionType().getId();
            if (HyxcateData.ALLOWED_DIMENSIONS_LUNAR.contains(dimension)) {
                for (EntityPlayer player : event.world.playerEntities) {
                    if (!GameStages.checkGameStageFallingStarEvents(player)) continue;
                    if (event.world.rand.nextFloat() > (data.currentLunarEvent instanceof LunarEventStarShower ? HyxcateConfig.FALLING_STARS.chanceShowerM : HyxcateConfig.FALLING_STARS.chanceM))
                        continue;
                    BlockPos startPos = player.getPosition().add(event.world.rand.nextGaussian() * 20, 0, event.world.rand.nextGaussian() * 20);
                    startPos = event.world.getPrecipitationHeight(startPos).up(MathHelper.getInt(event.world.rand, 32, 64));

                    EntityFallingStar star = new EntityFallingStar(event.world);
                    star.setPosition(startPos.getX(), startPos.getY(), startPos.getZ());
                    event.world.spawnEntity(star);
                }
            }
        }
    }
}
