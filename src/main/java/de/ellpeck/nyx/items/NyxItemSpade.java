package de.ellpeck.nyx.items;

import de.ellpeck.nyx.events.NyxEvents;
import de.ellpeck.nyx.init.NyxAttributes;
import de.ellpeck.nyx.init.NyxItems;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.List;

public class NyxItemSpade extends ItemSpade {
    public double attackSpeed;
    public AttributeModifier paralysisChance;
    public EnumRarity rarity;
    
    public NyxItemSpade(ToolMaterial material, double attackSpeed, double paralysisChance, EnumRarity rarity) {
        super(material);
        this.attackSpeed = attackSpeed;
        this.paralysisChance = new AttributeModifier(NyxAttributes.PARALYSIS_ID.toString(), paralysisChance, 1);
        this.rarity = rarity;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase target = (EntityLivingBase) entity;

            if (target.getHealth() > 0 && entity instanceof EntityLivingBase) {
                if (this == NyxItems.frezariteShovel) {
                    entity.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY + 1.0D, entity.posZ, 0.0D, 0.0D, 0.0D);

                    for (Entity nearbyLivingEntity : entity.world.getEntitiesWithinAABBExcludingEntity(player, entity.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                        if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(player) && !nearbyLivingEntity.isEntityEqual(player)) {
                            for (int i = 0; i < 20; ++i) {
                                double d0 = entity.world.rand.nextGaussian() * 0.02D;
                                double d1 = entity.world.rand.nextGaussian() * 0.02D;
                                double d2 = entity.world.rand.nextGaussian() * 0.02D;
                                nearbyLivingEntity.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, nearbyLivingEntity.posX + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d0 * 10.0D, nearbyLivingEntity.posY + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.height) - d1 * 10.0D, nearbyLivingEntity.posZ + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d2 * 10.0D, d0, d1, d2);
                            }
                        }
                    }
                } else if (this == NyxItems.kreknoriteShovel) {
                    entity.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY + 1.0D, entity.posZ, 0.0D, 0.0D, 0.0D);

                    for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(player, target.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                        if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(player) && !nearbyLivingEntity.isEntityEqual(player)) {
                            for (int i = 0; i < 8; ++i) {
                                double d0 = target.world.rand.nextGaussian() * 0.02D;
                                double d1 = target.world.rand.nextGaussian() * 0.02D;
                                double d2 = target.world.rand.nextGaussian() * 0.02D;
                                nearbyLivingEntity.world.spawnParticle(EnumParticleTypes.LAVA, nearbyLivingEntity.posX + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d0 * 10.0D, nearbyLivingEntity.posY + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.height) - d1 * 10.0D, nearbyLivingEntity.posZ + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d2 * 10.0D, d0, d1, d2);
                            }
                        }
                    }
                }
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    // TODO: AoE ability should be a SubscribeEvent
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (this == NyxItems.frezariteShovel) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.frezariteHit.getSoundEvent(), SoundCategory.PLAYERS, 0.75F, 2.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));

            // Explosion deals AoE damage
            for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(attacker, target.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(attacker) && !nearbyLivingEntity.isEntityEqual(attacker)) {
                    if (nearbyLivingEntity instanceof EntityLiving) {
                        EntityLiving entity = (EntityLiving) nearbyLivingEntity;

                        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
                    }

                    nearbyLivingEntity.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.attackDamage + 4.0F);
                }
            }
        } else if (this == NyxItems.kreknoriteShovel) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.kreknoriteHit.getSoundEvent(), SoundCategory.PLAYERS, 1.25F, 1.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));

            // Explosion deals AoE damage
            for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(attacker, target.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(attacker) && !nearbyLivingEntity.isEntityEqual(attacker)) {
                    if (nearbyLivingEntity instanceof EntityLiving) {
                        EntityLiving entity = (EntityLiving) nearbyLivingEntity;

                        entity.setFire(10);
                        entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 10 * 20, 1));
                    }

                    nearbyLivingEntity.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.attackDamage + 4.0F);
                }
            }
        }

        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (isSelected && this == NyxItems.meteoriteShovel) {
            EntityPlayer player = (EntityPlayer) entityIn;

            // If an item with Magnetization exists, cancel out the armor's effect in favor of the leveled enchants
            if (NyxEvents.magnetizationLevel == 0) {
                NyxEvents.pullItems(player, 4.0D, 0.0125F);
            }
        }
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return rarity;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == NyxItems.frezariteShovel) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_tool"));
        } else if (this == NyxItems.kreknoriteShovel) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.kreknorite_tool"));
        } else if (this == NyxItems.meteoriteShovel) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.meteorite_tool"));
        }
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, NyxAttributes.ATTACK_DAMAGE_ID.toString(), this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, NyxAttributes.ATTACK_SPEED_ID.toString(), this.attackSpeed - 4.0D, 0));
            multimap.put(NyxAttributes.PARALYSIS.getName(), paralysisChance);
        }

        return multimap;
    }
}
