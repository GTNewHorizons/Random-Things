package lumien.randomthings.Library.Inventorys;

import lumien.randomthings.Items.ItemDropFilter;
import lumien.randomthings.Items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryDropFilter extends InventoryBasic
{
	ItemStack dropFilter;
	EntityPlayer player;

	private boolean reading = false;

	public InventoryDropFilter(EntityPlayer player, ItemStack dropFilter)
	{
		super("Drop Filter", false, 1);

		this.player = player;
		this.dropFilter = dropFilter;
		
		if (!hasInventory())
		{
			createInventory();
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
		if (dropFilter.stackTagCompound==null)
		{
			return false;
		}
		return dropFilter.stackTagCompound.getTag("Inventory") != null;
	}

	private void createInventory()
	{
		writeToNBT();
	}

	public void loadInventory()
	{
		readFromNBT();
	}

	@Override
	public void markDirty()
	{
		super.markDirty();

		if (!reading)
		{
			saveInventory();
		}
	}

	protected void setNBT()
	{
		ItemStack itemStack = player.getCurrentEquippedItem();

		if (itemStack!=null && itemStack.getItem() instanceof ItemDropFilter)
		{
			itemStack.setTagCompound(dropFilter.getTagCompound());
		}
	}

	public void saveInventory()
	{
		writeToNBT();
		setNBT();
	}

	protected void writeToNBT()
	{
		if (dropFilter.stackTagCompound==null)
		{
			dropFilter.stackTagCompound = new NBTTagCompound();
		}
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
		dropFilter.stackTagCompound.setTag("Inventory", inventory);
	}

	protected void readFromNBT()
	{
		reading = true;

		NBTTagList itemList = (NBTTagList) ((NBTTagCompound) dropFilter.stackTagCompound.getTag("Inventory")).getTag("Items");
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
	
	@Override
	public String getInventoryName()
	{
		return "Drop Filter";
	}
}
