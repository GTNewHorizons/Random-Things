package lumien.randomthings.Client.Particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Entity.EntityWhitestone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleWhitestone extends EntityFX
{
	int type;
	double radius;
	double t;
	double radiusStep;
	final double step = Math.PI * 2D / 360D * 8;
	boolean starting;
	EntityWhitestone entityWhitestone;

	public ParticleWhitestone(World par1World, EntityWhitestone entityWhitestone, int type, double t)
	{
		super(par1World, entityWhitestone.posX, entityWhitestone.posY, entityWhitestone.posZ);

		this.entityWhitestone = entityWhitestone;
		this.setParticleTextureIndex((int) (Math.random() * 26.0D + 1.0D + 224.0D));
		this.particleScale = 0.4F;
		this.type = type;
		this.radius = 0.5D;
		this.t = t;
		this.radiusStep = 0.5D / 380D;
		starting=true;
		this.particleAlpha = 0;
		
		this.onUpdate();
	}
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
		return 255;
    }

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		
		switch (type)
		{
			case 0:
				if (starting)
				{
					this.particleAlpha+=0.01;
					if (this.particleAlpha>=1)
					{
						starting=false;
					}
				}
				t += step;
				radius -= radiusStep;

				this.posY = entityWhitestone.posY + 0.2;
				this.posX = radius * Math.cos(t) + entityWhitestone.posX;
				this.posZ = radius * Math.sin(t) + entityWhitestone.posZ;

				if (entityWhitestone.isDead || entityWhitestone.getEntityItem().getItemDamage() == 1)
				{
					this.particleAlpha -= 0.04F;
					if (this.particleAlpha <= 0)
					{
						this.setDead();
					}
				}
				break;
		}
	}

}
