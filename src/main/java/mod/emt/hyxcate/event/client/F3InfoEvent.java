package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class F3InfoEvent {
    // TODO: Make these be affected by translations
    // Also event names should have their actual name (e.g. Blood Moon instead of blood_moon)
    @SubscribeEvent
    public void onDebug(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.gameSettings.showDebugInfo) return;
        event.getLeft().add("");
        CapabilityCelestialEvent world = CapabilityCelestialEvent.get(mc.world);
        String pre = TextFormatting.GREEN + "[" + Hyxcate.NAME + "]" + TextFormatting.RESET;
        String nameL = "None";
        String nameS = "None";
        if (world.currentLunarEvent != null) {
            nameL = world.currentLunarEvent.name;
        }
        if (world.currentSolarEvent != null) {
            nameS = world.currentSolarEvent.name;
        }
        event.getLeft().add(pre + " Current Lunar Event: " + nameL);
        event.getLeft().add(pre + " Current Solar Event: " + nameS);
    }
}
