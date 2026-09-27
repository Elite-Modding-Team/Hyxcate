package mod.emt.hyxcate.core;

import com.google.common.collect.ImmutableMap;
import zone.rong.mixinbooter.Context;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class HyxcateMixinLoader implements ILateMixinLoader {

    private static final Map<String, Predicate<Context>> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>() {
        {
        }
    });

    private static final Map<String, Predicate<Context>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>() {
        {
            put("mixins.hyxcate.compat.b3m.client.json", c -> c.isModPresent("B3M"));
            put("mixins.hyxcate.compat.urkazmoontools.client.json", c -> c.isModPresent("urkazmoontools"));
        }
    });

    private static final Map<String, Predicate<Context>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>() {
        {
            put("mixins.hyxcate.compat.peacefulsurface.common.json", c -> c.isModPresent("peacefulsurface"));
            put("mixins.hyxcate.compat.urkazmoontools.common.json", c -> c.isModPresent("urkazmoontools"));
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = new ArrayList<>();
        if (HyxcateLoadingPlugin.isClient) configs.addAll(clientsideMixinConfigs.keySet());
        else configs.addAll(serversideMixinConfigs.keySet());
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(Context context) {
        String mixinConfig = context.mixinConfig();
        Predicate<Context> sidedPredicate = HyxcateLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : serversideMixinConfigs.get(mixinConfig);
        Predicate<Context> commonPredicate = commonMixinConfigs.get(mixinConfig);
        return sidedPredicate != null ? sidedPredicate.test(context) : commonPredicate == null || commonPredicate.test(context);
    }
}
