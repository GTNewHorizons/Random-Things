package lumien.randomthings.Library.Interfaces;

import net.minecraft.item.ItemStack;

public interface IItemWithProperties
{
	public boolean isValidAttribute(ItemStack is,String attributeName,int attributeType);
}
