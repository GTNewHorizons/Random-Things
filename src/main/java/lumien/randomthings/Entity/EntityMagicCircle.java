package lumien.randomthings.Entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityMagicCircle extends Entity implements IEntityAdditionalSpawnData
{
	double radius;

	double maxRadius;
	double radiusStep;
	double rotation;
	double rotationStep;

	int lifetime;

	ResourceLocation circleTexture;
	String circleTextureName;

	int radiusStatus;

	Entity attachedTo;
	double modY;

	public EntityMagicCircle(World worldObj, double posX, double posY, double posZ, double maxRadius, double radiusStep, double rotationSpeed, int lifetime, String circleTextureName)
	{
		super(worldObj);

		this.setPosition(posX, posY, posZ);
		this.maxRadius = maxRadius;
		this.radiusStep = radiusStep;
		this.rotation = 0;
		this.rotationStep = rotationSpeed;
		this.circleTextureName = circleTextureName;
		this.lifetime = lifetime;
		this.radiusStatus = 0;
	}

	public EntityMagicCircle(World worldObj)
	{
		super(worldObj);

		this.ignoreFrustumCheck = true;
		this.radiusStatus = 0;
	}

	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1;
	}

	public Entity attachedTo()
	{
		return attachedTo;
	}

	public double getModY()
	{
		return modY;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (!worldObj.isRemote && attachedTo != null && !attachedTo.isDead)
		{
			if (this.attachedTo.dimension != this.dimension)
			{
				this.travelToDimension(this.attachedTo.dimension);
			}
			this.setPosition(attachedTo.posX, attachedTo.posY + modY, attachedTo.posZ);
		}

		switch (radiusStatus)
		{
			case 0:
				if (radius < maxRadius)
				{
					radius = Math.min(maxRadius, radius + radiusStep);
				}
				else
				{
					radiusStatus = 1;
				}
				break;
			case 1:
				break;
			case 2:
				if (radius > 0)
				{
					radius = Math.max(0, radius - radiusStep);
				}
				break;
		}

		rotation += rotationStep;
		this.renderDistanceWeight = 2;

		if (this.lifetime <= maxRadius / radiusStep && radiusStatus < 2)
		{
			radiusStatus = 2;
		}

		this.lifetime--;
		if (!worldObj.isRemote)
		{
			if (lifetime <= 0)
			{
				this.setDead();
			}
		}
	}

	@Override
	protected void entityInit()
	{

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		radius = nbt.getDouble("radius");
		maxRadius = nbt.getDouble("maxRadius");
		radiusStep = nbt.getDouble("radiusStep");
		rotation = nbt.getDouble("rotation");
		rotationStep = nbt.getDouble("rotationSpeed");
		circleTextureName = nbt.getString("circleTextureName");
		lifetime = nbt.getInteger("lifetime");
		radiusStatus = nbt.getInteger("radiusStatus");
		attachedTo = worldObj.getEntityByID(nbt.getInteger("attachedTo"));
		modY = nbt.getDouble("modY");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setDouble("radius", radius);
		nbt.setDouble("maxRadius", maxRadius);
		nbt.setDouble("radiusStep", radiusStep);
		nbt.setDouble("rotation", rotation);
		nbt.setDouble("rotationSpeed", rotationStep);
		nbt.setString("circleTextureName", circleTextureName);
		nbt.setInteger("lifetime", lifetime);
		nbt.setInteger("radiusStatus", radiusStatus);
		if (attachedTo != null)
		{
			nbt.setInteger("attachedTo", attachedTo.getEntityId());
		}
		nbt.setDouble("modY", modY);
	}

	public ResourceLocation getCircleTexture()
	{
		return circleTexture;
	}

	public void setCircleTexture(ResourceLocation circleTexture)
	{
		this.circleTexture = circleTexture;
	}

	public double getRadius()
	{
		return radius;
	}

	public double getRotation()
	{
		return rotation;
	}

	public double getRadiusStep()
	{
		return radiusStep;
	}

	public void attachTo(Entity entity)
	{
		this.attachedTo = entity;
	}

	public void setModY(double modY)
	{
		this.modY = modY;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeEntityToNBT(nbt);
		ByteBufUtils.writeTag(buffer, nbt);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		this.readEntityFromNBT(ByteBufUtils.readTag(additionalData));

		this.circleTexture = new ResourceLocation(this.circleTextureName);
	}

	public double getRotationStep()
	{
		return rotationStep;
	}

	public double getMaxRadius()
	{
		return maxRadius;
	}

	public int getRadiusStatus()
	{
		return radiusStatus;
	}
}
