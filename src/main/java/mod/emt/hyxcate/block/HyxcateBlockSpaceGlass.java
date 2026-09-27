package mod.emt.hyxcate.block;

import com.invadermonky.futurefireproof.api.IFireproofBlock;
import mod.emt.hyxcate.init.HyxcateRegistry;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofBlock", striprefs = true)
public class HyxcateBlockSpaceGlass extends BlockGlass implements IFireproofBlock {
    public HyxcateBlockSpaceGlass() {
        super(Material.GLASS, false);
        this.setHarvestLevel("pickaxe", 0);
        this.setSoundType(HyxcateRegistry.LIGHT_CRYSTAL);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.blastproof"));
    }
}
