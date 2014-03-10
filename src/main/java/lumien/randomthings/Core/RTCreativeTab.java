package lumien.randomthings.Core;

import lumien.randomthings.Items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RTCreativeTab extends CreativeTabs
{

	public RTCreativeTab()
	{
		super ("Random Things");
	}

	@Override
	@SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        return new ItemStack(ModItems.ingredients,0);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ModItems.ingredients;
	}

}
