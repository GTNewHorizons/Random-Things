package lumien.randomthings.Items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.Library.InventoryUtils;
import lumien.randomthings.Library.Interfaces.IItemWithProperties;
import lumien.randomthings.Library.Inventorys.InventoryEnderLetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemEnderLetter extends ItemBase implements IItemWithProperties
{

	public ItemEnderLetter()
	{
		super("enderLetter");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return par1ItemStack.getItemDamage() == 1;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.stackTagCompound != null)
		{
			String receiver = par1ItemStack.stackTagCompound.getString("receiver");
			String sender = par1ItemStack.stackTagCompound.getString("sender");
			if (receiver.equals(""))
			{
				par3List.add("No Receiver set");
			}
			else
			{
				par3List.add("Receiver: " + receiver);
			}

			if (!sender.equals(""))
			{
				par3List.add("Sender: " + sender);
			}
		}
		else
		{
			par3List.add("No Receiver set");
		}
	}

	public static IInventory getLetterInventory(EntityPlayer player)
	{
		ItemStack enderLetter;
		IInventory letterInventory = null;
		enderLetter = player.getCurrentEquippedItem();

		if (enderLetter != null && enderLetter.getItem() instanceof ItemEnderLetter)
		{
			letterInventory = new InventoryEnderLetter(player, enderLetter);
		}

		return letterInventory;
	}

	public static IInventory getLetterInventory(EntityPlayer player, ItemStack letter)
	{
		ItemStack enderLetter;
		IInventory letterInventory = null;
		enderLetter = letter;

		if (enderLetter != null && enderLetter.getItem() instanceof ItemEnderLetter)
		{
			letterInventory = new InventoryEnderLetter(player, enderLetter);
		}

		return letterInventory;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer playerEntity)
	{
		if (par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		}

		if (playerEntity.isSneaking() && par1ItemStack.getItemDamage() == 0)
		{
			if (!worldObj.isRemote)
			{
				sendLetter(par1ItemStack, playerEntity);
			}
		}
		else
		{
			if (!worldObj.isRemote)
			{
				playerEntity.openGui(RandomThings.instance, GuiIds.ENDER_LETTER, worldObj, (int) playerEntity.posX, (int) playerEntity.posY, (int) playerEntity.posZ);
			}
		}

		return par1ItemStack;
	}

	private void sendLetter(ItemStack letter, EntityPlayer sender)
	{
		String receiver = letter.stackTagCompound.getString("receiver");
		if (receiver.trim().equals(""))
		{
			ChatComponentTranslation invalidReceiverMessage = new ChatComponentTranslation("text.enderLetter.invalidreceiver");
			invalidReceiverMessage.getChatStyle().setColor(EnumChatFormatting.RED);
			sender.addChatMessage(invalidReceiverMessage);
			return;
		}
		else
		{
			IInventory inventory = ItemEnderLetter.getLetterInventory(sender);
			inventory.openInventory();
			if (InventoryUtils.isInventoryEmpty(inventory))
			{
				ChatComponentTranslation emptyLetterMessage = new ChatComponentTranslation("text.enderLetter.empty");
				emptyLetterMessage.getChatStyle().setColor(EnumChatFormatting.RED);
				sender.addChatMessage(emptyLetterMessage);
				return;
			}
			letter.stackTagCompound.setString("sender", sender.getCommandSenderName());

			sender.worldObj.playSoundAtEntity(sender, "mob.endermen.portal", 0.1F, 2F);
			sender.inventory.setInventorySlotContents(sender.inventory.currentItem, null);
			sender.inventory.markDirty();
			sender.inventoryContainer.detectAndSendChanges();

			ItemStack receivedLetter = letter.copy();
			receivedLetter.setItemDamage(1);

			RandomThings.instance.letterHandler.addLetter(receivedLetter);
		}
	}

	@Override
	public boolean isValidAttribute(String attributeName, int attributeType)
	{
		return attributeName.equals("receiver") && attributeType == 1;
	}
}
