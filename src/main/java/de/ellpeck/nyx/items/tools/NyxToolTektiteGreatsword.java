package de.ellpeck.nyx.items.tools;

import de.ellpeck.nyx.items.NyxItemSword;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import de.ellpeck.nyx.util.Utils;
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
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolTektiteGreatsword extends NyxItemSword {
    public NyxToolTektiteGreatsword(ToolMaterial material, double attackSpeed, double paralysisChance, EnumRarity rarity) {
        super(material, attackSpeed, paralysisChance, rarity);
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

                    // TODO: Replace this with a unique Paralysis potion effect
                    if (Utils.setChance(this.paralysisChance.getAmount())) {
                        nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
                        nearbyLivingEntity.world.playSound(null, nearbyLivingEntity.posX, nearbyLivingEntity.posY, nearbyLivingEntity.posZ, NyxSoundEvents.paralysis.getSoundEvent(), SoundCategory.PLAYERS, 0.8F, 1.5F / (nearbyLivingEntity.world.rand.nextFloat() * 0.4F + 1.2F));
                        nearbyLivingEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 15 * 20, 9));
                    } else {
                        nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
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
            Utils.setUnbreakable(stack);
            list.add(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.tektite_greatsword"));
    }
}
