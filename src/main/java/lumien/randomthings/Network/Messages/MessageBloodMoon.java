package lumien.randomthings.Network.Messages;

import lumien.randomthings.Handler.BloodMoonHandler;
import lumien.randomthings.Network.IRTMessage;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageBloodMoon implements IRTMessage
{
	int dimensionID;
	boolean bloodmoon;

	@Override
	public void onMessage(MessageContext ctx)
	{
		BloodMoonHandler.INSTANCE.setBloodMoon(dimensionID,bloodmoon,true);
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
