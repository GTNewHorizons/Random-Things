package lumien.randomthings.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpirit extends EntityMob
{
	ChunkCoordinates spawnPosition;
	int lifetime;

	public EntitySpirit(World par1World, double posX, double posY, double posZ)
	{
		super(par1World);

		this.yOffset = 0.0F;

		this.width = 0.6f;
		this.height = 0.6f;

		this.setSize(0.6F, 0.6F);
		this.setPosition(posX, posY, posZ);

		this.lifetime = 20 * 20;
	}

	public EntitySpirit(World par1World)
	{
		super(par1World);

		this.setSize(0.6F, 0.6F);

		this.lifetime = 20 * 20;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
	}

	@Override
	protected void collideWithNearbyEntities()
	{
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.motionY *= 0.6000000238418579D;
	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F)
		{
			this.spawnPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = this.spawnPosition.posX + 0.5D - this.posX;
		double d1 = this.spawnPosition.posY + 0.1D - this.posY;
		double d2 = this.spawnPosition.posZ + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
		float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
		float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;
	}

	public int getLifeTime()
	{
		return lifetime;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		lifetime--;
		if (lifetime == 0 && !worldObj.isRemote)
		{
			this.setDead();
		}
	}

	@Override
	protected Item getDropItem()
	{
		return ModItems.ingredients;
	}

	@Override
	protected void dropFewItems(boolean par1, int looting)
	{
		int amount = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);
		this.entityDropItem(new ItemStack(ModItems.ingredients, amount, 3), 0.0F);
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float par1)
	{
	}

	/**
	 * Takes in the distance the entity has fallen this tick and whether its on
	 * the ground to update the fall distance and deal fall damage if landing on
	 * the ground. Args: distanceFallenThisTick, onGround
	 */
	@Override
	protected void updateFallState(double par1, boolean par3)
	{
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(float par1, float par2)
	{
		if (this.isInWater())
		{
			this.moveFlying(par1, par2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		}
		else if (this.handleLavaMovement())
		{
			this.moveFlying(par1, par2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}
		else
		{
			float f2 = 0.91F;

			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			float f3 = 0.16277136F / (f2 * f2 * f2);
			this.moveFlying(par1, par2, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;

			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= f2;
			this.motionY *= f2;
			this.motionZ *= f2;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder()
	{
		return false;
	}
}
