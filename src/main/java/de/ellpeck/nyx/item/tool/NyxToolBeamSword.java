package de.ellpeck.nyx.item.tool;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxSoundEvents;
import de.ellpeck.nyx.item.NyxItemSword;
import de.ellpeck.nyx.util.NyxUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolBeamSword extends NyxItemSword {
    public NyxToolBeamSword(ToolMaterial material, double attackSpeed, int magnetizationAmount, double paralysisChance, EnumRarity rarity) {
        super(material, attackSpeed, magnetizationAmount, paralysisChance, rarity);
        this.attackSpeed = 1.4F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);

        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            player.world.playSound(null, player.posX, player.posY, player.posZ, NyxSoundEvents.ITEM_BEAM_SWORD_HIT.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        // Unbreakable
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (stack.getItemDamage() < stack.getMaxDamage() && entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).getCooledAttackStrength(0) > 0.1F) {
            entityLiving.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, NyxSoundEvents.ITEM_BEAM_SWORD_SWING.getSoundEvent(), SoundCategory.PLAYERS, 0.5F, 1.5F / (entityLiving.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        return super.onEntitySwing(entityLiving, stack);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof EntityPlayerSP) || !world.isRemote) {
            return;
        }
        NBTTagCompound tag = stack.getOrCreateSubCompound(Nyx.ID);
        boolean wasEquipped = tag.getBoolean("wasEquipped");
        if (isSelected) {
            if (!wasEquipped) {
                NyxUtils.playClientSoundBeamSword(stack);
                tag.setBoolean("wasEquipped", true);
            }
        } else {
            tag.setBoolean("wasEquipped", false);
        }
    }

    // This can cause the idle sound loop to stop, so let's disable this
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.beam_sword"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
        }
    }
}
