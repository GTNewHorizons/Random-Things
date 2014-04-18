package lumien.randomthings.Network.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lumien.randomthings.Network.AbstractPacket;
import lumien.randomthings.TileEntities.TileEntityFluidRouter;

public class PacketLiquidRouter extends AbstractPacket
{
	int action;
	int posX,posY,posZ;
	
	public PacketLiquidRouter()
	{
		
	}
	
	public PacketLiquidRouter(int posX,int posY,int posZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
		this.action=0;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(action);
		
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		action = buffer.readInt();
		
		posX = buffer.readInt();
		posY = buffer.readInt();
		posZ = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		World worldObj = player.worldObj;
		TileEntity te = worldObj.getTileEntity(posX, posY, posZ);
		if (te!=null && te instanceof TileEntityFluidRouter)
		{
			TileEntityFluidRouter lr = (TileEntityFluidRouter) te;
		}
		
	}

}
