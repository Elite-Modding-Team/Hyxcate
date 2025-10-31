package de.ellpeck.nyx.items.tools;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import de.ellpeck.nyx.items.NyxItemSword;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Courtesy of Desoroxxx for a good portion of the code here
public class NyxToolSolarSword extends NyxItemSword {
    private final Queue<EntitySunstrike> sunstrikeQueue = new LinkedList<>();
    private int delay = 0;

    public NyxToolSolarSword(ToolMaterial material) {
        super(material);
    }

    public static RayTraceResult rayTraceWithExtendedReach(final World world, final EntityPlayer player) {
        // Calculate the start position of the ray trace using player's position and eye height
        final Vec3d startPosition = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);

        // Get player's rotation yaw and compute cosine and sine values
        final float yaw = player.rotationYaw;
        final float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        final float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);

        // Get player's rotation pitch and compute cosine and sine values
        final float pitch = player.rotationPitch;
        final float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        final float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        // Get the reach multiplier
        final float reachMultiplier = 40;

        // Calculate the end position of the ray trace using the player's rotation and reach multiplier
        final Vec3d endPosition = startPosition.add((sinYaw * cosPitch) * reachMultiplier, sinPitch * reachMultiplier, (cosYaw * cosPitch) * reachMultiplier);

        // Perform the ray trace in the world and return the result
        return world.rayTraceBlocks(startPosition, endPosition, false, true, false);
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
                EntitySolarBeam solarBeam = new EntitySolarBeam(player.world, player, player.posX, player.posY + (double) 1.2F, player.posZ, (float) ((double) (player.rotationYawHead + 90.0F) * Math.PI / (double) 180.0F), (float) ((double) (-player.rotationPitch) * Math.PI / (double) 180.0F), 55);
                solarBeam.setHasPlayer(true);
                player.world.spawnEntity(solarBeam);
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 2, false, false));
                world.playSound(null, player.getPosition(), MMSounds.ENTITY_SUPERNOVA_END, SoundCategory.PLAYERS, 0.7F, 0.7F);
            }

            player.getCooldownTracker().setCooldown(this, 90);
        } else if (player.isSneaking()) {
            if (!world.isRemote) {
                RayTraceResult rayTraceResult = rayTraceWithExtendedReach(world, player);

                if ((rayTraceResult != null) && rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                    final BlockPos target = new BlockPos.MutableBlockPos(rayTraceResult.getBlockPos());

                    final int area = 3;

                    for (int i = 0; i < 6; i++) {
                        final int offsetX = world.rand.nextInt((area * 2) + 1) - area;
                        final int offsetZ = world.rand.nextInt((area * 2) + 1) - area;

                        sunstrikeQueue.add(new EntitySunstrike(world, player, target.getX() + offsetX, target.getY(), target.getZ() + offsetZ));
                    }
                }
            }

            world.playSound(null, player.getPosition(), MMSounds.ENTITY_SUPERNOVA_START, SoundCategory.PLAYERS, 0.7F, 0.7F);
            player.getCooldownTracker().setCooldown(this, 40);
        }

        player.swingArm(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !sunstrikeQueue.isEmpty()) {
            if (delay == 0) {
                world.spawnEntity(sunstrikeQueue.poll());
                delay = world.rand.nextInt(2) + 1;
            } else {
                delay--;
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
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.kreknorite_tool"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.solar_sword"));
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.indestructible"));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.shift"));
        }
    }
}
