package de.ellpeck.nyx.compat.jei;

import de.ellpeck.nyx.init.NyxBlocks;
import de.ellpeck.nyx.init.NyxItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEI implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        // JEI Info
        addJEIInfo(registry, new ItemStack(NyxItems.fallenStar));
        addJEIInfo(registry, new ItemStack(NyxItems.frezariteBoots));

        // JEI Info - Meteors
        addJEIInfo(registry, new ItemStack(NyxBlocks.meteoriteRockHot));
        addJEIInfo(registry, new ItemStack(NyxBlocks.meteoriteRock));
        addJEIInfo(registry, new ItemStack(NyxBlocks.frezariteRock));
        addJEIInfo(registry, new ItemStack(NyxBlocks.kreknoriteRock));
        addJEIInfo(registry, new ItemStack(NyxItems.tektiteGemCluster));
        addJEIInfo(registry, new ItemStack(NyxBlocks.cyberCrystal));
    }

    private static void addJEIInfo(IModRegistry registry, ItemStack stack) {
        registry.addIngredientInfo(stack, VanillaTypes.ITEM, stack.getTranslationKey() + ".jei_desc");
    }
}
