package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lumien.randomthings.Client.Particle.ParticleWhitestone;


public class MessageWhitestone implements IMessage,IMessageHandler<MessageWhitestone,IMessage>
{
	int playerID;

	public MessageWhitestone()
	{

	}

	public MessageWhitestone(int playerID)
	{
		this.playerID = playerID;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(playerID);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		playerID = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(MessageWhitestone message, MessageContext ctx)
	{
		Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.playerID);
		if (entity != null && entity instanceof EntityPlayer)
		{
			for (int i = 0; i < 10; i++)
			{
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleWhitestone((EntityPlayer) entity, 0, 0, 0, Math.random() * 2 - 1, 0, Math.random() * 2 - 1));
			}
		}
		return null;
	}

}
