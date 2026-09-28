package mod.emt.hyxcate.event.tool;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CelestialWarhammerEvent {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;

        // Celestial Warhammer Ability
        // We check fall distance because we need the player to be done falling when removing the tag
        if (player.onGround && player.fallDistance <= 0 && player.getEntityData().hasKey(Hyxcate.ID + ":leap_start")) {
            if (!player.world.isRemote) {
                long leapTime = player.world.getTotalWorldTime() - player.getEntityData().getLong(Hyxcate.ID + ":leap_start");

                if (leapTime >= 5) {
                    int radius = 6;
                    AxisAlignedBB area = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                    DamageSource source = DamageSource.causePlayerDamage(player);
                    float damage = HyxcateConfig.GENERAL.celestialWarhammerAbilityDamage * Math.min((leapTime - 5) / 35F, 1);

                    for (EntityLivingBase entity : player.world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE)) {
                        if (!entity.isOnSameTeam(player)) {
                            if (entity == player) continue;
                            entity.addPotionEffect(new PotionEffect(HyxcatePotions.ASTRAL_EROSION, 8 * 20, 0, false, false));
                            entity.attackEntityFrom(source, damage);
                            entity.knockBack(player, 3.0F, player.posX - entity.posX, player.posZ - entity.posZ);
                            entity.motionY = 1;
                        }
                    }

                    if (!player.world.isRemote) {
                        int particleAmount = 90;
                        double particleDistance = 3.0D;
                        IBlockState state = player.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ).down());
                        int blockId = Block.getStateId(state);

                        // TODO: Cooler particles
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.END_ROD, player.posX, player.posY + 1.0D, player.posZ, particleAmount, particleDistance, 0.0D, particleDistance, 0.5D);
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, player.posX, player.posY, player.posZ, particleAmount * 2, particleDistance, 0.0D, particleDistance, 1.0D, blockId);
                    }

                    player.world.playSound(null, player.getPosition(), HyxcateSoundEvents.ITEM_CELESTIAL_WARHAMMER_SMASH.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }

            player.getEntityData().removeTag(Hyxcate.ID + ":leap_start");
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        // Celestial Warhammer Leap Ability
        if (event.getEntityLiving().getEntityData().hasKey(Hyxcate.ID + ":leap_start")) {
            event.setDamageMultiplier(0);
        }
    }
}
