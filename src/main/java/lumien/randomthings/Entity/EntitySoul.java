package lumien.randomthings.Entity;

import java.awt.Color;
import java.util.HashMap;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Items.ItemGinto;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.WorldUtils;
import io.netty.buffer.ByteBuf;

import com.sun.jmx.snmp.InetAddressAcl;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EntitySoul extends Entity implements IEntityAdditionalSpawnData
{
	String playerName;
	int counter = 0;
	
	public boolean render = false;
	
	public int type;

	public EntitySoul(World worldObj)
	{
		super(worldObj);

		this.setSize(0.3F, 0.3F);
		this.renderDistanceWeight = 5;
		this.noClip = true;
		this.playerName = "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float partial)
	{
		return 255;
	}

	public EntitySoul(World worldObj, double posX, double posY, double posZ, String playerName)
	{
		super(worldObj);
		this.setSize(0.3F, 0.3F);
		this.width = 0.3F;
		this.height = 0.3F;
		this.setPosition(posX, posY, posZ);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		this.noClip = true;
		this.playerName = playerName;
	}

	public boolean canBeCollidedWith()
	{
		return RandomThings.proxy.canBeCollidedWith(this);
	}

	public boolean interactFirst(EntityPlayer user)
	{
		ItemStack equipped = user.getCurrentEquippedItem();
		if (equipped != null && (equipped.getItem() instanceof ItemGinto) && equipped.getItemDamage() == 1)
		{
			if (!worldObj.isRemote && MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName) != null)
			{
				worldObj.spawnEntityInWorld(new EntityReviveCircle(worldObj,posX,posY,posZ,this));
				if (!user.capabilities.isCreativeMode)
				{
					equipped.stackSize--;
					user.inventory.markDirty();
					user.inventoryContainer.detectAndSendChanges();
				}
			}
			return true;
		}
		return false;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
	{
		return null;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return null;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		counter++;
		if (!worldObj.isRemote)
		{
			if (MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName) != null)
			{
				if (MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName).getHealth() > 0)
				{
					this.setDead();
				}
			}
		}
		else
		{
			render = WorldUtils.isPlayerOnline(this.playerName);
		}
	}

	@Override
	protected void entityInit()
	{
		type = this.rand.nextInt(2);	
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.playerName = nbt.getString("playerName");
		this.type = nbt.getInteger("type");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setString("playerName", playerName);
		nbt.setInteger("type", type);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, playerName);
		buffer.writeInt(type);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		this.playerName = ByteBufUtils.readUTF8String(additionalData);
		this.type = additionalData.readInt();
	}

}
