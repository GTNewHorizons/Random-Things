package lumien.randomthings.Client.Particle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.player.EntityPlayer;

public class ParticleWhitestone extends EntityPortalFX
{
	EntityPlayer followPlayer;

	Random rng;

	double modX, modY, modZ;

	public ParticleWhitestone(EntityPlayer followPlayer, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(followPlayer.worldObj, par2, par4, par6, par8, par10, par12);

		rng = new Random();

		this.setAlphaF(0F);

		this.modX = (rng.nextDouble() - 0.5D) * followPlayer.width;
		this.modY = (rng.nextDouble()) * followPlayer.height - (Minecraft.getMinecraft().thePlayer == followPlayer ? 1.5D : 0D);
		this.modZ = (rng.nextDouble() - 0.5D) * followPlayer.width;

		this.followPlayer = followPlayer;

		float f = this.rand.nextFloat() * 0.6F + 0.4F;

		this.particleRed = 1;
		this.particleGreen = 1;
		this.particleBlue = 1;

		// this.setRBGColorF(0, 0, 0);

		this.particleMaxAge = 40;
		this.setParticleTextureIndex((int) (Math.random() * 26.0D + 1.0D + 224.0D));
		// this.particleAlpha = 0;
	}

	@Override
	public int getBrightnessForRender(float par1)
	{
		return 210;
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		float f = (float) this.particleAge / (float) this.particleMaxAge;
		float f1 = f;
		f = -f + f * f * 2.0F;
		f = 1.0F - f;
		this.posX = followPlayer.posX + modX + this.motionX * f;
		this.posY = followPlayer.posY + modY + this.motionY * f + (1.0F - f1);
		this.posZ = followPlayer.posZ + modZ + this.motionZ * f;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}

		this.particleMaxAge--;

		if (this.particleAlpha < 1F)
		{
			this.particleAlpha += Math.random() * 0.1;
		}
	}

}
