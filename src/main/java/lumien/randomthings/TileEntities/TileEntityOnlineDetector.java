package lumien.randomthings.TileEntities;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Library.WorldUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOnlineDetector extends TileEntity
{
	String username;
	boolean online;

	int tickRate = 20;
	int tickCounter = 0;

	public TileEntityOnlineDetector()
	{
		username = "";

		online = false;
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			tickCounter++;
			if (tickCounter >= tickRate)
			{
				tickCounter = 0;
				
				boolean playerOnline = WorldUtils.isPlayerOnline(username);
				
				if (online && !playerOnline)
				{
					this.online = false;
					worldObj.notifyBlockChange(xCoord, yCoord, zCoord, ModBlocks.onlineDetector);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					this.markDirty();
				}
				else if (!online && playerOnline)
				{
					this.online = true;
					worldObj.notifyBlockChange(xCoord, yCoord, zCoord, ModBlocks.onlineDetector);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					this.markDirty();
				}
			}
		}
	}

	public boolean isActive()
	{
		return online;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setString("username", username);
		nbt.setBoolean("online", online);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		username = nbt.getString("username");
		online = nbt.getBoolean("online");
	}

	public void setUsername(String username)
	{
		this.username = username;
		this.markDirty();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}

	public String getPlayerName()
	{
		return username;
	}
}
