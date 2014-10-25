package lumien.randomthings.Client.Particle;

import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.world.World;

public class ParticleBiomeSolution extends EntityEnchantmentTableParticleFX
{

	public ParticleBiomeSolution(World worldObj, double targetX, double targetY, double targetZ, double startX, double startY, double startZ)
	{
		super(worldObj, targetX, targetY, targetZ, startX - targetX, startY - targetY, startZ - targetZ);

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	}

}
