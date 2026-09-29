package mod.emt.hyxcate.item.tool;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.item.HyxcateItemSword;
import mod.emt.hyxcate.util.ColorUtil;
import mod.emt.hyxcate.util.ParticleUtil;
import mod.emt.hyxcate.util.helpers.NBTHelper;
import mod.emt.hyxcate.util.helpers.SoundHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HyxcateToolCelestialWarhammer extends HyxcateItemSword {
    public HyxcateToolCelestialWarhammer(ToolMaterial material, double attackSpeed, int magnetizationAmount, double paralysisChance, EnumRarity rarity) {
        super(material, attackSpeed, magnetizationAmount, paralysisChance, rarity);
    }

    // TODO: Improve sweep damage calculation
    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);

        if (attacker instanceof EntityPlayer) {
            for (EntityLivingBase nearbyLivingEntity : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(attacker) && !nearbyLivingEntity.isEntityEqual(attacker)) {
                    float attribute = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                    float sweepCalculation = (this.getAttackDamage() + 4.0F) + EnchantmentHelper.getSweepingDamageRatio(attacker) * attribute;
                    float knockback = 2.0F + (EnchantmentHelper.getKnockbackModifier(attacker) * 0.5F);

                    nearbyLivingEntity.knockBack(attacker, knockback, MathHelper.sin(attacker.rotationYaw * 0.02F), (-MathHelper.cos(attacker.rotationYaw * 0.02F)));
                    nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
                    nearbyLivingEntity.addPotionEffect(new PotionEffect(HyxcatePotions.ASTRAL_EROSION, 8 * 20, 0, false, false));
                }
            }

            for (int i = 0; i < 8; i++) {
                float speed = 0.12F + attacker.world.rand.nextFloat() * 0.10F;
                double vx = (attacker.world.rand.nextDouble() - 0.5D) * speed;
                double vy = (attacker.world.rand.nextDouble() - 0.5D) * speed;
                double vz = (attacker.world.rand.nextDouble() - 0.5D) * speed;
                ParticleUtil.spawnStarSpark(attacker.world, (float) target.posX + (attacker.world.rand.nextFloat() - 0.5F) * 0.4F, (float) target.posY + target.height * 0.75F, (float) target.posZ + (attacker.world.rand.nextFloat() - 0.5F) * 0.4F,
                        (float) vx, (float) vy, (float) vz, 0.8F, 5.0F, 100, ColorUtil.SHOOTING_STAR);
            }

            attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, HyxcateSoundEvents.ITEM_CELESTIAL_WARHAMMER_HIT.getSoundEvent(), SoundCategory.PLAYERS, 1.35F, 1.0F / (attacker.world.rand.nextFloat() * 0.4F + 1.2F));
            ((EntityPlayer) attacker).spawnSweepParticles();
        }

        return true;
    }

    @Override
    public void setDamage(@Nonnull ItemStack stack, int damage) {
        // Unbreakable
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, EntityLivingBase entityLiving, int timeLeft) {
        if (!entityLiving.onGround)
            return;

        int useTime = this.getMaxItemUseDuration(stack) - timeLeft;

        if (useTime < 0)
            return;

        float modifier = MathHelper.clamp((useTime - 20.0F) / 5.0F, 1.0F, 2.5F);

        entityLiving.motionY += 1.250D * modifier;
        if (HyxcateConfig.GENERAL.celestialWarhammerForwardLaunch) {
            entityLiving.motionX += -modifier * MathHelper.sin(entityLiving.rotationYaw * 0.017453292F);
            entityLiving.motionZ += modifier * MathHelper.cos(entityLiving.rotationYaw * 0.017453292F);
        }
        entityLiving.getEntityData().setLong(Hyxcate.ID + ":leap_start", world.getTotalWorldTime());

        if (entityLiving instanceof EntityPlayer) {
            entityLiving.swingArm(entityLiving.getActiveHand());
            ((EntityPlayer) entityLiving).getCooldownTracker().setCooldown(this, 2 * 20);
        }

        for (int i = 0; i < 20; i++) {
            float speed = 0.4F + world.rand.nextFloat() * 0.04F;
            ParticleUtil.spawnStarSpark(world, (float) entityLiving.posX + (world.rand.nextFloat() - 0.5F), (float) entityLiving.posY + entityLiving.height * 0.5F, (float) entityLiving.posZ + (world.rand.nextFloat() - 0.5F),
                    (world.rand.nextFloat() - 0.5F) * speed, (world.rand.nextFloat() - 0.5F) * speed, (world.rand.nextFloat() - 0.5F) * speed, 0.8F, 5.0F, 100, ColorUtil.SHOOTING_STAR);
        }

        if (!world.isRemote) {
            world.playSound(null, entityLiving.getPosition(), HyxcateSoundEvents.ITEM_CELESTIAL_WARHAMMER_HIT.getSoundEvent(), SoundCategory.PLAYERS, 1.35F, 1.5F / (world.rand.nextFloat() * 0.4F + 0.8F));
        } else if (FMLLaunchHandler.side().isClient()) {
            SoundHelper.playClientSoundWarhammer(world);
        }
    }

    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 36000;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        if (enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            NBTHelper.setUnbreakable(stack);
            list.add(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.celestial_warhammer"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.shift"));
        }
    }
}
