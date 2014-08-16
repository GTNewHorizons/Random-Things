package lumien.randomthings.Container.Slots;

import lumien.randomthings.Container.ContainerCarpentryBench;
import lumien.randomthings.Items.ItemCarpentryPattern;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCarpentryPattern extends Slot
{
	ContainerCarpentryBench container;

	public SlotCarpentryPattern(ContainerCarpentryBench container, IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_)
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.container = container;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItem() instanceof ItemCarpentryPattern;
	}

	@Override
	public void onSlotChanged()
    {
		container.onCraftMatrixChanged(container.pattern);
	}
}
