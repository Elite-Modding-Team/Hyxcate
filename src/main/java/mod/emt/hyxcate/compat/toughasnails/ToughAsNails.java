package mod.emt.hyxcate.compat.toughasnails;

import mod.emt.hyxcate.compat.toughasnails.modifier.TANTemperatureModifier;
import toughasnails.api.temperature.TemperatureHelper;

public class ToughAsNails {
    public static void registerTemperatureModifiers() {
        TemperatureHelper.registerTemperatureModifier(new TANTemperatureModifier("hyxcate"));
    }
}
