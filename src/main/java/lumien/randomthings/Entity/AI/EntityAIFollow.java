package lumien.randomthings.Entity.AI;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollow extends EntityAIBase
{
	private EntityLiving thePet;
	private EntityLivingBase theOwner;
	World theWorld;
	private double field_75336_f;
	private PathNavigate petPathfinder;
	private int field_75343_h;
	float maxDist;
	float minDist;
	private boolean field_75344_i;
	private static final String __OBFID = "CL_00001585";

	public EntityAIFollow(EntityLiving par1EntityTameable, EntityLivingBase owner, double par2, float par4, float par5)
	{
		this.thePet = par1EntityTameable;
		this.theWorld = par1EntityTameable.worldObj;
		this.field_75336_f = par2;
		this.petPathfinder = par1EntityTameable.getNavigator();
		this.minDist = par4;
		this.maxDist = par5;
		this.theOwner = owner;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if (theOwner == null)
		{
			return false;
		}
		else if (this.thePet.getDistanceSqToEntity(theOwner) < (double) (this.minDist * this.minDist))
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double) (this.maxDist * this.maxDist);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.field_75343_h = 0;
		this.field_75344_i = this.thePet.getNavigator().getAvoidsWater();
		this.thePet.getNavigator().setAvoidsWater(false);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.theOwner = null;
		this.petPathfinder.clearPathEntity();
		this.thePet.getNavigator().setAvoidsWater(this.field_75344_i);
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float) this.thePet.getVerticalFaceSpeed());

		if (--this.field_75343_h <= 0)
		{
			this.field_75343_h = 10;

			if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f))
			{
				if (!this.thePet.getLeashed())
				{
					if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
					{
						int i = MathHelper.floor_double(this.theOwner.posX) - 2;
						int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
						int k = MathHelper.floor_double(this.theOwner.boundingBox.minY);

						for (int l = 0; l <= 4; ++l)
						{
							for (int i1 = 0; i1 <= 4; ++i1)
							{
								if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, i + l, k - 1, j + i1) && !this.theWorld.getBlock(i + l, k, j + i1).isNormalCube() && !this.theWorld.getBlock(i + l, k + 1, j + i1).isNormalCube())
								{
									this.thePet.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
									this.petPathfinder.clearPathEntity();
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}