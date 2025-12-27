package de.ellpeck.nyx.compat.toughasnails;

import de.ellpeck.nyx.compat.toughasnails.modifier.TANTemperatureModifier;
import toughasnails.api.temperature.TemperatureHelper;

public class ToughAsNails {
    public static void registerTemperatureModifiers() {
        TemperatureHelper.registerTemperatureModifier(new TANTemperatureModifier("hyxcate"));
    }
}
