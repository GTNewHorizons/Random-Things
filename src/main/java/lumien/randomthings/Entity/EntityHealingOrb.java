package lumien.randomthings.Entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityHealingOrb extends Entity {

    int energy = 600;
    float healAmount;
    EntityPlayer followPlayer;
    public int xpColor;

    private static final int RANGE = 20;

    public EntityHealingOrb(World par1World, double posX, double posY, double posZ, float healAmount) {
        super(par1World);
        this.setSize(0.5F, 0.5F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(posX, posY, posZ);
        this.rotationYaw = (float) (Math.random() * 360.0D);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;

        this.noClip = true;
        this.healAmount = healAmount;
    }

    public EntityHealingOrb(World par1World) {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        xpColor = 0;
        this.renderDistanceWeight = 5;
        this.noClip = true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!worldObj.isRemote) {
            par1EntityPlayer.heal(healAmount);
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (followPlayer == null || !followPlayer.isEntityAlive()) {
            List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(
                    EntityPlayer.class,
                    AxisAlignedBB.getBoundingBox(
                            this.posX - RANGE,
                            this.posY - RANGE,
                            this.posZ - RANGE,
                            this.posX + RANGE,
                            this.posY + RANGE,
                            this.posZ + RANGE));

            if (!players.isEmpty()) {
                EntityPlayer currentLow = null;
                float currentHealth = 30;
                for (EntityPlayer player : players) {
                    if (player.getHealth() < currentHealth) {
                        currentLow = player;
                        currentHealth = player.getHealth();
                    }
                }

                if (currentLow != null) {
                    this.followPlayer = currentLow;
                }
            }
        }

        if (followPlayer != null) {
            double difX = followPlayer.posX - this.posX;
            double difY = followPlayer.posY - this.posY;
            double difZ = followPlayer.posZ - this.posZ;
            double distance = Math.abs(difX) + Math.abs(difY) + Math.abs(difZ);

            this.motionX += difX / 100d * (distance);
            this.motionY += difY / 100d * (distance);
            this.motionZ += difZ / 100d * (distance);
        }

        this.moveEntity(motionX, motionY, motionZ);
        energy--;
        if (energy == 0) {
            this.setDead();
        }

        if (worldObj.isRemote) {
            xpColor++;
        }
    }

    @Override
    public int getBrightnessForRender(float par1) {
        float f1 = 0.5F;

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f1 * 15.0F * 16.0F);

        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getInteger("energy");
        this.healAmount = nbt.getFloat("healAmount");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("energy", energy);
        nbt.setFloat("healAmount", healAmount);
    }
}
