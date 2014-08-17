package lumien.randomthings.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAdvancedFluidDisplay extends TileEntity
{
	String[] fluids;
	boolean[] flowing;

	public TileEntityAdvancedFluidDisplay()
	{
		fluids = new String[6];
		flowing = new boolean[6];
	}

	public void setFluid(int side, String fluid)
	{
		fluids[side] = fluid;
	}

	public String getFluid(int side)
	{
		return fluids[side];
	}

	public boolean isFlowing(int side)
	{
		return flowing[side];
	}

	public void setFlowing(int side, boolean isFlowing)
	{
		flowing[side] = isFlowing;
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

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		for (int i = 0; i < fluids.length; i++)
		{
			if (fluids[i] != null)
			{
				nbt.setString("fluid" + i, fluids[i]);
			}
		}

		for (int i = 0; i < flowing.length; i++)
		{
			nbt.setBoolean("flowing" + i, flowing[i]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		for (int i = 0; i < fluids.length; i++)
		{
			if (nbt.hasKey("fluid" + i))
			{
				fluids[i] = nbt.getString("fluid" + i);
			}
		}
		
		for (int i=0;i<flowing.length;i++)
		{
			flowing[i] = nbt.getBoolean("flowing"+i);
		}
	}
}
