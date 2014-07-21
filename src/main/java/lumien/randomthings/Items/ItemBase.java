package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		this.setUnlocalizedName(name);
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:"+name);
		
		GameRegistry.registerItem(this, name);
	}
}
