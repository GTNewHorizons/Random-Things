package lumien.randomthings.Network.Messages;

import lumien.randomthings.Client.Particle.ParticleMagneticForce;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MessageMagneticForceParticle implements IMessage, IMessageHandler<MessageMagneticForceParticle, IMessage>
{
	int entityID;
	int dimensionID;

	float f;

	public MessageMagneticForceParticle()
	{

	}

	public MessageMagneticForceParticle(int entityID, int dimensionID, float f)
	{
		this.entityID = entityID;
		this.dimensionID = dimensionID;
		this.f = f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(MessageMagneticForceParticle message, MessageContext ctx)
	{
		EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
		if (thePlayer != null && thePlayer.worldObj != null && thePlayer.worldObj.provider.dimensionId == message.dimensionID)
		{
			Entity e = thePlayer.worldObj.getEntityByID(message.entityID);
			if (e != null && e instanceof EntityPlayer)
			{
				float f = message.f;
				f = (float) (Math.random()*Math.PI);
				double mX = 1 * Math.cos(f);
				double mY = 1 * Math.sin(f);
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleMagneticForce((EntityPlayer) e, mX, mY));

				mX = 1 * Math.cos(f - Math.PI);
				mY = 1 * Math.sin(f - Math.PI);
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleMagneticForce((EntityPlayer) e, mX, mY));

				mX = 1 * Math.cos(f - Math.PI / 2d);
				mY = 1 * Math.sin(f - Math.PI / 2d);
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleMagneticForce((EntityPlayer) e, mX, mY));

				mX = 1 * Math.cos(f + Math.PI / 2d);
				mY = 1 * Math.sin(f + Math.PI / 2d);
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleMagneticForce((EntityPlayer) e, mX, mY));
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityID = buf.readInt();
		dimensionID = buf.readInt();
		f = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityID);
		buf.writeInt(dimensionID);
		buf.writeFloat(f);
	}

}
