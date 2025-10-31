package de.ellpeck.nyx.items.tools;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBoulder;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import de.ellpeck.nyx.items.NyxItemAxe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IRarity;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Courtesy of Desoroxxx for a good portion of the code here
public class NyxToolLunarAxe extends NyxItemAxe {
    private final Queue<EntityBoulder> boulderQueue = new LinkedList<>();

    public NyxToolLunarAxe(ToolMaterial material, float damage, float speed) {
        super(material, damage, speed);
    }

    public static void spawnExplosionParticleAtEntity(final Entity entity, final int amount) {
        // Generate random values for particle velocity and offsets in each axis
        final double velocity = entity.world.rand.nextGaussian() / 8;
        final double xOffset = entity.world.rand.nextGaussian() / 12;
        final double yOffset = entity.world.rand.nextGaussian() / 12;
        final double zOffset = entity.world.rand.nextGaussian() / 12;

        // Spawn explosion particles around the entity with the specified amount and random offsets and velocity
        ((WorldServer) entity.getEntityWorld()).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entity.posX, entity.posY, entity.posZ, amount, xOffset, yOffset, zOffset, MathHelper.clamp(velocity, 0.06, 1));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!player.isSneaking()) {
            if (!world.isRemote) {
                EntityIceBall iceBall = new EntityIceBall(world, player);

                Vec3d offset = player.getLookVec().scale(1.5);

                iceBall.setPositionAndRotation(player.posX + offset.x, player.posY + 1.5, player.posZ + offset.z, (float) Math.toRadians(player.rotationYaw), (float) Math.toRadians(player.rotationPitch));
                iceBall.shoot(player.getLookVec().x, player.getLookVec().y, player.getLookVec().z, 1.6F, 0.0F);

                world.spawnEntity(iceBall);
            }

            world.playSound(null, player.getPosition(), MMSounds.ENTITY_FROSTMAW_ICEBALL_SHOOT, SoundCategory.PLAYERS, 2.0F, 0.7F);
            player.getCooldownTracker().setCooldown(this, 40);
        } else if (player.isSneaking()) {
            if (!world.isRemote) {
                for (int i = 0; i < 15; i++) {
                    final double yaw = Math.toRadians(player.rotationYaw);
                    double pitch = Math.toRadians(player.rotationPitch);

                    if (Math.abs(Math.toDegrees(pitch)) < 8) // Check if the pitch is in the deadzone if true set the pitch to 0
                        pitch = 0;

                    if (pitch <= -0.85) // Check if the pitch is above the max height or under the min height if true set the pitch to the maximum usable upward stairway
                        pitch = -0.84;
                    else if (pitch >= 0.85) // Check if the pitch is below the min height if true set the pitch to the minimum usable downward stairway
                        pitch = 0.84;

                    final double x = -Math.sin(yaw) * Math.cos(pitch);
                    double y = -Math.sin(pitch);
                    final double z = Math.cos(yaw) * Math.cos(pitch);

                    if (pitch <= -0.19)
                        y -= 0.25;

                    double yOffset = 0.8;

                    if (pitch <= -0.41) // Check if the pitch is above the min height to be an upward stairway, if true set the yOffset to 0.75 to allow the player to climb on the stairs
                        yOffset = 1.2;

                    final double distance = 2 + i * 1.5;

                    EntityBoulder boulder = new EntityBoulder(world, player, 0, Blocks.FROSTED_ICE.getDefaultState());

                    boulder.setDeathTime(400);
                    boulder.setPosition(player.posX + x * distance, (player.posY - yOffset) + y * distance, player.posZ + z * distance);

                    if (!world.getBlockState(boulder.getPosition()).getMaterial().isSolid())
                        boulderQueue.add(boulder);
                }
            }

            world.playSound(null, player.getPosition(), MMSounds.ENTITY_FROSTMAW_ICEBALL_SHOOT, SoundCategory.PLAYERS, 2.0F, 1.5F);
            player.getCooldownTracker().setCooldown(this, 40);
        }

        player.swingArm(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            EntityBoulder boulder = boulderQueue.poll();

            if (boulder != null) {
                world.spawnEntity(boulder);
                spawnExplosionParticleAtEntity(boulder, 25);
            }
        }
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
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_tool"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.lunar_axe"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.indestructible"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
        }
    }
}
