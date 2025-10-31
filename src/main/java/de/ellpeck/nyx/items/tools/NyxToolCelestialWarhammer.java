package de.ellpeck.nyx.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.items.NyxItemSword;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IRarity;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolCelestialWarhammer extends NyxItemSword {
    private final float attackSpeed;

    public NyxToolCelestialWarhammer(ToolMaterial material) {
        super(material);
        this.attackSpeed = 0.8F;
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

                    nearbyLivingEntity.knockBack(attacker, 2.0F, MathHelper.sin(attacker.rotationYaw * 0.02F), -MathHelper.cos(attacker.rotationYaw * 0.02F));
                    nearbyLivingEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), sweepCalculation);
                }
            }

            attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.PLAYERS, 1.0F, 0.025F / (attacker.world.rand.nextFloat() * 0.4F + 1.2F));
            ((EntityPlayer) attacker).spawnSweepParticles();
        }

        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if (!entityLiving.onGround)
            return;

        int useTime = this.getMaxItemUseDuration(stack) - timeLeft;

        if (useTime < 20)
            return;

        float modifier = MathHelper.clamp((useTime - 20.0F) / 5.0F, 1.0F, 2.5F);

        entityLiving.motionX += -modifier * MathHelper.sin(entityLiving.rotationYaw * 0.02F);
        entityLiving.motionY += 1.250D * modifier;
        entityLiving.motionZ += modifier * MathHelper.cos(entityLiving.rotationYaw * 0.02F);
        entityLiving.getEntityData().setLong(Nyx.ID + ":leap_start", world.getTotalWorldTime());

        if (entityLiving instanceof EntityPlayer) {
            entityLiving.swingArm(entityLiving.getActiveHand());
            ((EntityPlayer) entityLiving).getCooldownTracker().setCooldown(this, 2 * 20);
        }

        if (!world.isRemote) {
            world.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_IRONGOLEM_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
            world.playSound(null, entityLiving.getPosition(), NyxSoundEvents.hammerSmash.getSoundEvent(), SoundCategory.PLAYERS, 0.35F, 1.5F / (world.rand.nextFloat() * 0.4F + 0.8F));
            world.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.PLAYERS, 0.35F, 0.75F / (world.rand.nextFloat() * 0.4F + 0.8F));
            ((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, false, entityLiving.posX, entityLiving.posY + entityLiving.getEyeHeight(), entityLiving.posZ, 30, 0.25, 0.25, 0.25, 0.05);
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 36000;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        double attackDamageMod = this.getAttackDamage() + 3.0D;
        double attackSpeedMod = this.attackSpeed - 4.0D;

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", attackDamageMod, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", attackSpeedMod, 0));
        }

        return multimap;
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
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.celestial_warhammer"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.indestructible"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
        }
    }
}
