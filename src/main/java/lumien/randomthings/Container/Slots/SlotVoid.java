package lumien.randomthings.Container.Slots;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotVoid extends Slot
{

	public SlotVoid(int x, int y)
	{
		super(null, 0, x, y);
	}

	@Override
	public void putStack(ItemStack par1ItemStack)
	{
		this.onSlotChanged();
	}

	@Override
	public ItemStack getStack()
	{
		return null;
	}

	@Override
	public void onSlotChanged()
	{

	}

	@Override
	public int getSlotStackLimit()
	{
		return 64;
	}
}
