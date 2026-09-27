package mod.emt.hyxcate.compat.jei;

import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateBlocks;
import mod.emt.hyxcate.init.HyxcateItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEI implements IModPlugin {
    private static void addJEIInfo(IModRegistry registry, ItemStack stack) {
        registry.addIngredientInfo(stack, VanillaTypes.ITEM, stack.getTranslationKey() + ".jei_desc");
    }

    @Override
    public void register(IModRegistry registry) {
        // JEI Info
        addJEIInfo(registry, new ItemStack(HyxcateItems.fallenStar));

        // JEI Info - Meteors
        if (HyxcateConfig.MASTER_SWITCHES.meteorGearEnabled) {
            addJEIInfo(registry, new ItemStack(HyxcateItems.frezariteBoots));
        }

        addJEIInfo(registry, new ItemStack(HyxcateBlocks.meteoriteRockHot));
        addJEIInfo(registry, new ItemStack(HyxcateBlocks.meteoriteRock));
        addJEIInfo(registry, new ItemStack(HyxcateBlocks.frezariteRock));
        addJEIInfo(registry, new ItemStack(HyxcateBlocks.kreknoriteRock));
        addJEIInfo(registry, new ItemStack(HyxcateItems.tektiteGemCluster));
        addJEIInfo(registry, new ItemStack(HyxcateBlocks.cyberCrystal));
    }
}
