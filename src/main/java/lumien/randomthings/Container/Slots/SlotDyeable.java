package lumien.randomthings.Container.Slots;

import lumien.randomthings.Transformer.MCPNames;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotDyeable extends Slot
{

	public SlotDyeable(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItem() instanceof ItemBlock)
		{
			return false;
		}
		
		Item item = par1ItemStack.getItem();
		if (item.getClass()==Item.class)
		{
			return true;
		}
		try
		{
			item.getClass().getDeclaredMethod(MCPNames.method("func_82790_a"), ItemStack.class, int.class);
			return false;
		}
		catch (NoSuchMethodException e)
		{
			return true;
		}
	}
}
