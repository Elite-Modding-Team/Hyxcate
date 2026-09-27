package mod.emt.hyxcate.block;

import com.invadermonky.futurefireproof.api.IFireproofBlock;
import mod.emt.hyxcate.init.HyxcateBlocks;
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

import javax.annotation.Nullable;
import java.util.List;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofBlock", striprefs = true)
public class HyxcateBlock extends Block implements IFireproofBlock {
    public HyxcateBlock(Material material, MapColor mapColor, float hardness, float resistance, SoundType soundType) {
        super(material, mapColor);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(soundType);
    }

    public HyxcateBlock(Material material, MapColor mapColor, float hardness, SoundType soundType) {
        super(material, mapColor);
        this.setHardness(hardness);
        this.setSoundType(soundType);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == HyxcateBlocks.chiseledStarBlock || this == HyxcateBlocks.crackedStarBlock || this == HyxcateBlocks.starBlock) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.blastproof"));
        }
    }
}
