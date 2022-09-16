package lumien.randomthings.Client.Particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;

public class ParticleMagneticForce extends EntityFX {
    EntityPlayer followPlayer;

    Random rng;

    double offsetX, offsetZ;

    double modY;

    public ParticleMagneticForce(EntityPlayer followPlayer, double offsetX, double offsetZ) {
        super(
                followPlayer.worldObj,
                followPlayer.posX + offsetX,
                followPlayer.posY - followPlayer.yOffset,
                followPlayer.posZ + offsetZ,
                0,
                0,
                0);

        rng = new Random();

        this.offsetX = offsetX;
        this.offsetZ = offsetZ;

        this.followPlayer = followPlayer;

        float f = this.rand.nextFloat() * 0.6F + 0.4F;

        this.particleRed = 1;
        this.particleGreen = 1;
        this.particleBlue = 0;

        modY = -followPlayer.yOffset;
        this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;

        this.particleMaxAge = 20;
        this.setParticleTextureIndex((int) (Math.random() * 26.0D + 1.0D + 224.0D));
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 210;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        modY += 0.1;

        this.setPosition(followPlayer.posX + offsetX, followPlayer.posY + modY, followPlayer.posZ + offsetZ);

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        if (particleAge >= 10) {
            float alpha = 1 - (particleAge - 10f) / 10f;
            this.setAlphaF(alpha);
        }
    }
}
