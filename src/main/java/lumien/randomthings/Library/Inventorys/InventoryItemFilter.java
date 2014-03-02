package lumien.randomthings.Library.Inventorys;

import lumien.randomthings.Items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryItemFilter extends InventoryBasic
{
	private EntityPlayer playerEntity;
	private ItemStack originalIS;

	private boolean reading = false;

	public InventoryItemFilter(EntityPlayer player, ItemStack is)
	{
		super("Item Filter", false, 9);

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

	@Override
	public String getInventoryName()
	{
		return "Item Filter";
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
		if (itemStack.getItem() instanceof ItemFilter && itemStack.getItemDamage() == 1)
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
		// save name in display->Name
		NBTTagCompound name = new NBTTagCompound();
		name.setString("Name", getInventoryName());

		originalIS.stackTagCompound.setTag("display", name);

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
			NBTTagCompound slotEntry = (NBTTagCompound) itemList.getCompoundTagAt(i);
			int j = slotEntry.getByte("Slot") & 0xff;

			if (j >= 0 && j < getSizeInventory())
			{
				setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
			}
		}
		reading = false;
	}

}
