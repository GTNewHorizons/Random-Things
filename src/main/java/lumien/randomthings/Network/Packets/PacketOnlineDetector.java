package lumien.randomthings.Network.Packets;

import java.nio.CharBuffer;

import cpw.mods.fml.common.network.ByteBufUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import lumien.randomthings.Network.AbstractPacket;
import lumien.randomthings.TileEntities.TileEntityOnlineDetector;

public class PacketOnlineDetector extends AbstractPacket
{
	String username;
	int posX, posY, posZ, dimensionID;

	public PacketOnlineDetector()
	{

	}

	public PacketOnlineDetector(String username, int posX, int posY, int posZ, int dimensionID)
	{
		this.username = username;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.dimensionID = dimensionID;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, username);
		
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);
		
		buffer.writeInt(dimensionID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		username = ByteBufUtils.readUTF8String(buffer);
		
		posX = buffer.readInt();
		posY = buffer.readInt();
		posZ = buffer.readInt();
		
		dimensionID = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		World worldObj = DimensionManager.getWorld(dimensionID);
		if (worldObj.getTileEntity(posX, posY, posZ) instanceof TileEntityOnlineDetector)
		{
			TileEntityOnlineDetector od = (TileEntityOnlineDetector) worldObj.getTileEntity(posX, posY, posZ);
			od.setUsername(username);
		}
	}

}
