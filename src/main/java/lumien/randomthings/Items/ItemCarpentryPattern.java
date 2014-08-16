package lumien.randomthings.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCarpentryPattern extends ItemBase
{
	public enum PatternType
	{
		WORKBENCH, CHEST;
	}

	public ItemCarpentryPattern()
	{
		super("carpentryPattern");

		this.setHasSubtypes(true);
		this.setCreativeTab(null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int damage = 0; damage < PatternType.values().length; damage++)
		{
			list.add(new ItemStack(item, 1, damage));
		}
	}
}
