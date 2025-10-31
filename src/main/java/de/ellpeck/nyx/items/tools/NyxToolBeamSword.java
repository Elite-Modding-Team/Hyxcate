package de.ellpeck.nyx.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.items.NyxItemSword;
import de.ellpeck.nyx.sound.NyxSoundBeamSword;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolBeamSword extends NyxItemSword {
    private final float attackSpeed;

    public NyxToolBeamSword(ToolMaterial material) {
        super(material);
        this.attackSpeed = 1.4F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        super.hitEntity(stack, target, attacker);

        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            player.world.playSound(null, player.posX, player.posY, player.posZ, NyxSoundEvents.beamSwordHit.getSoundEvent(), SoundCategory.PLAYERS, 0.7F, 1.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        return true;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        // Unbreakable
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (stack.getItemDamage() < stack.getMaxDamage() && entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).getCooledAttackStrength(0) > 0.1F) {
            entityLiving.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, NyxSoundEvents.beamSwordSwing.getSoundEvent(), SoundCategory.PLAYERS, 0.5F, 1.5F / (entityLiving.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
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
                Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundBeamSword(stack));
                tag.setBoolean("wasEquipped", true);
            }
        } else {
            tag.setBoolean("wasEquipped", false);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.beam_sword"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.indestructible"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
        }
    }
}
