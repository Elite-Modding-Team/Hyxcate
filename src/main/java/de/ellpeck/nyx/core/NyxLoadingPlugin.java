package de.ellpeck.nyx.core;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("serial")
@IFMLLoadingPlugin.Name("NyxCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class NyxLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static final boolean isDev = FMLLaunchHandler.isDeobfuscatedEnvironment();
    private static final Map<String, Supplier<Boolean>> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>() {
        {

        }
    });
    private static final Map<String, Supplier<Boolean>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>() {
        {
            put("mixins.nyx.common.json", () -> true);
        }
    });
    private static final Map<String, Supplier<Boolean>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>() {
        {
            put("mixins.nyx.client.json", () -> true);
        }
    });

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = new ArrayList<>();
        if (isClient) configs.addAll(clientsideMixinConfigs.keySet());
        else configs.addAll(serversideMixinConfigs.keySet());
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        Supplier<Boolean> sidedSupplier = isClient ? clientsideMixinConfigs.get(mixinConfig) : null;
        Supplier<Boolean> commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.get() : commonSupplier == null || commonSupplier.get();
    }
}
