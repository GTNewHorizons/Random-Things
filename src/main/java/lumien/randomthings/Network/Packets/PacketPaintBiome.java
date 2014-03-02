package lumien.randomthings.Network.Packets;

import java.awt.Color;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import lumien.randomthings.Items.ItemBiomeSolution;
import lumien.randomthings.Network.AbstractPacket;

public class PacketPaintBiome extends AbstractPacket
{
	int posX,posY, posZ, dimensionID, biomeID;

	public PacketPaintBiome()
	{

	}

	public PacketPaintBiome(int x,int y, int z, int dimensionID,int biomeID)
	{
		posX = x;
		posZ = z;
		posY = y;
		this.dimensionID = dimensionID;
		this.biomeID = biomeID;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(posX);
		buffer.writeInt(posY);
		buffer.writeInt(posZ);
		buffer.writeInt(dimensionID);
		buffer.writeInt(biomeID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		posX = buffer.readInt();
		posY = buffer.readInt();
		posZ = buffer.readInt();
		dimensionID = buffer.readInt();
		biomeID = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClientSide(EntityPlayer player)
	{
		if (player.worldObj.provider.dimensionId == this.dimensionID)
		{
			Chunk c = player.worldObj.getChunkFromBlockCoords(posX, posZ);
			byte[] biomeArray = c.getBiomeArray();
			biomeArray[(posZ & 15) << 4 | (posX & 15)] = (byte) (biomeID & 255);
			c.setBiomeArray(biomeArray);
			player.worldObj.markBlocksDirtyVertical(posX, posZ, 0, player.worldObj.getActualHeight());
			BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
			int colorInt = ItemBiomeSolution.getColorForBiome(biome);
			Random rng = new Random();
			Color color = new Color(colorInt);
			
			for (int i=0;i<64;i++)
			{
				EntityFX smoke = new EntitySmokeFX(player.worldObj,posX+rng.nextFloat(),posY+rng.nextFloat(),posZ+rng.nextFloat(),0,0,0);
				smoke.setRBGColorF(1.0F/255.0F*color.getRed(), 1.0F/255.0F*color.getGreen(), 1.0F/255.0F*color.getBlue());
				
				Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			}
			
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{

	}

}
