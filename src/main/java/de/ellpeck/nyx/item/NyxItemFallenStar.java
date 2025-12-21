package de.ellpeck.nyx.item;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxBlocks;
import de.ellpeck.nyx.init.NyxSoundEvents;
import de.ellpeck.nyx.client.sound.NyxSoundFallenEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import javax.annotation.Nullable;

import com.invadermonky.futurefireproof.api.IFireproofItem;

import java.util.List;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofItem", striprefs = true)
public class NyxItemFallenStar extends Item implements IFireproofItem {

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.world.isRemote) {
            if (entityItem.world.rand.nextFloat() >= 0.7F) {
                double mX = entityItem.world.rand.nextGaussian() * 0.05;
                double mY = entityItem.world.rand.nextFloat() * 0.4;
                double mZ = entityItem.world.rand.nextGaussian() * 0.05;
                entityItem.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, entityItem.posX, entityItem.posY + 0.5F, entityItem.posZ, mX, mY, mZ);
            }
            if (entityItem.ticksExisted == 5 && FMLLaunchHandler.side().isClient()) {
                Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundFallenEntity(entityItem, NyxSoundEvents.ENTITY_STAR_IDLE.getSoundEvent(), 1F));
            }
            return false;
        }

        String lastOnGround = Nyx.ID + ":last_on_ground";
        if (entityItem.onGround && !entityItem.getEntityData().getBoolean(lastOnGround)) {
            entityItem.getEntityData().setBoolean(lastOnGround, true);
            this.placeStarAir(entityItem);
        }
        return false;
    }

    private void placeStarAir(EntityItem entityItem) {
        BlockPos pos = entityItem.getPosition();
        if (entityItem.world.getBlockState(pos).getBlock().isReplaceable(entityItem.world, pos)) {
            entityItem.world.setBlockState(pos, NyxBlocks.starAir.getDefaultState());
            return;
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos offset = pos.add(x, y, z);
                    if (entityItem.world.getBlockState(offset).getBlock().isReplaceable(entityItem.world, offset)) {
                        entityItem.world.setBlockState(offset, NyxBlocks.starAir.getDefaultState());
                        return;
                    }
                }
            }
        }
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.fallen_star"));
    }
}
