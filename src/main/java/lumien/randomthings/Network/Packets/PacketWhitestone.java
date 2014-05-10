package lumien.randomthings.Network.Packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lumien.randomthings.Client.Particle.ParticleWhitestone;
import lumien.randomthings.Network.AbstractPacket;

public class PacketWhitestone extends AbstractPacket
{
	int playerID;

	public PacketWhitestone()
	{

	}

	public PacketWhitestone(int playerID)
	{
		this.playerID = playerID;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(playerID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		playerID = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player)
	{
		Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(playerID);
		if (entity != null && entity instanceof EntityPlayer)
		{
			for (int i = 0; i < 10; i++)
			{
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleWhitestone((EntityPlayer) entity, 0, 0, 0, Math.random() * 2 - 1, 0, Math.random() * 2 - 1));
			}
		}

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		// TODO Auto-generated method stub

	}

}
