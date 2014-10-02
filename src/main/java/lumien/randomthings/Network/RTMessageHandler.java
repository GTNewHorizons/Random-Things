package lumien.randomthings.Network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class RTMessageHandler implements IMessageHandler<IRTMessage, IRTMessage>
{
	@Override
	public IRTMessage onMessage(IRTMessage message, MessageContext ctx)
	{
		message.onMessage(ctx);
		return null;
	}
}
