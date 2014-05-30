package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
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

public class MessageNotification implements IMessage, IMessageHandler<MessageNotification, IMessage>
{
	String title;
	String description;
	ItemStack icon;

	public MessageNotification(String title, String description, ItemStack icon)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
	}

	public MessageNotification()
	{

	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, title);
		ByteBufUtils.writeUTF8String(buffer, description);
		ByteBufUtils.writeItemStack(buffer, icon);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.title = ByteBufUtils.readUTF8String(buffer);
		this.description = ByteBufUtils.readUTF8String(buffer);
		this.icon = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public IMessage onMessage(MessageNotification message, MessageContext ctx)
	{
		RandomThings.instance.notificationHandler.addNotification(new Notification(message.title, message.description, message.icon));
		return null;
	}

}
