package lumien.randomthings.Network.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Network.AbstractPacket;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;

public class PacketItemCollector extends AbstractPacket
{
	int posX, posY, posZ;
	int rangeX, rangeY, rangeZ;
	
	public PacketItemCollector()
	{
		
	}

	public PacketItemCollector(int posX, int posY, int posZ, int rangeX, int rangeY, int rangeZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.rangeZ = rangeZ;
	}
	
	public PacketItemCollector(TileEntityAdvancedItemCollector te)
	{
		this.posX = te.xCoord;
		this.posY = te.yCoord;
		this.posZ = te.zCoord;

		this.rangeX = te.rangeX;
		this.rangeY = te.rangeY;
		this.rangeZ = te.rangeZ;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);

		buffer.writeInt(rangeX);
		buffer.writeInt(rangeY);
		buffer.writeInt(rangeZ);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.posX = buffer.readInt();
		this.posY = buffer.readInt();
		this.posZ = buffer.readInt();

		this.rangeX = buffer.readInt();
		this.rangeY = buffer.readInt();
		this.rangeZ = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		int dimensionID = player.dimension;
		World worldObj = player.worldObj;

		if (worldObj.getBlock(posX, posY, posZ) == ModBlocks.advancedItemCollector)
		{
			TileEntityAdvancedItemCollector te = (TileEntityAdvancedItemCollector) worldObj.getTileEntity(posX, posY, posZ);
			if (te !=null)
			{
				te.setRange(rangeX, rangeY, rangeZ);
				te.markDirty();
			}
		}

	}

}
