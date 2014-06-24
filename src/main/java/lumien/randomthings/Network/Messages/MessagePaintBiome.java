package lumien.randomthings.Network.Messages;

import java.awt.Color;
import java.util.Random;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import lumien.randomthings.Items.ItemBiomeCapsule;

public class MessagePaintBiome implements IMessage, IMessageHandler<MessagePaintBiome, IMessage>
{
	int posX, posY, posZ, dimensionID, biomeID;

	public MessagePaintBiome()
	{

	}

	public MessagePaintBiome(int x, int y, int z, int dimensionID, int biomeID)
	{
		posX = x;
		posZ = z;
		posY = y;
		this.dimensionID = dimensionID;
		this.biomeID = biomeID;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);
		buffer.writeInt(dimensionID);
		buffer.writeInt(biomeID);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		posX = buffer.readInt();
		posY = buffer.readInt();
		posZ = buffer.readInt();
		dimensionID = buffer.readInt();
		biomeID = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(MessagePaintBiome message, MessageContext ctx)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player!=null && player.worldObj.provider.dimensionId == message.dimensionID)
		{
			Chunk c = player.worldObj.getChunkFromBlockCoords(message.posX, message.posZ);
			byte[] biomeArray = c.getBiomeArray();
			biomeArray[(message.posZ & 15) << 4 | (message.posX & 15)] = (byte) (message.biomeID & 255);
			c.setBiomeArray(biomeArray);
			Minecraft.getMinecraft().thePlayer.worldObj.markBlocksDirtyVertical(message.posX, message.posZ, 0, player.worldObj.getActualHeight());
			BiomeGenBase biome = BiomeGenBase.getBiome(message.biomeID);
			int colorInt = ItemBiomeCapsule.getColorForBiome(biome);
			Random rng = new Random();
			Color color = new Color(colorInt);

			for (int i = 0; i < 64; i++)
			{
				EntityFX smoke = new EntitySmokeFX(player.worldObj, message.posX + rng.nextFloat(), message.posY + rng.nextFloat(), message.posZ + rng.nextFloat(), 0, 0, 0);
				smoke.setRBGColorF(1.0F / 255.0F * color.getRed(), 1.0F / 255.0F * color.getGreen(), 1.0F / 255.0F * color.getBlue());

				Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			}

		}
		return null;
	}

}
