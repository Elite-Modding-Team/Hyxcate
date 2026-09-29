package mod.emt.hyxcate.event.tool;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.ColorUtil;
import mod.emt.hyxcate.util.ParticleUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
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
            long leapTime = player.world.getTotalWorldTime() - player.getEntityData().getLong(Hyxcate.ID + ":leap_start");

            if (leapTime >= 5) {
                if (!player.world.isRemote) {
                    int radius = 6;
                    AxisAlignedBB area = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                    DamageSource source = DamageSource.causePlayerDamage(player);
                    float damage = HyxcateConfig.GENERAL.celestialWarhammerAbilityDamage * Math.min((leapTime - 5) / 35F, 1);

                    for (EntityLivingBase entity : player.world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE)) {
                        if (entity != player && !entity.isOnSameTeam(player)) {
                            entity.addPotionEffect(new PotionEffect(HyxcatePotions.ASTRAL_EROSION, 8 * 20, 0, false, false));
                            entity.attackEntityFrom(source, damage);
                            entity.knockBack(player, 3.0F, player.posX - entity.posX, player.posZ - entity.posZ);
                            entity.motionY = 1;
                        }
                    }
                    player.world.playSound(null, player.getPosition(), HyxcateSoundEvents.ITEM_CELESTIAL_WARHAMMER_SMASH.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                } else {
                    for (int i = 0; i < 60; i++) {
                        ParticleUtil.spawnStarSpark(player.world, (float) player.posX + (player.world.rand.nextFloat() - 0.5F) * 2.0F, (float) player.posY + 0.5F, (float) player.posZ + (player.world.rand.nextFloat() - 0.5F) * 2.0F,
                                (player.world.rand.nextFloat() - 0.5F), player.world.rand.nextFloat(), (player.world.rand.nextFloat() - 0.5F), 0.8F, 10.0F, 150, ColorUtil.SHOOTING_STAR);
                    }
                }

                player.getEntityData().removeTag(Hyxcate.ID + ":leap_start");
            }
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
