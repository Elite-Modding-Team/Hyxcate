package mod.emt.hyxcate.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HyxcateParticleHandler {
    public static void spawnLaserParticles(World world, double xCoord, double yCoord, double zCoord, float r, float g, float b) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGlowingRedstone(world, xCoord, yCoord, zCoord, r, g, b));
    }
}
