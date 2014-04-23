package lumien.randomthings.Network.Packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Network.AbstractPacket;

public class PacketEnderLetter extends AbstractPacket
{
	public enum ACTION
	{
		CHANGED_NAME;
	}

	ACTION action;
	String newReceiver;

	public PacketEnderLetter(ACTION action)
	{
		this.action = action;
	}
	
	public PacketEnderLetter(String newReceiver)
	{
		this.action = ACTION.CHANGED_NAME;
		this.newReceiver = newReceiver;
	}
	
	public PacketEnderLetter()
	{
		
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
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
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
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
	public void handleClientSide(EntityPlayer player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		switch (action)
		{
			case CHANGED_NAME:
				ItemStack enderLetter = player.getCurrentEquippedItem();
				if (enderLetter!=null && enderLetter.getItem() == ModItems.enderLetter)
				{
					if (enderLetter.stackTagCompound==null)
					{
						enderLetter.stackTagCompound = new NBTTagCompound();
					}
					enderLetter.stackTagCompound.setString("receiver", newReceiver);
				}
				break;
		}
	}

}
