package lumien.randomthings.Network.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lumien.randomthings.Items.ItemFilter;
import lumien.randomthings.Network.AbstractPacket;

public class PacketItemFilter extends AbstractPacket
{
	public enum ACTION
	{
		OREDICT, METADATA, LISTTYPE, NBT;
	}

	ACTION action;

	public PacketItemFilter(ACTION action)
	{
		this.action = action;
	}

	public PacketItemFilter()
	{

	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(action.ordinal());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		action = ACTION.values()[buffer.readInt()];
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		String toToggle = "";
		ItemStack filter = player.getCurrentEquippedItem();
		if (filter != null && filter.getItem() instanceof ItemFilter)
		{
			switch (action)
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
	}

}
