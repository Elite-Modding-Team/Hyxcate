package mod.emt.hyxcate.compat.simpledifficulty;

import com.charles445.simpledifficulty.api.temperature.TemperatureRegistry;
import mod.emt.hyxcate.compat.simpledifficulty.modifier.SDTemperatureModifier;

public class SimpleDifficulty {
    public static void registerTemperatureModifiers() {
        TemperatureRegistry.registerModifier(new SDTemperatureModifier("hyxcate"));
    }
}
