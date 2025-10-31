package de.ellpeck.nyx.compat;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.compat.tinkers.ConstructsArmory;
import de.ellpeck.nyx.compat.tinkers.TinkersConstruct;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxCompatHandler {
    public static void preInit() {
        if (Loader.isModLoaded("tconstruct")) {
            TinkersConstruct.registerToolMaterials();

            // Only load Construct's Armory if Tinkers' Construct is also loaded
            if (Loader.isModLoaded("conarm")) {
                ConstructsArmory.registerToolMaterials();
            }
        }
    }

    public static void init() {
        if (Loader.isModLoaded("tconstruct")) {
            TinkersConstruct.registerToolRecipes();
        }
    }

    public static void postInit() {
        if (Loader.isModLoaded("tconstruct")) {
            TinkersConstruct.registerSmelteryRecipes();
        }
    }
}
