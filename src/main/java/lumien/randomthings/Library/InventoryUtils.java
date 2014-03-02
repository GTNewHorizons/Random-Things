package lumien.randomthings.Library;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryUtils
{
	public static void writeInventoryToNBT(IInventory inventory, NBTTagCompound nbt)
	{
		if (nbt==null || inventory==null)
		{
			return;
		}
		NBTTagList items = new NBTTagList();

		for (int slot = 0; slot < inventory.getSizeInventory(); slot++)
		{
			if (inventory.getStackInSlot(slot) != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("slot", slot);
				inventory.getStackInSlot(slot).writeToNBT(tag);

				items.appendTag(tag);
			}
		}

		nbt.setTag("items", items);
	}

	public static void readInventoryFromNBT(IInventory inventory, NBTTagCompound compound)
	{
		if (compound == null || compound.getTag("items") == null)
		{
			return;
		}
		NBTTagList items = (NBTTagList) compound.getTag("items");

		for (int i = 0; i < items.tagCount(); i++)
		{
			NBTTagCompound nbt = items.getCompoundTagAt(i);
			int slot = nbt.getInteger("slot");
			ItemStack lIS = ItemStack.loadItemStackFromNBT(nbt);
			inventory.setInventorySlotContents(slot, lIS);
		}
	}
}
