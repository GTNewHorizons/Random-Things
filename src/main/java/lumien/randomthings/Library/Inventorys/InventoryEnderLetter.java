package lumien.randomthings.Library.Inventorys;

import cpw.mods.fml.common.FMLCommonHandler;
import lumien.randomthings.Items.ItemEnderLetter;
import lumien.randomthings.Library.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryEnderLetter extends InventoryBasic
{
	private EntityPlayer playerEntity;
	private ItemStack originalIS;

	private boolean reading = false;

	public InventoryEnderLetter(EntityPlayer player, ItemStack is)
	{
		super("Ender Letter", false, 9);

		playerEntity = player;
		originalIS = is;

		if (!hasInventory())
		{
			createInventory();
		}
	}

	@Override
	public void markDirty()
	{
		super.markDirty();

		if (!reading)
		{
			saveInventory();
		}

		if (FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if (originalIS.getItemDamage() == 1)
			{
				if (InventoryUtils.isInventoryEmpty(this))
				{
					playerEntity.closeScreen();
					playerEntity.inventory.setInventorySlotContents(playerEntity.inventory.currentItem, null);
					playerEntity.inventory.markDirty();
					playerEntity.inventoryContainer.detectAndSendChanges();
				}
			}
		}
	}

	@Override
	public void openInventory()
	{
		loadInventory();
	}

	@Override
	public void closeInventory()
	{
		saveInventory();
	}

	private boolean hasInventory()
	{
		return originalIS.stackTagCompound.getTag("Inventory") != null;
	}

	private void createInventory()
	{
		writeToNBT();
	}

	public void loadInventory()
	{
		readFromNBT();
	}

	protected void setNBT()
	{
		ItemStack itemStack = playerEntity.getCurrentEquippedItem();
		if (itemStack!=null && itemStack.getItem() instanceof ItemEnderLetter)
		{
			itemStack.setTagCompound(originalIS.getTagCompound());
		}

	}

	public void saveInventory()
	{
		writeToNBT();
		setNBT();
	}

	protected void writeToNBT()
	{
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound slotEntry = new NBTTagCompound();
				slotEntry.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(slotEntry);
				itemList.appendTag(slotEntry);
			}
		}
		// save content in Inventory->Items
		NBTTagCompound inventory = new NBTTagCompound();
		inventory.setTag("Items", itemList);
		originalIS.stackTagCompound.setTag("Inventory", inventory);
	}

	protected void readFromNBT()
	{
		reading = true;

		NBTTagList itemList = (NBTTagList) ((NBTTagCompound) originalIS.stackTagCompound.getTag("Inventory")).getTag("Items");
		for (int i = 0; i < itemList.tagCount(); i++)
		{
			NBTTagCompound slotEntry = itemList.getCompoundTagAt(i);
			int j = slotEntry.getByte("Slot") & 0xff;

			if (j >= 0 && j < getSizeInventory())
			{
				setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
			}
		}
		reading = false;
	}

}
