package de.ellpeck.nyx.item.tool;

import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.item.NyxItemSword;
import de.ellpeck.nyx.init.NyxSoundEvents;
import de.ellpeck.nyx.util.NyxUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolTektiteGreatsword extends NyxItemSword {
    public NyxToolTektiteGreatsword(ToolMaterial material, double attackSpeed, int magnetizationAmount, double paralysisChance, EnumRarity rarity) {
        super(material, attackSpeed, magnetizationAmount, paralysisChance, rarity);
    }

    // TODO: Improve sweep damage calculation
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);

        if (attacker instanceof EntityPlayer) {
            for (EntityLivingBase nearbyLivingEntity : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(attacker) && !nearbyLivingEntity.isEntityEqual(attacker)) {
                    float attribute = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                    float sweepCalculation = (this.getAttackDamage() + 4.0F) + EnchantmentHelper.getSweepingDamageRatio(attacker) * attribute;
                    float knockback = EnchantmentHelper.getKnockbackModifier(attacker);

                    if (NyxUtils.setChance(this.paralysisChance.getAmount())) {
                        nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
                        nearbyLivingEntity.world.playSound(null, nearbyLivingEntity.posX, nearbyLivingEntity.posY, nearbyLivingEntity.posZ, NyxSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (nearbyLivingEntity.world.rand.nextFloat() * 0.4F + 1.2F));
                        nearbyLivingEntity.knockBack(attacker, knockback * 0.5F, MathHelper.sin(attacker.rotationYaw * 0.0175F), (-MathHelper.cos(attacker.rotationYaw * 0.0175F)));
                        nearbyLivingEntity.addPotionEffect(new PotionEffect(NyxPotions.PARALYSIS, 10 * 20, 0));
                    } else {
                        nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
                        nearbyLivingEntity.knockBack(attacker, knockback * 0.5F, MathHelper.sin(attacker.rotationYaw * 0.0175F), (-MathHelper.cos(attacker.rotationYaw * 0.0175F)));
                    }
                }
            }

            ((EntityPlayer) attacker).spawnSweepParticles();
        }

        return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        // Unbreakable
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            NyxUtils.setUnbreakable(stack);
            list.add(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.tektite_greatsword"));
    }
}
