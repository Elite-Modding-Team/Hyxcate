package mod.emt.hyxcate.client.particle;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Redstone particles except they glow
@SideOnly(Side.CLIENT)
public class ParticleGlowingRedstone extends ParticleRedstone {
    public ParticleGlowingRedstone(World world, double x, double y, double z, float red, float green, float blue) {
        super(world, x, y, z, 1.0F, red, green, blue);
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }
}
