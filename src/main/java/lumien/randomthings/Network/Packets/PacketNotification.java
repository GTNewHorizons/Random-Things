package lumien.randomthings.Network.Packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.Notifications.Notification;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.ClientUtil;
import lumien.randomthings.Network.AbstractPacket;

public class PacketNotification extends AbstractPacket
{
	String title;
	String description;
	ItemStack icon;

	public PacketNotification(String title,String description,ItemStack icon)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
	}
	
	public PacketNotification()
	{
		
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, title);
		ByteBufUtils.writeUTF8String(buffer, description);
		ByteBufUtils.writeItemStack(buffer, icon);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.title = ByteBufUtils.readUTF8String(buffer);
		this.description = ByteBufUtils.readUTF8String(buffer);
		this.icon = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		RandomThings.instance.notificationHandler.addNotification(new Notification(title,description,icon));
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		
	}

}
