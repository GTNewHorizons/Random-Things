package lumien.randomthings.Network.Messages;

import com.google.common.base.Preconditions;

import lumien.randomthings.Library.Interfaces.IItemWithProperties;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageChangeItemProperty implements IMessage, IMessageHandler<MessageChangeItemProperty, IMessage>
{
	String propertyName;

	int propertyType; // 0=Boolean,1=String,2=Int,3=Long
	int itemID;
	int itemDamage;
	int slot;

	boolean newBoolean;
	String newString;
	int newInt;
	long newLong;

	public MessageChangeItemProperty()
	{

	}

	public MessageChangeItemProperty(int propertyType, String propertyName, int itemID, int itemDamage, int slot)
	{
		this.propertyType = propertyType;
		this.itemID = itemID;
		this.itemDamage = itemDamage;
		this.slot = slot;
		this.propertyName = propertyName;
	}

	public MessageChangeItemProperty(int itemID, int itemDamage, int slot, String propertyName, boolean newBoolean)
	{
		this(0, propertyName, itemID, itemDamage, slot);
		this.newBoolean = newBoolean;
	}

	public MessageChangeItemProperty(int itemID, int itemDamage, int slot, String propertyName, String newString)
	{
		this(1, propertyName, itemID, itemDamage, slot);
		this.newString = newString;
	}

	public MessageChangeItemProperty(int itemID, int itemDamage, int slot, String propertyName, int newInt)
	{
		this(2, propertyName, itemID, itemDamage, slot);
		this.newInt = newInt;
	}

	public MessageChangeItemProperty(int itemID, int itemDamage, int slot, String propertyName, long newLong)
	{
		this(3, propertyName, itemID, itemDamage, slot);
		this.newLong = newLong;
	}

	@Override
	public IMessage onMessage(MessageChangeItemProperty message, MessageContext ctx)
	{
		NetHandlerPlayServer netHandler = (NetHandlerPlayServer) ctx.netHandler;
		EntityPlayerMP player = netHandler.playerEntity;

		if (message.slot >= 0 && message.slot < player.inventory.getSizeInventory() && message.propertyName != null)
		{
			ItemStack is = player.inventory.getStackInSlot(message.slot);
			if (is != null && is.getItem() instanceof IItemWithProperties && is.getItem() != null && Item.getItemById(message.itemID) == is.getItem() && message.itemDamage == is.getItemDamage())
			{
				IItemWithProperties properties = (IItemWithProperties) is.getItem();
				if (properties.isValidAttribute(message.propertyName, message.propertyType))
				{
					if (is.stackTagCompound == null)
					{
						is.stackTagCompound = new NBTTagCompound();
					}
					switch (message.propertyType)
					{
						case 0: // Boolean
							is.stackTagCompound.setBoolean(message.propertyName, message.newBoolean);
							break;
						case 1: // String
							is.stackTagCompound.setString(message.propertyName, message.newString);
							break;
						case 2: // Int
							is.stackTagCompound.setInteger(message.propertyName, message.newInt);
							break;
						case 3: // Long
							is.stackTagCompound.setLong(message.propertyName, message.newLong);
							break;
					}
				}
			}
		}

		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		propertyType = buf.readInt();
		itemID = buf.readInt();
		itemDamage = buf.readInt();
		slot = buf.readInt();

		propertyName = ByteBufUtils.readUTF8String(buf);

		switch (propertyType)
		{
			case 0:
				newBoolean = buf.readBoolean();
				break;
			case 1:
				newString = ByteBufUtils.readUTF8String(buf);
				break;
			case 2:
				newInt = buf.readInt();
				break;
			case 3:
				newLong = buf.readLong();
				break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(propertyType);
		buf.writeInt(itemID);
		buf.writeInt(itemDamage);
		buf.writeInt(slot);
		
		ByteBufUtils.writeUTF8String(buf, propertyName);

		switch (propertyType)
		{
			case 0:
				buf.writeBoolean(newBoolean);
				break;
			case 1:
				ByteBufUtils.writeUTF8String(buf, newString);
				break;
			case 2:
				buf.writeInt(newInt);
				break;
			case 3:
				buf.writeLong(newLong);
				break;
		}
	}

}
