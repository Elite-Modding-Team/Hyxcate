package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public class NyxBlocks {

    public static Block starAir;
    public static Block starBlock;
    public static Block crackedStarBlock;
    public static Block chiseledStarBlock;
    public static Block starStairs;
    public static Block starSlab;
    public static Block meteoriteRock;
    public static Block meteoriteRockHot;
    public static Block frezariteRock;
    public static Block kreknoriteRock;
    public static Block cyberCrystal;
    public static Block meteoriteBlock;
    public static Block frezariteBlock;
    public static Block kreknoriteBlock;
    public static Block tektiteBlock;
    public static Block meteorGlass;

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();

        reg.registerAll(
                starAir = new NyxBlockStarAir(),
                starBlock = initBlock(new NyxBlock(Material.ROCK, MapColor.GOLD, 2.0F, 2000.0F, NyxRegistry.LIGHT_CRYSTAL), "star_block", ItemBlock::new),
                crackedStarBlock = initBlock(new NyxBlock(Material.ROCK, MapColor.GOLD, 2.0F, 2000.0F, NyxRegistry.LIGHT_CRYSTAL), "cracked_star_block", ItemBlock::new),
                chiseledStarBlock = initBlock(new NyxBlock(Material.ROCK, MapColor.GOLD, 2.0F, 2000.0F, NyxRegistry.LIGHT_CRYSTAL), "chiseled_star_block", ItemBlock::new),
                starStairs = initBlock(new NyxBlockStairs(starBlock.getDefaultState()), "star_stairs", ItemBlock::new),
                meteoriteRock = initBlock(new NyxBlockMeteorRock(() -> NyxItems.meteoriteShard, EnumParticleTypes.SUSPENDED_DEPTH, NyxRegistry.METEORIC_ROCK, 3).setHardness(4.0F).setResistance(2000.0F), "meteorite_rock", ItemBlock::new),
                meteoriteRockHot = initBlock(new NyxBlockMeteorRockHot(() -> NyxItems.meteoriteShard, meteoriteRock, EnumParticleTypes.FLAME, NyxRegistry.METEORIC_ROCK, 3, 0.05D, 0.05D).setHardness(20.0F).setResistance(3000.0F), "meteorite_rock_hot", ItemBlock::new),
                frezariteRock = initBlock(new NyxBlockMeteorRockHot(() -> NyxItems.frezariteCrystal, Blocks.PACKED_ICE, EnumParticleTypes.SNOW_SHOVEL, NyxRegistry.LIGHT_CRYSTAL, 4, 0.025D, 0.075D), "frezarite_rock", ItemBlock::new).setHardness(30.0F).setResistance(3000.0F),
                kreknoriteRock = initBlock(new NyxBlockMeteorRockHot(() -> NyxItems.kreknoriteShard, Blocks.OBSIDIAN, EnumParticleTypes.FLAME, NyxRegistry.METEORIC_ROCK, 4, 0.025D, 0.075D), "kreknorite_rock", ItemBlock::new).setHardness(30.0F).setResistance(3000.0F),
                cyberCrystal = new NyxBlockCyberCrystal(),
                meteoriteBlock = initBlock(new Block(Material.ROCK).setHardness(3), "meteorite_block", ItemBlock::new),
                frezariteBlock = initBlock(new NyxBlock(Material.ROCK, MapColor.ICE, 3.0F, 3000.0F, NyxRegistry.LIGHT_CRYSTAL), "frezarite_block", ItemBlock::new),
                kreknoriteBlock = initBlock(new Block(Material.ROCK).setHardness(3), "kreknorite_block", ItemBlock::new),
                tektiteBlock = initBlock(new NyxBlock(Material.ROCK, MapColor.RED, 3.0F, 3000.0F, NyxRegistry.LIGHT_CRYSTAL), "tektite_block", ItemBlock::new),
                meteorGlass = initBlock(new NyxBlockSpaceGlass().setHardness(2).setResistance(3000), "meteor_glass", ItemBlock::new)
        );

        // TODO: Set resistance and map color as well
        NyxBlockSlab[] slabs = NyxBlockSlab.makeSlab("star_slab", Material.ROCK, NyxRegistry.LIGHT_CRYSTAL, 2.0F);
        reg.registerAll(slabs);
        starSlab = slabs[0];
    }

    public static Block initBlock(Block block, String name, Function<Block, ItemBlock> item) {
        block.setRegistryName(new ResourceLocation(Nyx.ID, name));
        block.setTranslationKey(Nyx.ID + "." + block.getRegistryName().getPath());
        block.setCreativeTab(NyxRegistry.CREATIVE_TAB);
        if (item != null)
            NyxItems.initItem(item.apply(block), name);
        return block;
    }
}
