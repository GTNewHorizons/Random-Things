package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lumien.randomthings.Items.ItemFilter;


public class MessageItemFilter implements IMessage,IMessageHandler<MessageItemFilter,IMessage>
{
	public enum ACTION
	{
		OREDICT, METADATA, LISTTYPE, NBT;
	}

	ACTION action;

	public MessageItemFilter(ACTION action)
	{
		this.action = action;
	}

	public MessageItemFilter()
	{

	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(action.ordinal());
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		action = ACTION.values()[buffer.readInt()];
	}

	@Override
	public IMessage onMessage(MessageItemFilter message, MessageContext ctx)
	{
		String toToggle = "";
		ItemStack filter =ctx.getServerHandler().playerEntity.getCurrentEquippedItem();
		if (filter != null && filter.getItem() instanceof ItemFilter)
		{
			switch (message.action)
			{
				case OREDICT:
					toToggle = "oreDict";
					filter.stackTagCompound.setBoolean(toToggle, !filter.stackTagCompound.getBoolean(toToggle));
					break;
				case LISTTYPE:
					int oldType = filter.stackTagCompound.getInteger("listType");
					filter.stackTagCompound.setInteger("listType", oldType == 0 ? 1 : 0);
					break;
				case METADATA:
					break;
				case NBT:
					break;
				default:
					break;
			}
		}
		return null;
	}

}
