package lumien.randomthings.Network.Messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Handler.Notifications.Notification;
import lumien.randomthings.Network.IRTMessage;

public class MessageNotification implements IRTMessage
{
	String title;
	String description;
	int duration;
	ItemStack icon;

	public MessageNotification(String title, String description, int duration, ItemStack icon)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.duration = duration;
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
		buffer.writeInt(duration);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.title = ByteBufUtils.readUTF8String(buffer);
		this.description = ByteBufUtils.readUTF8String(buffer);
		this.icon = ByteBufUtils.readItemStack(buffer);
		this.duration = buffer.readInt();
	}

	@Override
	public void onMessage(MessageContext ctx)
	{
		RandomThings.instance.notificationHandler.addNotification(new Notification(title, description, duration, icon));
	}

}
