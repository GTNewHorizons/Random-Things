package lumien.randomthings.Client.Particle;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.player.EntityPlayer;

public class ParticleMagneticForce extends EntityPortalFX
{
	EntityPlayer followPlayer;

	Random rng;

	double offsetX, offsetZ;

	double modY;

	public ParticleMagneticForce(EntityPlayer followPlayer, double offsetX, double offsetZ)
	{
		super(followPlayer.worldObj, followPlayer.posX+offsetX, followPlayer.posY, followPlayer.posZ+offsetZ, 0, 0, 0);

		rng = new Random();

		this.offsetX = offsetX;
		this.offsetZ = offsetZ;

		this.followPlayer = followPlayer;

		float f = this.rand.nextFloat() * 0.6F + 0.4F;

		this.particleRed = 1;
		this.particleGreen = 1;
		this.particleBlue = 0;

		modY = -followPlayer.yOffset;

		// this.setRBGColorF(0, 0, 0);

		this.particleMaxAge = 20;
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
		modY += 0.1f;
		this.posX = followPlayer.posX + offsetX;
		this.posY = followPlayer.posY + modY;
		this.posZ = followPlayer.posZ + offsetZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}

		if (particleAge >= 10)
		{
			//float alpha = 1 - (particleAge - 10f) / 10f;
			//this.setAlphaF(alpha);
		}
	}

}
