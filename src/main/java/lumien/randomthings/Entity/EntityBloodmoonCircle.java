package lumien.randomthings.Entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import lumien.randomthings.Items.ItemBloodstone;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityBloodmoonCircle extends Entity implements IEntityAdditionalSpawnData
{
	public int age;

	int centerX, centerY, centerZ;

	public EntityBloodmoonCircle(World worldObj)
	{
		super(worldObj);

		this.noClip = true;
		this.renderDistanceWeight = 5;
		this.ignoreFrustumCheck = true;
	}

	public EntityBloodmoonCircle(World worldObj, double posX, double posY, double posZ, int centerX, int centerY, int centerZ)
	{
		super(worldObj);

		this.setPosition(posX, posY, posZ);
		this.noClip = true;
		this.renderDistanceWeight = 5;
		this.ignoreFrustumCheck = true;

		this.centerX = centerX;
		this.centerY = centerY;
		this.centerZ = centerZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return null;
	}

	@Override
	public float getCollisionBorderSize()
	{
		return 0;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
	{
		return null;
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		age = nbt.getInteger("age");

		centerX = nbt.getInteger("centerX");
		centerY = nbt.getInteger("centerY");
		centerZ = nbt.getInteger("centerZ");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("age", age);

		nbt.setInteger("centerX", centerX);
		nbt.setInteger("centerY", centerY);
		nbt.setInteger("centerZ", centerZ);
	}

	@Override
	public void onEntityUpdate()
	{
		age++;

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (!worldObj.isRemote && !ItemBloodstone.ritualPattern.matches(worldObj, centerX, centerY, centerZ))
		{
			this.setDead();
		}
		
		if (!worldObj.isRemote && age == 340)
		{
			this.setDead();
			ServerBloodmoonHandler.INSTANCE.force();
			
			for (int modX=-2;modX<3;modX++)
			{
				for (int modZ=-2;modZ<3;modZ++)
				{
					worldObj.setBlock(centerX+modX, centerY, centerZ+modZ, Blocks.obsidian);
				}
			}
		}

		if (worldObj.isRemote)
		{
			if (age < 100)
			{
				RandomThings.proxy.spawnColoredDust(centerX + 2 + 0.5, centerY + 1, centerZ + 2 + 0.5, 0, 0.1, 0, 1, 1, 1);
				RandomThings.proxy.spawnColoredDust(centerX - 2 + 0.5, centerY + 1, centerZ + 2 + 0.5, 0, 0.1, 0, 1, 1, 1);
				RandomThings.proxy.spawnColoredDust(centerX - 2 + 0.5, centerY + 1, centerZ - 2 + 0.5, 0, 0.1, 0, 1, 1, 1);
				RandomThings.proxy.spawnColoredDust(centerX + 2 + 0.5, centerY + 1, centerZ - 2 + 0.5, 0, 0.1, 0, 1, 1, 1);
			}
			else if (age > 150 && age < 300)
			{
				RandomThings.proxy.spawnColoredDust(centerX + 0.5, centerY + 1, centerZ + 2 + 0.5, 0, 0.1, 0, 1, 0, 0);
				RandomThings.proxy.spawnColoredDust(centerX - 2 + 0.5, centerY + 1, centerZ + 0.5, 0, 0.1, 0, 1, 0, 0);
				RandomThings.proxy.spawnColoredDust(centerX + 0.5, centerY + 1, centerZ - 2 + 0.5, 0, 0.1, 0, 1, 0, 0);
				RandomThings.proxy.spawnColoredDust(centerX + 2 + 0.5, centerY + 1, centerZ + 0.5, 0, 0.1, 0, 1, 0, 0);
			}
		}
	}

	@Override
	public void setDead()
	{
		super.setDead();

		if (age > 300)
		{
			for (int i = 0; i < 1000; i++)
			{
				RandomThings.proxy.spawnColoredDust(centerX + Math.random() * 5 - 2, centerY + 1, centerZ + Math.random() * 5 - 2, 0, 0.1, 0, 1, 0, 0);
			}
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		buffer.writeInt(age);
		buffer.writeInt(centerX);
		buffer.writeInt(centerY);
		buffer.writeInt(centerZ);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		this.age = additionalData.readInt();

		this.centerX = additionalData.readInt();
		this.centerY = additionalData.readInt();
		this.centerZ = additionalData.readInt();
	}
}
