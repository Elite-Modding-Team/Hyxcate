package de.ellpeck.nyx.item;

import de.ellpeck.nyx.event.NyxEvents;
import de.ellpeck.nyx.init.NyxAttributes;
import de.ellpeck.nyx.init.NyxItems;
import de.ellpeck.nyx.item.tool.INyxTool;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.invadermonky.futurefireproof.api.IFireproofItem;

import java.util.List;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofItem", striprefs = true)
public class NyxItemPickaxe extends ItemPickaxe implements INyxTool, IFireproofItem {
    public double attackSpeed;
    public AttributeModifier paralysisChance;
    public EnumRarity rarity;
    private final ToolMaterial material;

    public NyxItemPickaxe(ToolMaterial material, double attackSpeed, double paralysisChance, EnumRarity rarity) {
        super(material);
        this.attackSpeed = attackSpeed;
        this.paralysisChance = new AttributeModifier(NyxAttributes.PARALYSIS_ID.toString(), paralysisChance, 1);
        this.material = material;
        this.rarity = rarity;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase target = (EntityLivingBase) entity;

            if (target.getHealth() > 0 && entity instanceof EntityLivingBase) {
                if (this == NyxItems.frezaritePickaxe) {
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
                } else if (this == NyxItems.kreknoritePickaxe) {
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

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (isSelected && this == NyxItems.meteoritePickaxe) {
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
        if (GuiScreen.isShiftKeyDown()) {
            if (this.getToolMaterial() == NyxItems.frezariteToolMaterial) {
                tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_tool"));
            } else if (this.getToolMaterial() == NyxItems.kreknoriteToolMaterial) {
                tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.kreknorite_tool"));
            }
        } else if (this.getToolMaterial() == NyxItems.frezariteToolMaterial || this.getToolMaterial() == NyxItems.kreknoriteToolMaterial) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
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

    @Override
    public ToolMaterial getToolMaterial() {
        return material;
    }
}
