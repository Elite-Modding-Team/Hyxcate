package de.ellpeck.nyx.blocks;

import de.ellpeck.nyx.init.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class NyxBlockMeteorRockHot extends NyxBlockMeteorRock {

    private final Block convertedBlock;
    private final double cooldownChance;
    private final double tektiteChance;

    public NyxBlockMeteorRockHot(Supplier<Item> droppedItem, Block convertedBlock, EnumParticleTypes particleType, SoundType soundType, int harvestLevel, double cooldownChance, double tektiteChance) {
        super(droppedItem, particleType, soundType, harvestLevel);
        this.convertedBlock = convertedBlock;
        this.cooldownChance = cooldownChance;
        this.tektiteChance = tektiteChance;

        this.setTickRandomly(true);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // There is a rare chance for Tektite to appear instead.
        // Not affected by fortune.
        if (rand.nextDouble() <= tektiteChance) {
            return NyxItems.tektiteGemCluster;
        }

        return this.droppedItem.get();
    }
    
    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
    	// Hot meteor rocks will always hurt you if you try walking on them
        if (!entity.isImmuneToFire() && entity instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entity))
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1);
        super.onEntityWalk(world, pos, entity);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote && rand.nextDouble() < cooldownChance) {
            world.setBlockState(pos, convertedBlock.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
        }
    }
}
