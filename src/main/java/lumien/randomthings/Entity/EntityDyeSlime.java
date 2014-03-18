package lumien.randomthings.Entity;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

import lumien.randomthings.RandomThings;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntityDyeSlime extends EntityLiving implements IMob, IEntityAdditionalSpawnData
{
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;

	private int slimeJumpDelay, dye;

	public EntityDyeSlime(World par1World)
	{
		super(par1World);
		int i = 1 << this.rand.nextInt(3);
		this.yOffset = 0.0F;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;
		this.setSlimeSize(i);
		dye = rand.nextInt(ItemDye.field_150922_c.length);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 1));
	}

	protected void setSlimeSize(int par1)
	{
		this.dataWatcher.updateObject(16, new Byte((byte) par1));
		this.setSize(0.6F * (float) par1, 0.6F * (float) par1);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) (par1 * par1));
		this.setHealth(this.getMaxHealth());
		this.experienceValue = par1;
	}

	/**
	 * Returns the size of the slime.
	 */
	public int getSlimeSize()
	{
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Size", this.getSlimeSize() - 1);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
	}

	/**
	 * Returns the name of a particle effect that may be randomly created by
	 * EntityDyeSlime.onUpdate()
	 */
	protected String getSlimeParticle()
	{
		return "slime";
	}

	/**
	 * Returns the name of the sound played when the slime jumps.
	 */
	protected String getJumpSound()
	{
		return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
	}

	public void setDye(int dye)
	{
		this.dye = dye;
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		super.dropFewItems(par1, par2);
		if (rand.nextInt(10) == 0)
		{
			this.entityDropItem(new ItemStack(Items.dye, 1, dye), 0);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getSlimeSize() > 0)
		{
			this.isDead = true;
		}

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		boolean flag = this.onGround;
		super.onUpdate();
		int i;

		if (this.onGround && !flag)
		{
			i = this.getSlimeSize();

			for (int j = 0; j < i * 8; ++j)
			{
				float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
				float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
				float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
				float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
				RandomThings.proxy.spawnSlimeParticle(dye, this.posX + (double) f2, this.boundingBox.minY, this.posZ + (double) f3, 0.0D, 0.0D, 0.0D);
			}

			if (this.makesSoundOnLand())
			{
				this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}

			this.squishAmount = -0.5F;
		}
		else if (!this.onGround && flag)
		{
			this.squishAmount = 1.0F;
		}

		this.alterSquishAmount();

		if (this.worldObj.isRemote)
		{
			i = this.getSlimeSize();
			this.setSize(0.6F * (float) i, 0.6F * (float) i);
		}
	}

	protected void updateEntityActionState()
	{
		this.despawnEntity();
		EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);

		if (entityplayer != null)
		{
			this.faceEntity(entityplayer, 10.0F, 20.0F);
		}

		if (this.onGround && this.slimeJumpDelay-- <= 0)
		{
			this.slimeJumpDelay = this.getJumpDelay();

			if (entityplayer != null)
			{
				this.slimeJumpDelay /= 3;
			}

			this.isJumping = true;

			if (this.makesSoundOnJump())
			{
				this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}

			this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
			this.moveForward = (float) (1 * this.getSlimeSize());
		}
		else
		{
			this.isJumping = false;

			if (this.onGround)
			{
				this.moveStrafing = this.moveForward = 0.0F;
			}
		}
	}

	protected void alterSquishAmount()
	{
		this.squishAmount *= 0.6F;
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	protected int getJumpDelay()
	{
		return this.rand.nextInt(20) + 10;
	}

	protected EntityDyeSlime createInstance()
	{
		return new EntityDyeSlime(this.worldObj);
	}

	/**
	 * Will get destroyed next tick.
	 */
	public void setDead()
	{
		int i = this.getSlimeSize();

		if (!this.worldObj.isRemote && i > 1 && this.getHealth() <= 0.0F)
		{
			int j = 2 + this.rand.nextInt(3);

			for (int k = 0; k < j; ++k)
			{
				float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
				float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
				EntityDyeSlime entityDyeSlime = this.createInstance();
				entityDyeSlime.setSlimeSize(i / 2);
				entityDyeSlime.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
				entityDyeSlime.setDye(dye);
				this.worldObj.spawnEntityInWorld(entityDyeSlime);
			}
		}

		super.setDead();
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if (this.canDamagePlayer())
		{
			int i = this.getSlimeSize();

			if (this.canEntityBeSeen(par1EntityPlayer) && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6D * (double) i * 0.6D * (double) i && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttackStrength()))
			{
				this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the
	 * slime's size)
	 */
	protected boolean canDamagePlayer()
	{
		return this.getSlimeSize() > 1;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the
	 * slime.
	 */
	protected int getAttackStrength()
	{
		return this.getSlimeSize();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
	}

	protected Item getDropItem()
	{
		return this.getSlimeSize() == 1 ? Items.slime_ball : Item.getItemById(0);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	public boolean getCanSpawnHere()
	{
		return true;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume()
	{
		return 0.4F * (float) this.getSlimeSize();
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	/**
	 * Returns true if the slime makes a sound when it jumps (based upon the
	 * slime's size)
	 */
	protected boolean makesSoundOnJump()
	{
		return this.getSlimeSize() > 0;
	}

	/**
	 * Returns true if the slime makes a sound when it lands after a jump (based
	 * upon the slime's size)
	 */
	protected boolean makesSoundOnLand()
	{
		return this.getSlimeSize() > 2;
	}

	public int getDye()
	{
		return dye;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		buffer.writeInt(dye);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		dye = additionalData.readInt();
	}
}
