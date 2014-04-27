package lumien.randomthings.Client.Particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.World;

public class ParticleSquare extends EntityReddustFX
{

	public ParticleSquare(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6, 0, 0.3F, 0);

		this.particleTextureIndexX = 41;
		this.particleTextureIndexY = 8;
		this.particleMaxAge=60;
		this.setRBGColorF(0, 0, 0);
	}
	
	public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;
    }
}
