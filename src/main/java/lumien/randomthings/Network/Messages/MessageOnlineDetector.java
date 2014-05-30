package lumien.randomthings.Network.Messages;

import java.nio.CharBuffer;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import lumien.randomthings.TileEntities.TileEntityOnlineDetector;

public class MessageOnlineDetector implements IMessage,IMessageHandler<MessageOnlineDetector,IMessage>
{
	String username;
	int posX, posY, posZ, dimensionID;

	public MessageOnlineDetector()
	{

	}

	public MessageOnlineDetector(String username, int posX, int posY, int posZ, int dimensionID)
	{
		this.username = username;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.dimensionID = dimensionID;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, username);
		
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);
		
		buffer.writeInt(dimensionID);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		username = ByteBufUtils.readUTF8String(buffer);
		
		posX = buffer.readInt();
		posY = buffer.readInt();
		posZ = buffer.readInt();
		
		dimensionID = buffer.readInt();
	}

	@Override
	public IMessage onMessage(MessageOnlineDetector message, MessageContext ctx)
	{
		World worldObj = DimensionManager.getWorld(message.dimensionID);
		if (worldObj.getTileEntity(message.posX, message.posY, message.posZ) instanceof TileEntityOnlineDetector)
		{
			TileEntityOnlineDetector od = (TileEntityOnlineDetector) worldObj.getTileEntity(message.posX, message.posY, message.posZ);
			od.setUsername(message.username);
		}
		return null;
	}

}
