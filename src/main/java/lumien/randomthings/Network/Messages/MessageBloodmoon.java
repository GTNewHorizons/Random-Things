package lumien.randomthings.Network.Messages;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;
import lumien.randomthings.Network.IRTMessage;

public class MessageBloodmoon implements IRTMessage
{
	boolean bloodMoon;

	public MessageBloodmoon()
	{

	}

	public MessageBloodmoon(boolean bloodMoon)
	{
		this.bloodMoon = bloodMoon;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.bloodMoon = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(bloodMoon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onMessage(MessageContext context)
	{
		ClientBloodmoonHandler.INSTANCE.setBloodmoon(this.bloodMoon);
	}
}
