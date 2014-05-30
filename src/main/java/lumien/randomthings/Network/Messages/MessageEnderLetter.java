package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Network.PacketHandler;

public class MessageEnderLetter implements IMessage,IMessageHandler<MessageEnderLetter,IMessage>
{
	public enum ACTION
	{
		CHANGED_NAME;
	}

	ACTION action;
	String newReceiver;

	public MessageEnderLetter(ACTION action)
	{
		this.action = action;
	}
	
	public MessageEnderLetter(String newReceiver)
	{
		this.action = ACTION.CHANGED_NAME;
		this.newReceiver = newReceiver;
	}
	
	public MessageEnderLetter()
	{
		
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(action.ordinal());
		switch (action)
		{
			case CHANGED_NAME:
				ByteBufUtils.writeUTF8String(buffer, newReceiver);
				break;
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		action = ACTION.values()[buffer.readInt()];
		
		switch (action)
		{
			case CHANGED_NAME:
				newReceiver = ByteBufUtils.readUTF8String(buffer);
				break;
		}
	}

	@Override
	public IMessage onMessage(MessageEnderLetter message, MessageContext ctx)
	{
		switch (message.action)
		{
			case CHANGED_NAME:
				ItemStack enderLetter = ctx.getServerHandler().playerEntity.getCurrentEquippedItem();
				if (enderLetter!=null && enderLetter.getItem() == ModItems.enderLetter)
				{
					if (enderLetter.stackTagCompound==null)
					{
						enderLetter.stackTagCompound = new NBTTagCompound();
					}
					enderLetter.stackTagCompound.setString("receiver", message.newReceiver);
				}
				break;
		}
		return null;
	}

}
