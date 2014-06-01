package lumien.randomthings.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import lumien.randomthings.RandomThings;
import net.minecraft.item.Item;

public class ItemSpiritBinder extends Item
{
	public ItemSpiritBinder()
	{
		this.setUnlocalizedName("spiritBinder");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setTextureName("RandomThings:spiritBinder");
		
		GameRegistry.registerItem(this, "spiritBinder");
	}
}
