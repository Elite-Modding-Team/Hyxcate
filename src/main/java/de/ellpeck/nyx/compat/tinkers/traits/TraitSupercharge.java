package de.ellpeck.nyx.compat.tinkers.traits;

import de.ellpeck.nyx.Nyx;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSupercharge extends AbstractTrait {
    public TraitSupercharge() {
        super(Nyx.ID + "." + "supercharge", 0xC82323);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (random.nextInt(10) == 0 && wasEffective) {
            player.world.playSound(null, player.posX, player.posY, player.posZ, Sounds.shocking_discharge, SoundCategory.PLAYERS, 0.5F, 0.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5 * 20, 2));
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (random.nextInt(10) == 0 && wasHit) {
            player.world.playSound(null, player.posX, player.posY, player.posZ, Sounds.shocking_discharge, SoundCategory.PLAYERS, 0.5F, 0.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5 * 20, 2));
        }
    }
}
