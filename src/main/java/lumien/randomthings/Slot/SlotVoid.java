package lumien.randomthings.Slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotVoid extends Slot
{

	public SlotVoid(int x, int y)
	{
		super(null, 0, x, y);
	}
	
    public void putStack(ItemStack par1ItemStack)
    {
        this.onSlotChanged();
    }
    
    public ItemStack getStack()
    {
        return null;
    }
    
    public void onSlotChanged()
    {
        
    }
    
    public int getSlotStackLimit()
    {
        return 64;
    }
}
