package de.ellpeck.nyx.block;

import java.util.List;

import javax.annotation.Nullable;

import com.invadermonky.futurefireproof.api.IFireproofBlock;

import de.ellpeck.nyx.init.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofBlock", striprefs = true)
public class NyxBlock extends Block implements IFireproofBlock {
    public NyxBlock(Material material, MapColor mapColor, float hardness, float resistance, SoundType soundType) {
        super(material, mapColor);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(soundType);
    }

    public NyxBlock(Material material, MapColor mapColor, float hardness, SoundType soundType) {
        super(material, mapColor);
        this.setHardness(hardness);
        this.setSoundType(soundType);
    }
    
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == NyxBlocks.chiseledStarBlock || this == NyxBlocks.crackedStarBlock || this == NyxBlocks.starBlock) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.blastproof"));
        }
    }
}
