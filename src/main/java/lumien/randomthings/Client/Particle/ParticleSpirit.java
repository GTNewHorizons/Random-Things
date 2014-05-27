package lumien.randomthings.Client.Particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

public class ParticleSpirit extends EntityPortalFX
{

	public ParticleSpirit(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
		super(par1World,par2,par4,par6,par8,par10,par12);
		
		float t = 1F/255F;
		this.setRBGColorF(t*152F, t*245F, t*255F);
    }
	
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float)this.particleAge / (float)this.particleMaxAge;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
    }
}
