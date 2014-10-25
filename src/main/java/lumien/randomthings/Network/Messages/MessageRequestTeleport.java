package lumien.randomthings.Network.Messages;

import net.minecraft.entity.player.EntityPlayerMP;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Handler.MagneticForceHandler;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.WorldUtils;
import lumien.randomthings.Network.IRTMessage;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageAnswerTeleport.STATUS;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageRequestTeleport implements IRTMessage
{
	String username;

	@Override
	public void onMessage(MessageContext ctx)
	{
		MessageAnswerTeleport answer = new MessageAnswerTeleport();
		EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;

		if (!ConfigItems.magneticForce)
		{
			answer.setStatus(STATUS.NO_RIGHT);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
		}

		if (playerEntity == null || username.trim().equals(""))
		{
			answer.setStatus(STATUS.INVALID_USERNAME);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
		}
		else if (playerEntity.getCurrentEquippedItem() == null || playerEntity.getCurrentEquippedItem().getItem() != ModItems.magneticForce)
		{
			answer.setStatus(STATUS.NO_RIGHT);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
		}
		else if (!WorldUtils.isPlayerOnline(username))
		{
			answer.setStatus(STATUS.NOT_ONLINE);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
		}
		else if (username.equals(playerEntity.getCommandSenderName()) && !playerEntity.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME))
		{
			answer.setStatus(STATUS.SAME_PLAYER);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
		}
		else
		{
			answer.setStatus(STATUS.OKAY);
			PacketHandler.INSTANCE.sendTo(answer, playerEntity);
			if (!playerEntity.capabilities.isCreativeMode && !playerEntity.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME))
			{
				playerEntity.inventory.consumeInventoryItem(ModItems.magneticForce);
				playerEntity.inventory.markDirty();
			}

			MagneticForceHandler.INSTANCE.addEvent(playerEntity.getCommandSenderName(), username);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.username = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, username);
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

}
