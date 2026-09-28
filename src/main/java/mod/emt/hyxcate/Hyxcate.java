package mod.emt.hyxcate;

import mod.emt.hyxcate.command.HyxcateCommandForce;
import mod.emt.hyxcate.command.HyxcateCommandMeteor;
import mod.emt.hyxcate.compat.HyxcateCompatHandler;
import mod.emt.hyxcate.compat.datafixes.HyxcateBlockDataFixer;
import mod.emt.hyxcate.compat.datafixes.HyxcateEntityDataFixer;
import mod.emt.hyxcate.compat.datafixes.HyxcateItemDataFixer;
import mod.emt.hyxcate.compat.datafixes.HyxcateMiscDataFixer;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.init.HyxcateRegistry;
import mod.emt.hyxcate.network.HyxcatePacketHandler;
import mod.emt.hyxcate.proxy.CommonProxy;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid = Hyxcate.ID, name = Hyxcate.NAME, version = Hyxcate.VERSION, dependencies = Hyxcate.DEPENDENCIES)
public class Hyxcate {
    public static final String ID = Tags.MOD_ID;
    public static final String NAME = Tags.NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:mixinbooter@[10.2,)" + ";required-after:expandedevents" + ";after:tconstruct" + ";after:conarm";

    public static final String CLIENT_PROXY = "mod.emt.hyxcate.proxy.ClientProxy";
    public static final String COMMON_PROXY = "mod.emt.hyxcate.proxy.CommonProxy";

    @Mod.Instance
    public static Hyxcate instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        HyxcateRegistry.preInit();
        HyxcatePacketHandler.init();
        HyxcateCompatHandler.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        HyxcateRegistry.init();
        HyxcateCompatHandler.init();

        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(ID, 1);
        modFixer.registerFix(FixTypes.BLOCK_ENTITY, new HyxcateBlockDataFixer());
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new HyxcateItemDataFixer());
        modFixer.registerFix(FixTypes.ENTITY, new HyxcateEntityDataFixer());
        MinecraftForge.EVENT_BUS.register(new HyxcateMiscDataFixer());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        HyxcateData.initConfigLists();
        HyxcateCompatHandler.postInit();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new HyxcateCommandForce());
        if (HyxcateConfig.MASTER_SWITCHES.meteorEventsEnabled) event.registerServerCommand(new HyxcateCommandMeteor());
    }
}
