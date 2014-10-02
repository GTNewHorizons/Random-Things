package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import lumien.randomthings.Blocks.ModBlocks;

import lumien.randomthings.Network.IRTMessage;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;

public class MessageItemCollector implements IRTMessage
{
	int posX, posY, posZ;
	int rangeX, rangeY, rangeZ;
	
	public MessageItemCollector()
	{
		
	}

	public MessageItemCollector(int posX, int posY, int posZ, int rangeX, int rangeY, int rangeZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.rangeZ = rangeZ;
	}
	
	public MessageItemCollector(TileEntityAdvancedItemCollector te)
	{
		this.posX = te.xCoord;
		this.posY = te.yCoord;
		this.posZ = te.zCoord;

		this.rangeX = te.rangeX;
		this.rangeY = te.rangeY;
		this.rangeZ = te.rangeZ;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);

		buffer.writeInt(rangeX);
		buffer.writeInt(rangeY);
		buffer.writeInt(rangeZ);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.posX = buffer.readInt();
		this.posY = buffer.readInt();
		this.posZ = buffer.readInt();

		this.rangeX = buffer.readInt();
		this.rangeY = buffer.readInt();
		this.rangeZ = buffer.readInt();
	}

	@Override
	public void onMessage(MessageContext ctx)
	{
		int dimensionID = ctx.getServerHandler().playerEntity.dimension;
		World worldObj = ctx.getServerHandler().playerEntity.worldObj;

		if (worldObj.getBlock(posX, posY, posZ) == ModBlocks.advancedItemCollector)
		{
			TileEntityAdvancedItemCollector te = (TileEntityAdvancedItemCollector) worldObj.getTileEntity(posX, posY, posZ);
			if (te !=null)
			{
				te.setRange(rangeX, rangeY, rangeZ);
				te.markDirty();
				te.getWorldObj().markBlockForUpdate(posX, posY, posZ);
			}
		}
	}

}
