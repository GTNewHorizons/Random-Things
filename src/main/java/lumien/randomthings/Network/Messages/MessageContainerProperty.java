package lumien.randomthings.Network.Messages;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.Interfaces.IContainerWithProperties;
import lumien.randomthings.Network.IRTMessage;

public class MessageContainerProperty implements IRTMessage
{
	int index;
	int value;
	
	public MessageContainerProperty()
	{
		
	}
	
	public MessageContainerProperty(int index,int value)
	{
		this.index = index;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.index = buf.readInt();
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(index);
		buf.writeInt(value);
	}

	@Override
	public void onMessage(MessageContext context)
	{
		RandomThings.proxy.setContainerProperty(index, value);
	}
}
