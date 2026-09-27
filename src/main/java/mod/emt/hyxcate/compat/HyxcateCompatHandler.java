package mod.emt.hyxcate.compat;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.compat.simpledifficulty.SimpleDifficulty;
import mod.emt.hyxcate.compat.tconstruct.ConstructsArmory;
import mod.emt.hyxcate.compat.tconstruct.TinkersConstruct;
import mod.emt.hyxcate.compat.tconstruct.TinkersConstructClient;
import mod.emt.hyxcate.compat.toughasnails.ToughAsNails;
import mod.emt.hyxcate.config.HyxcateConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

@EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcateCompatHandler {
    public static void preInit() {
        if (Loader.isModLoaded("tconstruct") && HyxcateConfig.MOD_INTEGRATION.tinkersConstructIntegration) {
            TinkersConstruct.registerToolMaterials();
            if (FMLLaunchHandler.side().isClient()) {
                MinecraftForge.EVENT_BUS.register(new TinkersConstructClient());
            }

            // Only load Construct's Armory if Tinkers' Construct is also loaded
            if (Loader.isModLoaded("conarm") && HyxcateConfig.MOD_INTEGRATION.constructsArmoryIntegration) {
                ConstructsArmory.registerToolMaterials();
            }
        }
    }

    public static void init() {
        if (Loader.isModLoaded("simpledifficulty") && HyxcateConfig.MOD_INTEGRATION.SIMPLE_DIFFICULTY.enableSimpleDifficulty) {
            SimpleDifficulty.registerTemperatureModifiers();
        }

        if (Loader.isModLoaded("tconstruct") && HyxcateConfig.MOD_INTEGRATION.tinkersConstructIntegration) {
            TinkersConstruct.registerToolRecipes();
        }
        if (Loader.isModLoaded("toughasnails") && HyxcateConfig.MOD_INTEGRATION.TAN.enableTAN) {
            ToughAsNails.registerTemperatureModifiers();
        }
    }

    public static void postInit() {
        if (Loader.isModLoaded("tconstruct") && HyxcateConfig.MOD_INTEGRATION.tinkersConstructIntegration) {
            TinkersConstruct.registerSmelteryRecipes();
        }
    }
}
