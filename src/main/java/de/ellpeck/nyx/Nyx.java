package de.ellpeck.nyx;

import de.ellpeck.nyx.command.NyxCommandForce;
import de.ellpeck.nyx.command.NyxCommandMeteor;
import de.ellpeck.nyx.compat.NyxCompatHandler;
import de.ellpeck.nyx.compat.datafixes.NyxBlockDataFixer;
import de.ellpeck.nyx.compat.datafixes.NyxItemDataFixer;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxRegistry;
import de.ellpeck.nyx.network.NyxPacketHandler;
import de.ellpeck.nyx.proxy.NyxCommonProxy;
import mod.emt.nyx.Tags;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

@Mod(modid = Nyx.ID, name = Nyx.NAME, version = Nyx.VERSION, guiFactory = "de.ellpeck.nyx.util.NyxGuiFactory", dependencies = "after:tconstruct;after:conarm")
public class Nyx {

    public static final String ID = Tags.MOD_ID;
    public static final String NAME = Tags.NAME;
    public static final String VERSION = Tags.VERSION;

    @Mod.Instance
    public static Nyx instance;
    @SidedProxy(clientSide = "de.ellpeck.nyx.proxy.NyxClientProxy", serverSide = "de.ellpeck.nyx.proxy.NyxCommonProxy")
    public static NyxCommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NyxConfig.init(new File(event.getModConfigurationDirectory(), NAME + ".cfg"));
        NyxRegistry.preInit();
        NyxPacketHandler.init();
        NyxCompatHandler.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NyxRegistry.init();
        NyxCompatHandler.init();

        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(ID, 1);
        modFixer.registerFix(FixTypes.BLOCK_ENTITY, new NyxBlockDataFixer());
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new NyxItemDataFixer());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        NyxCompatHandler.postInit();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new NyxCommandForce());
        if (NyxConfig.meteors) event.registerServerCommand(new NyxCommandMeteor());
    }
}
