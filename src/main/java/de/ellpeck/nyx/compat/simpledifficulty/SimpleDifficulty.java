package de.ellpeck.nyx.compat.simpledifficulty;

import com.charles445.simpledifficulty.api.temperature.TemperatureRegistry;
import de.ellpeck.nyx.compat.simpledifficulty.modifier.SDTemperatureModifier;

public class SimpleDifficulty {
    public static void registerTemperatureModifiers() {
        TemperatureRegistry.registerModifier(new SDTemperatureModifier("hyxcate"));
    }
}
