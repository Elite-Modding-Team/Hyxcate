package mod.emt.hyxcate.block;

import com.invadermonky.futurefireproof.api.IFireproofBlock;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
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
public class HyxcateBlockStairs extends BlockStairs implements IFireproofBlock {
    public HyxcateBlockStairs(IBlockState modelState) {
        super(modelState);
        this.useNeighborBrightness = true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.blastproof"));
    }
}
