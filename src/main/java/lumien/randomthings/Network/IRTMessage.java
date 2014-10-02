package lumien.randomthings.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public interface IRTMessage extends IMessage
{
	public void onMessage(MessageContext context);
}
