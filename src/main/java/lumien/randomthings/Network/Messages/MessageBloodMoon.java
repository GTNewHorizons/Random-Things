package lumien.randomthings.Network.Messages;

import lumien.randomthings.Handler.BloodMoonHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageBloodMoon implements IMessage, IMessageHandler<MessageBloodMoon, IMessage>
{
	int dimensionID;
	boolean bloodmoon;

	@Override
	public IMessage onMessage(MessageBloodMoon message, MessageContext ctx)
	{
		BloodMoonHandler.INSTANCE.setBloodMoon(message.dimensionID,message.bloodmoon,true);
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		dimensionID = buf.readInt();
		bloodmoon = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(dimensionID);
		buf.writeBoolean(bloodmoon);
	}
	
	public MessageBloodMoon setDimensionID(int dimensionID)
	{
		this.dimensionID = dimensionID;
		return this;
	}

	public MessageBloodMoon setBloodmoon(boolean bloodmoon)
	{
		this.bloodmoon = bloodmoon;
		return this;
	}
}
